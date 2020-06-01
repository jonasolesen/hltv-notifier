package com.hltvnotifier.parsers

import com.hltvnotifier.data.models.Team
import org.jsoup.nodes.Document

object TeamParser : ResourceParser<Team>() {
    override val clazz = Team::class.java

    override fun parse(document: Document) = Team(
        parseId(document),
        parseRanking(document),
        parseName(document),
        parseCountry(document),
        parseFlagUrl(document)
    )

    private fun parseFlagUrl(document: Document): String {
        return document.getElementsByClass("team-country")
            .first()
            .getElementsByTag("img")
            .first()
            .attr("src")
    }

    private fun parseId(document: Document) =
        document.getElementsByClass("profile-team-logo-container")
            .first()
            .getElementsByTag("img")
            .first()
            .attr("src")
            .split("/")
            .last()
            .toInt()

    private fun parseRanking(document: Document): String {
        return document
            .getElementsByClass("profile-team-stat")[0]
            .getElementsByTag("a")
            .text()
    }

    private fun parseName(document: Document): String {
        return document.getElementsByClass("profile-team-name")
            .first()
            .text()
    }

    private fun parseCountry(document: Document): String {
        return document.getElementsByClass("team-country")
            .first()
            .text()
    }

    override fun parseMultiple(document: Document): List<Team> {
        TODO("Not yet implemented")
    }
}