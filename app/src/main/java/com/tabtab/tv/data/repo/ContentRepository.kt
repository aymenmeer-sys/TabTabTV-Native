package com.tabtab.tv.data.repo

import com.tabtab.tv.data.api.StreamUrlBuilder
import com.tabtab.tv.data.api.XtreamApi
import com.tabtab.tv.data.model.*

/**
 * Thin wrapper around XtreamApi so ViewModels don't juggle username/password
 * on every call. One instance is built per logged-in session.
 */
class ContentRepository(
    private val api: XtreamApi,
    private val username: String,
    private val password: String
) {
    suspend fun login() = api.login(username, password)

    fun liveStreamUrl(baseUrl: String, streamId: Int) =
        StreamUrlBuilder.live(baseUrl, username, password, streamId)

    fun vodStreamUrl(baseUrl: String, streamId: Int, ext: String) =
        StreamUrlBuilder.vod(baseUrl, username, password, streamId, ext)

    fun seriesStreamUrl(baseUrl: String, episodeId: String, ext: String) =
        StreamUrlBuilder.series(baseUrl, username, password, episodeId, ext)

    suspend fun liveCategories() = api.getLiveCategories(username, password)
    suspend fun liveStreams(categoryId: String?) = api.getLiveStreams(username, password, categoryId)

    suspend fun vodCategories() = api.getVodCategories(username, password)
    suspend fun vodStreams(categoryId: String?) = api.getVodStreams(username, password, categoryId)
    suspend fun vodInfo(vodId: Int) = api.getVodInfo(username, password, vodId)

    suspend fun seriesCategories() = api.getSeriesCategories(username, password)
    suspend fun series(categoryId: String?) = api.getSeries(username, password, categoryId)
    suspend fun seriesInfo(seriesId: Int) = api.getSeriesInfo(username, password, seriesId)

    suspend fun shortEpg(streamId: Int) = api.getShortEpg(username, password, streamId)
}
