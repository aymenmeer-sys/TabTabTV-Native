package com.tabtab.tv.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Home: "Continuer à regarder" + rows by country/genre (sprint S8 wires real content
 * once the aggregation pipeline from S2 exists). Placeholder for now so navigation
 * and the Hilt/Room/theme foundations can be verified end-to-end.
 */
@Composable
fun HomeScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Accueil — à venir (S8)")
    }
}
