package com.hltvnotifier.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hltvnotifier.data.AppDatabase
import com.hltvnotifier.data.entities.TeamEntity
import com.hltvnotifier.data.repositories.TeamRepository

class TeamViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TeamRepository
    val teams: LiveData<List<TeamEntity>>

    init {
        val teamDao = AppDatabase.getDatabase(application, viewModelScope).teamDao()
        repository = TeamRepository.getInstance(teamDao)
        teams = repository.teams
    }

    suspend fun getFromId(id: Int): TeamEntity {
        return repository.getFromId(id)
    }
}