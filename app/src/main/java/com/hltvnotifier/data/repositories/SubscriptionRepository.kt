package com.hltvnotifier.data.repositories

import androidx.lifecycle.LiveData
import com.hltvnotifier.data.daos.SubscriptionDao
import com.hltvnotifier.data.models.Subscription

class SubscriptionRepository(private val subscriptionDao: SubscriptionDao) {
    val subscriptions: LiveData<List<Subscription>> = subscriptionDao.getAll()

    suspend fun subscribe(sub: Subscription) {
        subscriptionDao.insert(sub)
    }

    suspend fun unsubscribe(teamId: Int) {
        subscriptionDao.delete(teamId)
    }

    suspend fun isSubscribed(teamId: Int): Boolean {
        return subscriptionDao.isSubscribed(teamId) != null
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