package com.hltvnotifier.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hltvnotifier.services.EventService
import com.hltvnotifier.stores.EventStore
import com.hltvnotifier.stores.SubscriptionStore
import kotlinx.coroutines.coroutineScope

class EventWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = coroutineScope {
        val subscriptions = SubscriptionStore.getSubscriptions()

        subscriptions.forEach { updateSubscription(it) }

        Result.success()
    }

    private suspend fun updateSubscription(id: Int) {
        val events = EventService.getFromTeamAsync(id).await()
        println("Found ${events.size} events")
        EventStore.saveEvents(events)
    }
}