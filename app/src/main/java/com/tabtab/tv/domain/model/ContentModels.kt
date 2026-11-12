package com.tabtab.tv.domain.model

/**
 * Pure Kotlin domain models — no Room/Retrofit annotations here on purpose, so the
 * UI and use-cases never depend on a specific persistence or network technology.
 * Mapping to/from Room entities and network DTOs happens in the data layer.
 */

enum class StreamFormat { HLS, MP4, DASH }
enum class StreamQuality { SD, HD, FHD, UHD_4K }

data class Channel(
    val id: String,
    val name: String,
    val logoUrl: String?,
    val genre: String,
    val country: String,
    val streamUrl: String,
    val backupStreamUrl: String? = null,
    val format: StreamFormat = StreamFormat.HLS,
    val quality: StreamQuality = StreamQuality.HD,
    val channelNumber: Int = 0
)

data class RadioStation(
    val id: String,
    val name: String,
    val logoUrl: String?,
    val genre: String,
    val country: String,
    val streamUrl: String,
    val bitrateKbps: Int? = null
)

data class Movie(
    val id: String,
    val title: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val year: Int,
    val durationMinutes: Int,
    val rating: Float,
    val genres: List<String>,
    val country: String,
    val director: String?,
    val cast: List<String>,
    val synopsis: String,
    val streamUrl: String,
    val quality: StreamQuality,
    val sourceAttribution: String,
    val licenseName: String
)

data class Episode(
    val id: String,
    val seasonNumber: Int,
    val episodeNumber: Int,
    val title: String,
    val durationMinutes: Int,
    val thumbnailUrl: String?,
    val synopsis: String,
    val streamUrl: String
)

data class Series(
    val id: String,
    val title: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val year: Int,
    val genres: List<String>,
    val country: String,
    val rating: Float,
    val synopsis: String,
    val episodes: List<Episode>,
    val sourceAttribution: String,
    val licenseName: String
)

data class EpgProgram(
    val id: String,
    val channelId: String,
    val title: String,
    val description: String,
    val startTimeMillis: Long,
    val endTimeMillis: Long
)

enum class ContentKind { CHANNEL, RADIO, MOVIE, SERIES_EPISODE }

/** One row per (profile, content) — powers favorites and resume playback. */
data class WatchHistoryEntry(
    val profileId: String,
    val contentId: String,
    val contentKind: ContentKind,
    val progressSeconds: Long,
    val durationSeconds: Long,
    val lastWatchedAtMillis: Long
)

data class FavoriteEntry(
    val profileId: String,
    val contentId: String,
    val contentKind: ContentKind
)
