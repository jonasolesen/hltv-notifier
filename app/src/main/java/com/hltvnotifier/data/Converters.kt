package com.hltvnotifier.data

import androidx.room.TypeConverter
import java.time.Instant
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return if (value == null) null else Instant.ofEpochMilli(value)
    }

    @TypeConverter
    fun toTimestamp(date: Instant?): Long? {
        return date?.toEpochMilli()
    }

}