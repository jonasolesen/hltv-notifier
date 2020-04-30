package com.hltvnotifier

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hltvnotifier.stores.SubscriptionStore

class SubscriptionWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val subscriptions = SubscriptionStore.getSubscriptions()
        if (subscriptions.isEmpty()) return Result.success()


        TODO("Not yet implemented")
    }

    fun getEvents(id: Int) {

    }
}