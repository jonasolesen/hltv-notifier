package com.hltvnotifier.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hltvnotifier.data.entities.MatchEntity

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches")
    fun getAll(): LiveData<List<MatchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: MatchEntity)

    @Update
    suspend fun update(match: MatchEntity)

    @Query("SELECT * FROM matches WHERE teamId = :teamId")
    suspend fun getAllForTeam(teamId: Int)

    @Query("DELETE FROM matches WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM matches")
    suspend fun deleteAll()
}