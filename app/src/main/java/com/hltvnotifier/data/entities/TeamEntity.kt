package com.hltvnotifier.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hltvnotifier.parsers.TeamParser
import org.jetbrains.annotations.NotNull
import org.jsoup.nodes.Document

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @NotNull val name: String,
    @NotNull val ranking: Int,
    val country: String
) {

    companion object {
        fun create(document: Document) = TeamParser.parseDocument(document)
    }
}