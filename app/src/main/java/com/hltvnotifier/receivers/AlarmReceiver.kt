package com.hltvnotifier.receivers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hltvnotifier.R
import com.hltvnotifier.data.AppDatabase
import com.hltvnotifier.views.TeamActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val pm =
            context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "hltv-notifier::alarm")
        wl.acquire(10*60*1000L /*10 minutes*/)

        val matchId = intent!!.getIntExtra("matchId", 0)

        CoroutineScope(Dispatchers.IO).launch {
            val matchDao = AppDatabase.getDatabase(context, this)
            val match = matchDao.matchDao().get(matchId)

            val intent = Intent(context, TeamActivity::class.java).apply {
                putExtra("TeamId", match.teamId)
            }

            val notification = NotificationCompat.Builder(context, context.getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(match.team1 + " vs " + match.team2)
                .setContentTitle("CSGO Match")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0))
                .build()

            with(NotificationManagerCompat.from(context)) {
                notify(matchId, notification)
            }
        }
        wl.release()

    }


}