package com.tabtab.tv.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "channels")
data class ChannelEntity(
    @PrimaryKey val id: String,
    val name: String,
    val logoUrl: String?,
    val genre: String,
    val country: String,
    val streamUrl: String,
    val backupStreamUrl: String?,
    val format: String,
    val quality: String,
    val channelNumber: Int
)

@Entity(tableName = "radio_stations")
data class RadioStationEntity(
    @PrimaryKey val id: String,
    val name: String,
    val logoUrl: String?,
    val genre: String,
    val country: String,
    val streamUrl: String,
    val bitrateKbps: Int?
)

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: String,
    val title: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val year: Int,
    val durationMinutes: Int,
    val rating: Float,
    val genres: String, // comma-separated
    val country: String,
    val director: String?,
    val cast: String, // comma-separated
    val synopsis: String,
    val streamUrl: String,
    val quality: String,
    val sourceAttribution: String,
    val licenseName: String
)

@Entity(tableName = "series")
data class SeriesEntity(
    @PrimaryKey val id: String,
    val title: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val year: Int,
    val genres: String,
    val country: String,
    val rating: Float,
    val synopsis: String,
    val sourceAttribution: String,
    val licenseName: String
)

@Entity(tableName = "episodes", primaryKeys = ["id"])
data class EpisodeEntity(
    val id: String,
    val seriesId: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val title: String,
    val durationMinutes: Int,
    val thumbnailUrl: String?,
    val synopsis: String,
    val streamUrl: String
)

@Entity(tableName = "epg_programs", primaryKeys = ["id"])
data class EpgProgramEntity(
    val id: String,
    val channelId: String,
    val title: String,
    val description: String,
    val startTimeMillis: Long,
    val endTimeMillis: Long
)

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val id: String,
    val displayName: String,
    val avatarId: String,
    val type: String,
    val pinHash: String?,
    val maxAgeRating: Int,
    val dailyScreenTimeMinutes: Int?,
    val allowedHoursStart: Int?,
    val allowedHoursEnd: Int?,
    val blockedGenres: String // comma-separated
)

@Entity(tableName = "watch_history", primaryKeys = ["profileId", "contentId"])
data class WatchHistoryEntity(
    val profileId: String,
    val contentId: String,
    val contentKind: String,
    val progressSeconds: Long,
    val durationSeconds: Long,
    val lastWatchedAtMillis: Long
)

@Entity(tableName = "favorites", primaryKeys = ["profileId", "contentId"])
data class FavoriteEntity(
    val profileId: String,
    val contentId: String,
    val contentKind: String
)
