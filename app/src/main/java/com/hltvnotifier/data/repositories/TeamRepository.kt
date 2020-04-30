package com.hltvnotifier.data.repositories

import androidx.lifecycle.LiveData
import com.hltvnotifier.data.daos.TeamDao
import com.hltvnotifier.data.entities.TeamEntity
import com.hltvnotifier.services.TeamService

class TeamRepository(private val teamDao: TeamDao) {
    val teams: LiveData<List<TeamEntity>> = teamDao.getAll()

    suspend fun getFromId(id: Int): TeamEntity {
        println("Teams: ${teams.value?.size}")
        if (teams.value!!.any { it.id == id }) {
            println("Team found locally")
            return teams.value!!.find { it.id == id }!!
        }

        TeamService.getTeamAsync(id).await().let {
            println("Fetching team from remote")
            insert(it)
            return it
        }
    }

    suspend fun insert(team: TeamEntity) {
        teamDao.insert(team)
    }

    suspend fun delete(id: Int) {
        teamDao.delete(id)
    }

    companion object {
        @Volatile
        private var instance: TeamRepository? = null

        fun getInstance(teamDao: TeamDao) =
            instance ?: synchronized(this) {
                instance ?: TeamRepository(teamDao).also { instance = it }
            }
    }
}