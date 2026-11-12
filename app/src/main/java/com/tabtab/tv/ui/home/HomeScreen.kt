package com.tabtab.tv.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Smoke-test view for S2: shows the live channel/radio counts flowing from the
 * on-device aggregation pipeline through Room, confirming the whole chain works
 * end-to-end. Replaced by the real Netflix-style home (rows, continue watching)
 * in S8, once Live TV/VOD/Series UIs exist to populate it.
 */
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.channelCount == 0 && uiState.radioCount == 0) {
            CircularProgressIndicator()
            Text("Agrégation du contenu en cours…")
        } else {
            Text("${uiState.channelCount} chaînes vérifiées")
            Text("${uiState.radioCount} radios vérifiées")
        }
    }
}
