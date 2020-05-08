package com.hltvnotifier.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import com.hltvnotifier.services.MatchUpdater
import com.hltvnotifier.services.NotificationService

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val pm =
            context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "hltv-notifier::boot")
        wl.acquire(10*60*1000L /*10 minutes*/)

        // Update matches on boot and set alarms
        MatchUpdater.updateMatchesFromSubscriptions(context)

        // Start the service for the updater to run every 24 hours
        MatchUpdater.createServiceUpdater(context)

        wl.release()
    }
}