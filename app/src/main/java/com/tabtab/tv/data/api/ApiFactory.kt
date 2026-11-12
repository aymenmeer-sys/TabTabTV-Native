package com.tabtab.tv.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Builds a Retrofit client for a given Xtream panel base URL.
 *
 * The analyzed reference app baked one encrypted panel URL into the APK and
 * decrypted it natively at runtime, tying the build to a single reseller
 * backend. Here the base URL is just whatever the user types at login —
 * no secret, no native layer, works against any Xtream Codes panel the
 * user is actually authorized to use.
 */
object ApiFactory {

    fun create(baseUrl: String): XtreamApi {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        val normalizedBase = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/"

        return Retrofit.Builder()
            .baseUrl(normalizedBase)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(XtreamApi::class.java)
    }
}
