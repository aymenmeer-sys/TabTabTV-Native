package com.tabtab.tv.core.database

import com.tabtab.tv.core.database.entity.ChannelEntity
import com.tabtab.tv.core.database.entity.RadioStationEntity
import com.tabtab.tv.core.network.GenreTaxonomy
import com.tabtab.tv.core.network.RawChannel
import com.tabtab.tv.core.network.RawRadioStation
import java.security.MessageDigest

private fun stableId(prefix: String, streamUrl: String): String {
    val digest = MessageDigest.getInstance("MD5").digest(streamUrl.toByteArray())
    return prefix + "_" + digest.joinToString("") { "%02x".format(it) }.take(16)
}

fun RawChannel.toEntity(channelNumber: Int): ChannelEntity = ChannelEntity(
    id = stableId("ch", streamUrl),
    name = name,
    logoUrl = logoUrl,
    genre = GenreTaxonomy.normalize(rawCategory),
    country = country,
    streamUrl = streamUrl,
    backupStreamUrl = null,
    format = if (streamUrl.contains(".m3u8")) "HLS" else "MP4",
    quality = "HD",
    channelNumber = channelNumber
)

fun RawRadioStation.toEntity(): RadioStationEntity = RadioStationEntity(
    id = id,
    name = name,
    logoUrl = logoUrl,
    genre = genre,
    country = country,
    streamUrl = streamUrl,
    bitrateKbps = bitrateKbps
)
