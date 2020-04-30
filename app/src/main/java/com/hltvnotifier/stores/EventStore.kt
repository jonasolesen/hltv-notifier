package com.hltvnotifier.stores

import com.hltvnotifier.models.Event

object EventStore {
    suspend fun saveEvents(events: List<Event>) {
        println("Saving events")
    }
}