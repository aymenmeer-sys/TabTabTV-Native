package com.tabtab.tv.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tabtab.tv.core.database.dao.*
import com.tabtab.tv.core.database.entity.*

@Database(
    entities = [
        ChannelEntity::class,
        RadioStationEntity::class,
        MovieEntity::class,
        SeriesEntity::class,
        EpisodeEntity::class,
        EpgProgramEntity::class,
        ProfileEntity::class,
        WatchHistoryEntity::class,
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false // revisit once the schema stabilizes closer to release
)
abstract class TabTabDatabase : RoomDatabase() {
    abstract fun channelDao(): ChannelDao
    abstract fun radioStationDao(): RadioStationDao
    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao
    abstract fun epgDao(): EpgDao
    abstract fun profileDao(): ProfileDao
    abstract fun watchHistoryDao(): WatchHistoryDao
    abstract fun favoriteDao(): FavoriteDao
}
