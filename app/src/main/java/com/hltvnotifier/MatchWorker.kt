package com.hltvnotifier

import android.content.Context
import androidx.work.*
import com.hltvnotifier.data.AppDatabase
import com.hltvnotifier.data.daos.MatchDao
import com.hltvnotifier.data.daos.SubscriptionDao
import com.hltvnotifier.data.models.Subscription
import com.hltvnotifier.services.HltvApi
import com.hltvnotifier.services.HltvService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    private var matchDao: MatchDao
    private var subscriptionDao: SubscriptionDao
    private var hltvService: HltvApi

    init {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        matchDao = AppDatabase.getDatabase(context, coroutineScope).matchDao()
        subscriptionDao = AppDatabase.getDatabase(context, coroutineScope).subscriptionDao()
        hltvService = HltvService.getService()
    }


    override suspend fun doWork(): Result {
//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()

        val subscriptions = subscriptionDao.getAll()
        if (subscriptions.value.isNullOrEmpty()) return Result.success()

        subscriptions.value!!.forEach {
            hltvService.getMatchesFromTeam(it.teamId).forEach { match -> matchDao.insert(match) }
        }

        return Result.success()
    }
}