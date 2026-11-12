package com.tabtab.tv.domain.model

enum class ProfileType { PARENT, CHILD, GUEST }

/**
 * A profile's own PIN (if any) is stored hashed, never in clear text — see
 * core.security.PinManager. [allowedHoursStart]/[allowedHoursEnd] implement the
 * "no content after 22:00" style time-window restriction for child profiles.
 */
data class Profile(
    val id: String,
    val displayName: String,
    val avatarId: String,
    val type: ProfileType,
    val hasPin: Boolean,
    val maxAgeRating: Int = 18,
    val dailyScreenTimeMinutes: Int? = null,
    val allowedHoursStart: Int? = null,
    val allowedHoursEnd: Int? = null,
    val blockedGenres: List<String> = emptyList()
)
