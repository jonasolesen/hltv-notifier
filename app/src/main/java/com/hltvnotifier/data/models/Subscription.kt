package com.hltvnotifier.data.models

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class Subscription(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "team_id")
    @NonNull
    val teamId: Int,

    @ColumnInfo(name = "team_name")
    @NonNull
    val teamName: String
)