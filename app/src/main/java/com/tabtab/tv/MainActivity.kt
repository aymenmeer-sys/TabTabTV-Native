package com.tabtab.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tabtab.tv.data.session.SessionManager
import com.tabtab.tv.player.PlayerScreen
import com.tabtab.tv.ui.screens.HomeScreen
import com.tabtab.tv.ui.screens.LoginScreen
import com.tabtab.tv.ui.theme.TabTabTvTheme
import kotlinx.coroutines.launch

private object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val PLAYER = "player/{url}"
    fun player(url: String) = "player/${java.net.URLEncoder.encode(url, "UTF-8")}"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sessionManager = SessionManager(applicationContext)

        setContent {
            TabTabTvTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavHost(navController, sessionManager)
                }
            }
        }
    }
}

@Composable
private fun AppNavHost(navController: NavHostController, sessionManager: SessionManager) {
    var startDestination by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val loggedIn = sessionManager.isLoggedInFlow.let {
            kotlinx.coroutines.flow.first(it)
        }
        if (loggedIn) {
            AppState.restoreFromSession(sessionManager)
        }
        startDestination = if (loggedIn) Routes.HOME else Routes.LOGIN
    }

    val resolvedStart = startDestination
    if (resolvedStart == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }

    NavHost(navController = navController, startDestination = resolvedStart) {
        composable(Routes.LOGIN) {
            LoginScreen(
                sessionManager = sessionManager,
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            val scope = rememberCoroutineScope()
            HomeScreen(
                onPlayLive = { stream ->
                    val repo = AppState.repository
                    val base = AppState.serverUrl
                    if (repo != null && base != null) {
                        val url = repo.liveStreamUrl(base, stream.streamId)
                        navController.navigate(Routes.player(url))
                    }
                },
                onOpenVod = { vod ->
                    val repo = AppState.repository
                    val base = AppState.serverUrl
                    if (repo != null && base != null) {
                        val ext = vod.containerExtension ?: "mp4"
                        val url = repo.vodStreamUrl(base, vod.streamId, ext)
                        navController.navigate(Routes.player(url))
                    }
                },
                onOpenSeries = { /* TODO: navigate to a season/episode picker screen */ },
                onLogout = {
                    scope.launch {
                        sessionManager.clear()
                        AppState.clear()
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.HOME) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(Routes.PLAYER) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("url").orEmpty()
            val url = java.net.URLDecoder.decode(encoded, "UTF-8")
            PlayerScreen(streamUrl = url)
        }
    }
}
