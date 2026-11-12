package com.tabtab.tv.core.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

private const val PERIODIC_WORK_NAME = "content_aggregation_periodic"
private const val FIRST_RUN_WORK_NAME = "content_aggregation_first_run"

object AggregationScheduler {
    /** Idempotent: safe to call on every app start (e.g. from Application.onCreate). */
    fun ensureScheduled(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Periodic requests only fire after their first interval elapses — kick off an
        // immediate one-off run too, so a fresh install isn't empty for 12 hours.
        val firstRun = OneTimeWorkRequestBuilder<ContentAggregationWorker>()
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(context).enqueueUniqueWork(
            FIRST_RUN_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            firstRun
        )

        val periodic = PeriodicWorkRequestBuilder<ContentAggregationWorker>(12, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            PERIODIC_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodic
        )
    }
}
