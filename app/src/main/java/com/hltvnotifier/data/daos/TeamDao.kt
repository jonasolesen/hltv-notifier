package com.hltvnotifier.data.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hltvnotifier.data.entities.TeamEntity

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getAll(): LiveData<List<TeamEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: TeamEntity)

    @Update
    suspend fun update(team: TeamEntity)

    @Query("DELETE FROM teams WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM teams")
    suspend fun deleteAll()
}