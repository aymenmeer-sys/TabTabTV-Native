package com.tabtab.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.tabtab.tv.core.sync.AggregationScheduler
import com.tabtab.tv.ui.navigation.TabTabNavHost
import com.tabtab.tv.ui.theme.TabTabTvTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AggregationScheduler.ensureScheduled(applicationContext)
        setContent {
            TabTabTvTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TabTabNavHost()
                }
            }
        }
    }
}
