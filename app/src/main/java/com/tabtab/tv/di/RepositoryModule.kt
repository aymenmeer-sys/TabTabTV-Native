package com.tabtab.tv.di

import com.tabtab.tv.data.repository.ChannelRepositoryImpl
import com.tabtab.tv.data.repository.RadioRepositoryImpl
import com.tabtab.tv.domain.repository.ChannelRepository
import com.tabtab.tv.domain.repository.RadioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChannelRepository(impl: ChannelRepositoryImpl): ChannelRepository

    @Binds
    @Singleton
    abstract fun bindRadioRepository(impl: RadioRepositoryImpl): RadioRepository
}
