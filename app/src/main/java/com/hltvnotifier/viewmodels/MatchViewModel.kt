package com.hltvnotifier.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hltvnotifier.data.AppDatabase
import com.hltvnotifier.data.models.Match
import com.hltvnotifier.data.repositories.MatchRepository

class MatchViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: MatchRepository
    var matches: LiveData<List<Match>>

    init {
        val matchDao = AppDatabase.getDatabase(application, viewModelScope).matchDao()
        repository = MatchRepository.getInstance(matchDao)
        matches = repository.matches
    }

    suspend fun getFromTeam(teamId: Int): List<Match> {
        return repository.getFromTeam(teamId)
    }
}