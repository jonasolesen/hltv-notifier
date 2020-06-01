package com.hltvnotifier.services

import android.content.Context
import androidx.work.*
import com.hltvnotifier.MatchWorker
import java.util.concurrent.TimeUnit

object MatchUpdater {
    fun updateMatchesFromSubscriptions(context: Context) {
        val workManager = WorkManager.getInstance(context)
        workManager.enqueue(OneTimeWorkRequest.from(MatchWorker::class.java))
    }

    fun createServiceUpdater(context: Context) {
        println("Creating service updater")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val myWork = PeriodicWorkRequest.Builder(MatchWorker::class.java, 1, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "UpdateMatches",
            ExistingPeriodicWorkPolicy.KEEP,
            myWork
        )
    }
}