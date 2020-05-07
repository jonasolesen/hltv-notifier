package com.hltvnotifier.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hltvnotifier.data.models.Subscription

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions")
    fun getAll(): LiveData<List<Subscription>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sub: Subscription)

    @Query("DELETE FROM subscriptions WHERE team_id = :teamId")
    suspend fun delete(teamId: Int)

    @Query("SELECT * FROM subscriptions WHERE team_id = :teamId LIMIT 1")
    suspend fun isSubscribed(teamId: Int): Subscription?

    @Query("DELETE FROM subscriptions")
    suspend fun deleteAll()
}