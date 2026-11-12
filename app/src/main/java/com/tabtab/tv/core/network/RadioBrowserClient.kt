package com.tabtab.tv.core.network

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

private data class RadioBrowserStation(
    @SerializedName("stationuuid") val stationUuid: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("url_resolved") val urlResolved: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("favicon") val favicon: String?,
    @SerializedName("tags") val tags: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("bitrate") val bitrate: Int?
)

data class RawRadioStation(
    val id: String,
    val name: String,
    val logoUrl: String?,
    val genre: String,
    val country: String,
    val streamUrl: String,
    val bitrateKbps: Int?
)

/**
 * Fetches real-world radio stations from the public Radio-Browser API — same
 * mirror-fallback + topclick/topvote dedup approach validated in the web project.
 */
class RadioBrowserClient @Inject constructor(
    private val client: OkHttpClient,
    private val gson: Gson
) {
    private val mirrors = listOf(
        "https://de1.api.radio-browser.info",
        "https://nl1.api.radio-browser.info",
        "https://at1.api.radio-browser.info"
    )

    suspend fun fetchStations(): List<RawRadioStation> = withContext(Dispatchers.IO) {
        val mirror = mirrors.firstOrNull { isReachable(it) } ?: return@withContext emptyList()
        val all = fetchEndpoint("$mirror/json/stations/topclick/2000") +
            fetchEndpoint("$mirror/json/stations/topvote/2000")

        val seenUrls = HashSet<String>()
        val result = ArrayList<RawRadioStation>()
        for (station in all) {
            val streamUrl = station.urlResolved?.ifBlank { null } ?: station.url ?: continue
            if (streamUrl in seenUrls) continue
            seenUrls.add(streamUrl)
            result.add(
                RawRadioStation(
                    id = "rb_${station.stationUuid ?: result.size}",
                    name = station.name?.ifBlank { null } ?: "Radio ${result.size + 1}",
                    logoUrl = station.favicon?.takeIf { it.startsWith("http") },
                    genre = station.tags?.split(",")?.take(3)?.joinToString(", ")?.ifBlank { "Variety" } ?: "Variety",
                    country = station.country?.ifBlank { null } ?: "International",
                    streamUrl = streamUrl,
                    bitrateKbps = station.bitrate
                )
            )
        }
        result
    }

    private fun isReachable(mirror: String): Boolean = try {
        client.newCall(Request.Builder().url("$mirror/json/stats").build()).execute().use { it.isSuccessful }
    } catch (e: Exception) {
        false
    }

    private fun fetchEndpoint(url: String): List<RadioBrowserStation> = try {
        client.newCall(Request.Builder().url(url).header("User-Agent", "TabTabTV/1.0").build())
            .execute().use { response ->
                if (!response.isSuccessful) return emptyList()
                val body = response.body?.string() ?: return emptyList()
                gson.fromJson(body, Array<RadioBrowserStation>::class.java)?.toList() ?: emptyList()
            }
    } catch (e: Exception) {
        emptyList()
    }
}
