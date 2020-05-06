package com.hltvnotifier.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hltvnotifier.data.models.Match

@Dao
interface MatchDao {
    @Query("SELECT * FROM matches")
    fun getAll(): LiveData<List<Match>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(match: Match)

    @Update
    suspend fun update(match: Match)

    @Query("SELECT * FROM matches WHERE teamId = :teamId")
    suspend fun getAllForTeam(teamId: Int): List<Match>

    @Query("DELETE FROM matches WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM matches")
    suspend fun deleteAll()
}