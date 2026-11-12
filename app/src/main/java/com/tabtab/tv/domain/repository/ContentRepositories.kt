package com.tabtab.tv.domain.repository

import com.tabtab.tv.domain.model.Channel
import com.tabtab.tv.domain.model.RadioStation
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    fun observeChannels(): Flow<List<Channel>>
}

interface RadioRepository {
    fun observeStations(): Flow<List<RadioStation>>
}
