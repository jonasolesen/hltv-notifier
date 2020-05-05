package com.hltvnotifier.services

import com.hltvnotifier.data.entities.MatchEntity
import com.hltvnotifier.parsers.EventParser
import com.hltvnotifier.parsers.MatchParser
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

object MatchService: WebService() {
    fun getFromTeamAsync(id: Int): Deferred<List<MatchEntity>> {
        return MatchService.coroutineScope.async {
            val url = "https://www.hltv.org/matches?team=$id"
            MatchParser.parseDocument(getDocument(url), id)
        }
    }
}