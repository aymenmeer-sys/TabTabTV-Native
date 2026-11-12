package com.tabtab.tv

import com.tabtab.tv.data.api.ApiFactory
import com.tabtab.tv.data.repo.ContentRepository
import com.tabtab.tv.data.session.SessionManager

/**
 * Builds a ContentRepository from stored credentials. Simple manual DI —
 * no Hilt/Dagger needed for a project this size, and it keeps the wiring
 * easy to follow for anyone picking up the codebase.
 */
object AppState {
    var repository: ContentRepository? = null
        private set

    var serverUrl: String? = null
        private set

    fun start(serverUrl: String, username: String, password: String) {
        val api = ApiFactory.create(serverUrl)
        repository = ContentRepository(api, username, password)
        this.serverUrl = serverUrl.trimEnd('/')
    }

    suspend fun restoreFromSession(session: SessionManager) {
        val creds = session.current() ?: return
        start(creds.serverUrl, creds.username, creds.password)
    }

    fun clear() {
        repository = null
        serverUrl = null
    }
}
