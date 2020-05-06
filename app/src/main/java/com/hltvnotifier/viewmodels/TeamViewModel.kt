package com.hltvnotifier.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class TeamViewModel(application: Application) : AndroidViewModel(application) {
//    val teams: LiveData<List<TeamEntity>>
//
//    init {
//        val teamDao = AppDatabase.getDatabase(application, viewModelScope).teamDao()
//        repository = TeamRepository.getInstance(teamDao)
//        teams = repository.teams
//    }
//
//    suspend fun getFromId(id: Int): TeamEntity {
//        return repository.getFromId(id)
//    }
}