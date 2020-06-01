package com.hltvnotifier.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.hltvnotifier.data.models.Team
import com.hltvnotifier.services.HltvService

class TeamViewModel(application: Application) : AndroidViewModel(application) {
    private val hltvService = HltvService.getService()
    private val teams = MutableLiveData<MutableList<Team>>()

    suspend fun getFromId(id: Int): Team {
        if (!teams.value.isNullOrEmpty() && teams.value!!.any { match -> match.id == id }) {
            return teams.value!!.find { team -> team.id == id }!!
        }

        return hltvService.getTeam(id).also { teams.value?.add(it) }
    }
}