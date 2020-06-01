package com.hltvnotifier

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hltvnotifier.data.AppDatabase
import com.hltvnotifier.data.models.Match
import com.hltvnotifier.data.repositories.MatchRepository
import com.hltvnotifier.data.repositories.SubscriptionRepository
import com.hltvnotifier.services.NotificationService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MatchWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        val database = AppDatabase.getDatabase(applicationContext, this)
        val matchRepo = MatchRepository.getInstance(database.matchDao())
        val subscriptions =
            SubscriptionRepository.getInstance(database.subscriptionDao()).subscriptions

        val matches = ArrayList<Match>()

        subscriptions.value?.forEach {
            matches.addAll(matchRepo.getUpdatedFromTeam(it.teamId))
        }

        NotificationService.setNotificationsForMatches(applicationContext, matches)

        Result.success()
    }
}