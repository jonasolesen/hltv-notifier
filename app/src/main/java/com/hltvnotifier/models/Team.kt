package com.hltvnotifier.models

import com.hltvnotifier.parsers.TeamParser
import org.jsoup.nodes.Document

class Team(private val document: Document) {
    val ranking : Int by lazy { TeamParser.parseRanking(this.document) }

    private fun parseRanking(document: Document): Int {
        println("parsing ranking")
        return document
            .getElementsByClass("profile-team-stat")[0]
            .getElementsByTag("a")
            .text()
            .substring(1)
            .toInt()
    }
}