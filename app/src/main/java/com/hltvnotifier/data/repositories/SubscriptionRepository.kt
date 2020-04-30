package com.hltvnotifier.data.repositories

import androidx.lifecycle.LiveData
import com.hltvnotifier.data.daos.SubscriptionDao
import com.hltvnotifier.data.entities.SubscriptionEntity

class SubscriptionRepository(private val subscriptionDao: SubscriptionDao) {
    val subscriptions: LiveData<List<SubscriptionEntity>> = subscriptionDao.getAll()

    suspend fun subscribe(teamId: Int) {
        println("Saving team id $teamId")
        subscriptionDao.insert(teamId)
    }

    suspend fun unsubscribe(teamId: Int) {
        subscriptionDao.delete(teamId)
    }

    companion object {
        @Volatile
        private var instance: SubscriptionRepository? = null

        fun getInstance(subscriptionDao: SubscriptionDao) =
            instance ?: synchronized(this) {
                instance ?: SubscriptionRepository(subscriptionDao).also { instance = it }
            }
    }
}