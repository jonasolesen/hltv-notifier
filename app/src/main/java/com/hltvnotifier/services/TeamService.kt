package com.hltvnotifier.services

import com.hltvnotifier.data.entities.TeamEntity
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async

object TeamService : WebService() {
    private val teams = HashMap<Int, TeamEntity>()

    fun getTeamAsync(id: Int): Deferred<TeamEntity> {
        if (teams.containsKey(id)) {
            println("Getting team from cache")
            return coroutineScope.async { teams[id]!! }
        }

        return coroutineScope.async {
            val url = "https://www.hltv.org/team/$id/-"
            TeamEntity.create(getDocument(url))
        }
    }
}