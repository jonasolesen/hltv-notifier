package com.hltvnotifier.parsers

import com.hltvnotifier.data.models.Team
import org.jsoup.nodes.Document

object TeamParser : ResourceParser<Team>() {
    override val clazz = Team::class.java

    override fun parse(document: Document) = Team(
        parseId(document),
        parseRanking(document),
        parseName(document),
        parseCountry(document)
    )

    private fun parseId(document: Document) =
        document.getElementsByClass("profile-team-logo-container")
            .first()
            .getElementsByTag("img")
            .first()
            .attr("src")
            .split("/")
            .last()
            .toInt()

    private fun parseRanking(document: Document): Int {
        return document
            .getElementsByClass("profile-team-stat")[0]
            .getElementsByTag("a")
            .text()
            .substring(1)
            .toInt()
    }

    private fun parseName(document: Document) = "Team"

    private fun parseCountry(document: Document) = "Country"

    override fun parseMultiple(document: Document): List<Team> {
        TODO("Not yet implemented")
    }
}