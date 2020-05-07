package com.hltvnotifier.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.core.content.ContextCompat.getSystemService
import com.hltvnotifier.data.models.Match
import com.hltvnotifier.receivers.AlarmReceiver
import java.time.LocalTime
import java.time.ZoneId

object NotificationService {

    fun setNotificationsForMatches(context: Context, matches: List<Match>) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        // For testing
        //var index = 1
        for (match in matches) {
            val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("matchId", match.id)
            }
            val pendingIntent = PendingIntent.getBroadcast(context, match.id, alarmIntent, 0)

            alarmMgr.setExact(
                //AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000 * index,
                AlarmManager.RTC_WAKEUP, match.date.toEpochMilli(),
                pendingIntent)

            // For testing
            // index++
        }
    }

    fun cancelNotificationsForMatches(context: Context, matches: List<Match>) {
        val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (match in matches) {
            val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra("matchId", match.id)
            }
            val pendingIntent = PendingIntent.getBroadcast(context, match.id, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            alarmMgr.cancel(pendingIntent)
        }
    }

}