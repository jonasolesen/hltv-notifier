package com.hltvnotifier.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.hltvnotifier.data.models.Subscription

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions")
    fun getAll(): LiveData<List<Subscription>>

    @Query("INSERT OR IGNORE INTO subscriptions (team_id) VALUES (:teamId)")
    suspend fun insert(teamId: Int)

    @Query("DELETE FROM subscriptions WHERE team_id = :teamId")
    suspend fun delete(teamId: Int)

    @Query("DELETE FROM subscriptions")
    suspend fun deleteAll()
}