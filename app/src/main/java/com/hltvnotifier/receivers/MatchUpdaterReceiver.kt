package com.hltvnotifier.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import com.hltvnotifier.services.MatchUpdater

class MatchUpdaterReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val pm =
            context!!.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "hltv-notifier::match-update")
        wl.acquire(10*60*1000L /*10 minutes*/)
        MatchUpdater.updateMatchesFromSubscriptions(context)
        wl.release()
    }
}