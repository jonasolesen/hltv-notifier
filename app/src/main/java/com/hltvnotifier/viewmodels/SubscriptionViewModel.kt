package com.hltvnotifier.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hltvnotifier.data.AppDatabase
import com.hltvnotifier.data.entities.SubscriptionEntity
import com.hltvnotifier.data.repositories.SubscriptionRepository
import kotlinx.coroutines.launch

class SubscriptionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SubscriptionRepository
    val subscriptions: LiveData<List<SubscriptionEntity>>

    init {
        val subscriptionDao = AppDatabase.getDatabase(application, viewModelScope).subscriptionDao()
        repository = SubscriptionRepository(subscriptionDao)
        subscriptions = repository.subscriptions
    }

    suspend fun subscribe(id: Int) = viewModelScope.launch {
        repository.subscribe(id)
    }

    suspend fun unsubscribe(id: Int) = viewModelScope.launch {
        repository.unsubscribe(id)
    }
}