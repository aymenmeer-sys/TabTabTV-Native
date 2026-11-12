package com.tabtab.tv.data.api

import com.tabtab.tv.data.model.*
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Xtream Codes "player_api.php" surface. Every panel exposes the same
 * action-based endpoint; we model each action as its own call for
 * type safety instead of one catch-all JSON blob.
 */
interface XtreamApi {

    @GET("player_api.php")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): LoginInfo

    @GET("player_api.php")
    suspend fun getLiveCategories(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_live_categories"
    ): List<Category>

    @GET("player_api.php")
    suspend fun getLiveStreams(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("category_id") categoryId: String?,
        @Query("action") action: String = "get_live_streams"
    ): List<LiveStream>

    @GET("player_api.php")
    suspend fun getVodCategories(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_vod_categories"
    ): List<Category>

    @GET("player_api.php")
    suspend fun getVodStreams(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("category_id") categoryId: String?,
        @Query("action") action: String = "get_vod_streams"
    ): List<VodStream>

    @GET("player_api.php")
    suspend fun getVodInfo(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("vod_id") vodId: Int,
        @Query("action") action: String = "get_vod_info"
    ): VodInfo

    @GET("player_api.php")
    suspend fun getSeriesCategories(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("action") action: String = "get_series_categories"
    ): List<Category>

    @GET("player_api.php")
    suspend fun getSeries(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("category_id") categoryId: String?,
        @Query("action") action: String = "get_series"
    ): List<SeriesItem>

    @GET("player_api.php")
    suspend fun getSeriesInfo(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("series_id") seriesId: Int,
        @Query("action") action: String = "get_series_info"
    ): SeriesInfo

    @GET("player_api.php")
    suspend fun getShortEpg(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("stream_id") streamId: Int,
        @Query("action") action: String = "get_short_epg"
    ): EpgResponse
}

/** Builds the direct stream URLs Xtream panels expect — not an API call, just URL shape. */
object StreamUrlBuilder {
    fun live(baseUrl: String, username: String, password: String, streamId: Int, ext: String = "m3u8") =
        "$baseUrl/live/$username/$password/$streamId.$ext"

    fun vod(baseUrl: String, username: String, password: String, streamId: Int, ext: String) =
        "$baseUrl/movie/$username/$password/$streamId.$ext"

    fun series(baseUrl: String, username: String, password: String, episodeId: String, ext: String) =
        "$baseUrl/series/$username/$password/$episodeId.$ext"
}
