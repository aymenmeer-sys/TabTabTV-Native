package com.tabtab.tv.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class IptvPlaylistClient @Inject constructor(private val client: OkHttpClient) {

    /** Downloads every configured M3U source and parses it; failures on one source don't block the rest. */
    suspend fun fetchAllChannels(): List<RawChannel> = withContext(Dispatchers.IO) {
        val result = ArrayList<RawChannel>()
        for ((url, country) in IPTV_PLAYLIST_SOURCES) {
            try {
                val text = fetchText(url) ?: continue
                result.addAll(M3uParser.parse(text, country))
            } catch (e: Exception) {
                // One bad source shouldn't abort the whole aggregation run.
            }
        }
        result
    }

    private fun fetchText(url: String): String? =
        client.newCall(Request.Builder().url(url).header("User-Agent", "TabTabTV-Aggregator/1.0").build())
            .execute().use { response ->
                if (!response.isSuccessful) null else response.body?.string()
            }
}
