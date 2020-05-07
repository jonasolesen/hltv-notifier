package com.hltvnotifier.data.repositories

import androidx.lifecycle.LiveData
import com.hltvnotifier.data.daos.MatchDao
import com.hltvnotifier.data.models.Match
import com.hltvnotifier.services.HltvService

class MatchRepository(private val matchDao: MatchDao) {
    val matches: LiveData<List<Match>> = matchDao.getAll()

    suspend fun getFromTeam(id: Int): List<Match> {
        if (!matches.value.isNullOrEmpty() && matches.value!!.any { match -> match.teamId == id }) {
            return matches.value!!.filter { match -> match.teamId == id }
        }

        return HltvService.getService().getMatchesFromTeam(id)
            .apply { forEach { matchDao.insert(it) } }
    }

    fun getMatch(id: Int): Match? {
        if (matches.value!!.any { it.id == id }) {
            println("Team found locally")
            return matches.value!!.find { it.id == id }!!
        }
        return null
     }

    companion object {
        @Volatile
        private var instance: MatchRepository? = null

        fun getInstance(matchDao: MatchDao) =
            instance ?: synchronized(this) {
                instance ?: MatchRepository(matchDao).also { instance = it }
            }
    }
}