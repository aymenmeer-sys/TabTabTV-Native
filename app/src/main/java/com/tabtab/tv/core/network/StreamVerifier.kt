package com.tabtab.tv.core.network

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import java.net.URI
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory

private const val BROWSER_USER_AGENT =
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36"
private const val CHECK_TIMEOUT_MS = 10_000L

/**
 * Confirms a stream URL actually responds before it ever reaches a user — same approach
 * validated in the TabTab TV web project: a realistic browser User-Agent + matching
 * Referer/Origin (many free hosts reject requests without them), a raw-socket fallback
 * for legacy Icecast/Shoutcast "ICY" responses (which standard HTTP clients reject
 * outright as invalid), and a hard timeout so one stuck connection can never stall the
 * whole verification batch.
 */
class StreamVerifier @Inject constructor(private val client: OkHttpClient) {

    suspend fun isAlive(streamUrl: String): Boolean = withContext(Dispatchers.IO) {
        withTimeoutOrNull(CHECK_TIMEOUT_MS + 2000) {
            try {
                checkViaHttp(streamUrl)
            } catch (e: Exception) {
                // Legacy Icecast/Shoutcast servers reply with "ICY 200 OK" instead of a
                // standard HTTP status line, which OkHttp rejects outright as malformed.
                checkViaRawSocket(streamUrl)
            }
        } ?: false
    }

    private fun checkViaHttp(url: String): Boolean {
        val origin = runCatching { URI(url).let { "${it.scheme}://${it.host}" } }.getOrNull()
        val requestBuilder = Request.Builder()
            .url(url)
            .header("User-Agent", BROWSER_USER_AGENT)
            .header("Range", "bytes=0-4096")
        if (origin != null) {
            requestBuilder.header("Referer", "$origin/").header("Origin", origin)
        }
        client.newCall(requestBuilder.build()).execute().use { response ->
            if (!response.isSuccessful && response.code != 206) return false
            if (url.contains(".m3u8")) {
                val text = response.body?.string().orEmpty()
                return text.contains("#EXTM3U")
            }
            return true
        }
    }

    /** Bypasses OkHttp's strict HTTP parser entirely, reading the raw response bytes. */
    private fun checkViaRawSocket(urlString: String): Boolean {
        val uri = runCatching { URI(urlString) }.getOrNull() ?: return false
        val host = uri.host ?: return false
        val isHttps = uri.scheme == "https"
        val port = if (uri.port != -1) uri.port else if (isHttps) 443 else 80
        val path = (uri.rawPath.ifEmpty { "/" }) + (uri.rawQuery?.let { "?$it" } ?: "")

        val socket: Socket = try {
            if (isHttps) SSLSocketFactory.getDefault().createSocket(host, port)
            else Socket(host, port)
        } catch (e: Exception) {
            return false
        }

        return socket.use {
            it.soTimeout = CHECK_TIMEOUT_MS.toInt()
            val request = "GET $path HTTP/1.1\r\n" +
                "Host: $host\r\n" +
                "User-Agent: $BROWSER_USER_AGENT\r\n" +
                "Icy-MetaData: 1\r\n" +
                "Connection: close\r\n\r\n"
            it.getOutputStream().write(request.toByteArray())
            it.getOutputStream().flush()

            val reader = BufferedReader(InputStreamReader(it.getInputStream()))
            val statusLine = reader.readLine() ?: return@use false
            when {
                statusLine.startsWith("ICY") -> true
                statusLine.startsWith("HTTP/1.") -> {
                    val code = statusLine.split(" ").getOrNull(1)?.toIntOrNull() ?: return@use false
                    code in 200..399
                }
                else -> false
            }
        }
    }
}

/** Runs [worker] over [items] with at most [concurrency] in flight, returning results in order. */
suspend fun <T, R> runWithConcurrency(items: List<T>, concurrency: Int, worker: suspend (T) -> R): List<R> =
    coroutineScope {
        val semaphore = Semaphore(concurrency)
        items.map { item ->
            async { semaphore.withPermit { worker(item) } }
        }.awaitAll()
    }
