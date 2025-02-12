package com.hltvnotifier.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hltvnotifier.data.AppDatabase
import com.hltvnotifier.data.models.Subscription
import com.hltvnotifier.data.repositories.SubscriptionRepository
import kotlinx.coroutines.launch

class SubscriptionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SubscriptionRepository
    val subscriptions: LiveData<List<Subscription>>

    init {
        val subscriptionDao = AppDatabase.getDatabase(application, viewModelScope).subscriptionDao()
        repository = SubscriptionRepository.getInstance(subscriptionDao)
        subscriptions = repository.subscriptions
    }

    suspend fun subscribe(sub: Subscription) = viewModelScope.launch {
        repository.subscribe(sub)
    }

    suspend fun unsubscribe(id: Int) = viewModelScope.launch {
        repository.unsubscribe(id)
    }

    suspend fun isSubscribed(teamId: Int): Boolean {
        return repository.isSubscribed(teamId)
    }
}