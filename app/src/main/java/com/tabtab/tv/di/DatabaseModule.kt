package com.tabtab.tv.di

import android.content.Context
import androidx.room.Room
import com.tabtab.tv.core.database.TabTabDatabase
import com.tabtab.tv.core.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TabTabDatabase =
        Room.databaseBuilder(context, TabTabDatabase::class.java, "tabtab_tv.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideChannelDao(db: TabTabDatabase): ChannelDao = db.channelDao()

    @Provides
    fun provideRadioStationDao(db: TabTabDatabase): RadioStationDao = db.radioStationDao()

    @Provides
    fun provideMovieDao(db: TabTabDatabase): MovieDao = db.movieDao()

    @Provides
    fun provideSeriesDao(db: TabTabDatabase): SeriesDao = db.seriesDao()

    @Provides
    fun provideEpgDao(db: TabTabDatabase): EpgDao = db.epgDao()

    @Provides
    fun provideProfileDao(db: TabTabDatabase): ProfileDao = db.profileDao()

    @Provides
    fun provideWatchHistoryDao(db: TabTabDatabase): WatchHistoryDao = db.watchHistoryDao()

    @Provides
    fun provideFavoriteDao(db: TabTabDatabase): FavoriteDao = db.favoriteDao()
}
