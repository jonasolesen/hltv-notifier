package com.hltvnotifier.data.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "team_id")
    @NonNull
    val teamId: Int
)