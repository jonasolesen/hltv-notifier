package com.hltvnotifier

import android.content.Context
import androidx.work.*
import com.hltvnotifier.data.AppDatabase
import com.hltvnotifier.data.daos.MatchDao
import com.hltvnotifier.data.daos.SubscriptionDao
import com.hltvnotifier.data.models.Match
import com.hltvnotifier.data.models.Subscription
import com.hltvnotifier.data.repositories.MatchRepository
import com.hltvnotifier.services.HltvApi
import com.hltvnotifier.services.HltvService
import com.hltvnotifier.services.NotificationService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatchWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        println("Updating matches")
        val database = AppDatabase.getDatabase(applicationContext, this)
        val matchRepo = MatchRepository(database.matchDao())

        val subscriptions = database.subscriptionDao().getAll()
        val matches = ArrayList<Match>()

        subscriptions.value?.forEach {
            matches.addAll(matchRepo.getUpdatedFromTeam(it.teamId))
        }

        NotificationService.setNotificationsForMatches(applicationContext, matches)

        Result.success()
    }
}