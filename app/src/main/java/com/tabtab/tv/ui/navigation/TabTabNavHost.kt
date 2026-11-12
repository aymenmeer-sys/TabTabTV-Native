package com.tabtab.tv.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tabtab.tv.player.PlayerScreen
import com.tabtab.tv.ui.common.PlaceholderScreen
import com.tabtab.tv.ui.home.HomeScreen

object Routes {
    const val HOME = "home"
    const val LIVE_TV = "live_tv"
    const val RADIO = "radio"
    const val VOD = "vod"
    const val SERIES = "series"
    const val SEARCH = "search"
    const val SETTINGS = "settings"
    const val PLAYER = "player/{url}"

    fun player(url: String) = "player/${java.net.URLEncoder.encode(url, "UTF-8")}"
}

@Composable
fun TabTabNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) { HomeScreen() }
        composable(Routes.LIVE_TV) { PlaceholderScreen("Direct TV — à venir (S3)") }
        composable(Routes.RADIO) { PlaceholderScreen("Radio — à venir (S3)") }
        composable(Routes.VOD) { PlaceholderScreen("Films — à venir (S6)") }
        composable(Routes.SERIES) { PlaceholderScreen("Séries — à venir (S6)") }
        composable(Routes.SEARCH) { PlaceholderScreen("Recherche — à venir (S10)") }
        composable(Routes.SETTINGS) { PlaceholderScreen("Paramètres — à venir (S12)") }
        composable(Routes.PLAYER) { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("url").orEmpty()
            val url = java.net.URLDecoder.decode(encoded, "UTF-8")
            PlayerScreen(streamUrl = url)
        }
    }
}
