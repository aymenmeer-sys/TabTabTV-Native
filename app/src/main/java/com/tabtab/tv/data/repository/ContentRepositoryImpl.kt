package com.tabtab.tv.data.repository

import com.tabtab.tv.core.database.dao.ChannelDao
import com.tabtab.tv.core.database.dao.RadioStationDao
import com.tabtab.tv.core.database.entity.ChannelEntity
import com.tabtab.tv.core.database.entity.RadioStationEntity
import com.tabtab.tv.domain.model.*
import com.tabtab.tv.domain.repository.ChannelRepository
import com.tabtab.tv.domain.repository.RadioRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private fun ChannelEntity.toDomain() = Channel(
    id = id,
    name = name,
    logoUrl = logoUrl,
    genre = genre,
    country = country,
    streamUrl = streamUrl,
    backupStreamUrl = backupStreamUrl,
    format = runCatching { StreamFormat.valueOf(format) }.getOrDefault(StreamFormat.HLS),
    quality = runCatching { StreamQuality.valueOf(quality) }.getOrDefault(StreamQuality.HD),
    channelNumber = channelNumber
)

private fun RadioStationEntity.toDomain() = RadioStation(
    id = id,
    name = name,
    logoUrl = logoUrl,
    genre = genre,
    country = country,
    streamUrl = streamUrl,
    bitrateKbps = bitrateKbps
)

class ChannelRepositoryImpl @Inject constructor(
    private val dao: ChannelDao
) : ChannelRepository {
    override fun observeChannels(): Flow<List<Channel>> = dao.observeAll().map { list -> list.map { it.toDomain() } }
}

class RadioRepositoryImpl @Inject constructor(
    private val dao: RadioStationDao
) : RadioRepository {
    override fun observeStations(): Flow<List<RadioStation>> = dao.observeAll().map { list -> list.map { it.toDomain() } }
}
