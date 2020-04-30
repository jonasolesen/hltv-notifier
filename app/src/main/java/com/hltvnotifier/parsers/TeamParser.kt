package com.hltvnotifier.parsers

import com.hltvnotifier.data.entities.TeamEntity
import org.jsoup.nodes.Document

object TeamParser {
    fun parseDocument(document: Document): TeamEntity {
        return TeamEntity(
            parseId(document),
            parseName(document),
            parseRanking(document),
            parseCountry(document)
        )
    }

    fun parseId(document: Document) = 6665

    fun parseName(document: Document) = "Team"

    fun parseCountry(document: Document) = "Country"

    fun parseRanking(document: Document?): Int {
        if (document == null) return 1

        return document
            .getElementsByClass("profile-team-stat")[0]
            .getElementsByTag("a")
            .text()
            .substring(1)
            .toInt()
    }
}