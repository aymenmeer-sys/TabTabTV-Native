package com.tabtab.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tabtab.tv.AppState
import com.tabtab.tv.R
import com.tabtab.tv.data.model.LiveStream
import com.tabtab.tv.data.model.SeriesItem
import com.tabtab.tv.data.model.VodStream
import kotlinx.coroutines.launch

private enum class HomeTab { LIVE, VOD, SERIES }

@Composable
fun HomeScreen(
    onPlayLive: (LiveStream) -> Unit,
    onOpenVod: (VodStream) -> Unit,
    onOpenSeries: (SeriesItem) -> Unit,
    onLogout: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(HomeTab.LIVE) }

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            actions = {
                TextButton(onClick = onLogout) { Text(stringResource(id = R.string.action_logout)) }
            }
        )
        TabRow(selectedTabIndex = selectedTab.ordinal) {
            Tab(selectedTab == HomeTab.LIVE, onClick = { selectedTab = HomeTab.LIVE },
                text = { Text(stringResource(id = R.string.tab_live)) })
            Tab(selectedTab == HomeTab.VOD, onClick = { selectedTab = HomeTab.VOD },
                text = { Text(stringResource(id = R.string.tab_vod)) })
            Tab(selectedTab == HomeTab.SERIES, onClick = { selectedTab = HomeTab.SERIES },
                text = { Text(stringResource(id = R.string.tab_series)) })
        }

        when (selectedTab) {
            HomeTab.LIVE -> LiveTab(onPlayLive)
            HomeTab.VOD -> VodTab(onOpenVod)
            HomeTab.SERIES -> SeriesTab(onOpenSeries)
        }
    }
}

@Composable
private fun LiveTab(onPlay: (LiveStream) -> Unit) {
    var streams by remember { mutableStateOf<List<LiveStream>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        loading = true
        streams = runCatching { AppState.repository?.liveStreams(null) ?: emptyList() }.getOrDefault(emptyList())
        loading = false
    }
    ContentGrid(loading, streams, label = { it.name }, icon = { it.icon }, onClick = onPlay)
}

@Composable
private fun VodTab(onOpen: (VodStream) -> Unit) {
    var streams by remember { mutableStateOf<List<VodStream>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        loading = true
        streams = runCatching { AppState.repository?.vodStreams(null) ?: emptyList() }.getOrDefault(emptyList())
        loading = false
    }
    ContentGrid(loading, streams, label = { it.name }, icon = { it.icon }, onClick = onOpen)
}

@Composable
private fun SeriesTab(onOpen: (SeriesItem) -> Unit) {
    var items by remember { mutableStateOf<List<SeriesItem>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        loading = true
        items = runCatching { AppState.repository?.series(null) ?: emptyList() }.getOrDefault(emptyList())
        loading = false
    }
    ContentGrid(loading, items, label = { it.name }, icon = { it.cover }, onClick = onOpen)
}

@Composable
private fun <T> ContentGrid(
    loading: Boolean,
    items: List<T>,
    label: (T) -> String,
    icon: (T) -> String?,
    onClick: (T) -> Unit
) {
    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        contentPadding = PaddingValues(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(items) { item ->
            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable { onClick(item) }
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = icon(item),
                    contentDescription = label(item),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(100.dp).clip(MaterialTheme.shapes.small)
                )
                Spacer(Modifier.height(6.dp))
                Text(label(item), maxLines = 2, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
