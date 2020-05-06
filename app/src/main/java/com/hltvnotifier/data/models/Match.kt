package com.hltvnotifier.data.models

import androidx.annotation.NonNull
import androidx.room.*
import java.util.*

@Entity(tableName = "matches")
data class Match(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @NonNull
    val id: Int,

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
    val date: Date
)