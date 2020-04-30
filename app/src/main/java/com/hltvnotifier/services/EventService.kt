package com.hltvnotifier.services

import com.hltvnotifier.models.Event
import com.hltvnotifier.parsers.EventParser
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

object EventService : WebService() {
    fun getFromTeamAsync(id: Int): Deferred<List<Event>> {
        return coroutineScope.async {
            val url = "https://www.hltv.org/events?team=$id"
            EventParser.parseDocument(getDocument(url))
        }
    }
}