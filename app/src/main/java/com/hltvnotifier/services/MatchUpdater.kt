package com.hltvnotifier.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.hltvnotifier.data.AppDatabase
import com.hltvnotifier.data.models.Match
import com.hltvnotifier.data.repositories.MatchRepository
import com.hltvnotifier.receivers.MatchUpdaterReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

object MatchUpdater {

    fun updateMatchesFromSubscriptions(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(context, this)
            val matchRepo = MatchRepository(database.matchDao())

            val subscriptions = database.subscriptionDao().getAll()

            val matches = ArrayList<Match>()

            for (subscription in subscriptions.value!!) {
                matches.addAll(matchRepo.getUpdatedFromTeam(subscription.teamId))
            }

            NotificationService.setNotificationsForMatches(context, matches)
        }
    }


    fun createServiceUpdater(context: Context) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, MatchUpdaterReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)


        alarmMgr.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis() + getMillisecondsTillNextMidnight(), AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    private fun getMillisecondsTillNextMidnight(): Long {
        val c = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 1) //add a day first
        c.set(Calendar.HOUR_OF_DAY, 0) //then set the other fields to 0'
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c.timeInMillis - System.currentTimeMillis()
    }
}