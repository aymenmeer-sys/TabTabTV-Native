package com.tabtab.tv.core.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tabtab.tv.core.database.dao.ChannelDao
import com.tabtab.tv.core.database.dao.RadioStationDao
import com.tabtab.tv.core.database.toEntity
import com.tabtab.tv.core.network.ChannelDeduplicator
import com.tabtab.tv.core.network.IptvPlaylistClient
import com.tabtab.tv.core.network.RadioBrowserClient
import com.tabtab.tv.core.network.StreamVerifier
import com.tabtab.tv.core.network.runWithConcurrency
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * Downloads + parses + dedups + verifies + persists Live TV channels and radio
 * stations — entirely on-device, no backend server required (per project decision:
 * avoid the always-on server the web project used). Runs periodically via WorkManager.
 *
 * The DB is updated progressively as verification completes (batches of ~5%),
 * dropping confirmed-dead entries immediately — so the UI shows a shrinking-but-never-
 * wrong list instead of blocking on the entire ~10,000-item batch to finish.
 */
@HiltWorker
class ContentAggregationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val playlistClient: IptvPlaylistClient,
    private val radioClient: RadioBrowserClient,
    private val verifier: StreamVerifier,
    private val channelDao: ChannelDao,
    private val radioDao: RadioStationDao
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            aggregateChannels()
            aggregateRadios()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun aggregateChannels() {
        val raw = playlistClient.fetchAllChannels()
        val deduped = ChannelDeduplicator.dedup(raw, { it.streamUrl }, { it.name })
        val entities = deduped.mapIndexed { index, ch -> ch.toEntity(channelNumber = index + 1) }

        val aliveFlags = BooleanArray(entities.size)
        var checked = 0
        val progressStep = (entities.size / 20).coerceAtLeast(1)

        runWithConcurrency(entities.indices.toList(), concurrency = 20) { index ->
            val alive = verifier.isAlive(entities[index].streamUrl)
            aliveFlags[index] = alive
            checked++
            if (checked % progressStep == 0 || checked == entities.size) {
                val survivingSoFar = entities.filterIndexed { i, _ -> aliveFlags[i] || i >= checked }
                channelDao.clear()
                channelDao.upsertAll(survivingSoFar)
            }
        }

        val finalAlive = entities.filterIndexed { i, _ -> aliveFlags[i] }
        channelDao.clear()
        channelDao.upsertAll(finalAlive)
    }

    private suspend fun aggregateRadios() {
        val raw = radioClient.fetchStations()
        val deduped = ChannelDeduplicator.dedup(raw, { it.streamUrl }, { it.name })
        val entities = deduped.map { it.toEntity() }

        val aliveFlags = BooleanArray(entities.size)
        var checked = 0
        val progressStep = (entities.size / 20).coerceAtLeast(1)

        runWithConcurrency(entities.indices.toList(), concurrency = 20) { index ->
            val alive = verifier.isAlive(entities[index].streamUrl)
            aliveFlags[index] = alive
            checked++
            if (checked % progressStep == 0 || checked == entities.size) {
                val survivingSoFar = entities.filterIndexed { i, _ -> aliveFlags[i] || i >= checked }
                radioDao.clear()
                radioDao.upsertAll(survivingSoFar)
            }
        }

        val finalAlive = entities.filterIndexed { i, _ -> aliveFlags[i] }
        radioDao.clear()
        radioDao.upsertAll(finalAlive)
    }
}
