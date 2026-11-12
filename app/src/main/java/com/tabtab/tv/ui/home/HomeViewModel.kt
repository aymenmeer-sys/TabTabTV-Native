package com.tabtab.tv.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tabtab.tv.domain.repository.ChannelRepository
import com.tabtab.tv.domain.repository.RadioRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeUiState(val channelCount: Int = 0, val radioCount: Int = 0)

@HiltViewModel
class HomeViewModel @Inject constructor(
    channelRepository: ChannelRepository,
    radioRepository: RadioRepository
) : ViewModel() {

    val uiState = combine(
        channelRepository.observeChannels(),
        radioRepository.observeStations()
    ) { channels, radios -> HomeUiState(channels.size, radios.size) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())
}
