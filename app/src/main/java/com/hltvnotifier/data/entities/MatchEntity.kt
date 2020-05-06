package com.hltvnotifier.data.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "matches")
data class MatchEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @NonNull
    val Id: Int,

    @ColumnInfo(name = "teamId")
    @NonNull
    val teamId: Int,

    @ColumnInfo(name = "team1")
    @NonNull
    val team1: String,

    @ColumnInfo(name = "team2")
    @NonNull
    val team2: String,

    @ColumnInfo(name = "date")
    @NonNull
    val date: Instant
) {

}