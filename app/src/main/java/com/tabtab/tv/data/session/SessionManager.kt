package com.tabtab.tv.data.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "session")

/**
 * Holds the panel credentials the user enters at login. Stored in DataStore,
 * which is sandboxed to this app by the OS — not shipped encrypted/embedded
 * in the APK like a resold white-label app would do with a fixed panel.
 */
class SessionManager(private val context: Context) {

    private object Keys {
        val SERVER_URL = stringPreferencesKey("server_url")
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
    }

    val serverUrlFlow: Flow<String?> = context.dataStore.data.map { it[Keys.SERVER_URL] }
    val usernameFlow: Flow<String?> = context.dataStore.data.map { it[Keys.USERNAME] }
    val isLoggedInFlow: Flow<Boolean> = context.dataStore.data.map { it[Keys.SERVER_URL] != null }

    suspend fun save(serverUrl: String, username: String, password: String) {
        context.dataStore.edit {
            it[Keys.SERVER_URL] = serverUrl.trimEnd('/')
            it[Keys.USERNAME] = username
            it[Keys.PASSWORD] = password
        }
    }

    suspend fun clear() {
        context.dataStore.edit { it.clear() }
    }

    data class Credentials(val serverUrl: String, val username: String, val password: String)

    suspend fun current(): Credentials? {
        val prefs = context.dataStore.data.first()
        val url = prefs[Keys.SERVER_URL] ?: return null
        val user = prefs[Keys.USERNAME] ?: return null
        val pass = prefs[Keys.PASSWORD] ?: return null
        return Credentials(url, user, pass)
    }
}
