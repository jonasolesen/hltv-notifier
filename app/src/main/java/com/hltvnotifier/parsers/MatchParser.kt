package com.hltvnotifier.parsers

import com.hltvnotifier.data.models.Match
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.Instant
import java.util.*

object MatchParser : ResourceParser<Match>() {
    override val clazz = Match::class.java

    override fun parse(document: Document): Match {
        TODO("Not yet implemented")
    }

    override fun parseMultiple(document: Document): List<Match> {
        val teamId: Int = document.getElementsByClass("sidebar-single-line-item selected")
            .first()
            .getElementsByTag("a")
            .first()
            .attr("href")
            .split("=")
            .last()
            .toInt()

        return parseDocument(document, teamId)
    }

    private fun parseDocument(document: Document, teamId: Int): List<Match> {
        return document.getElementsByClass("match").map { element -> parseMatch(element, teamId) }
    }

    private fun parseMatch(element: Element, teamId: Int): Match {
        val id = parseId(element)
        val teams = element.getElementsByClass("team")
        val team1 = teams[0].text()
        val team2 = teams[1].text()
        val date = Instant.ofEpochMilli(
            element.getElementsByAttribute("data-unix").first().attr("data-unix").toLong()
        )
        return Match(
            id,
            teamId,
            team1,
            team2,
            Date.from(date)
        )
    }

    private fun parseId(element: Element): Int {
        val regex = Regex("""/matches/(\d*)/""")
        val text = element.getElementsByClass("a-reset").first().attr("href")

        return regex.find(text)!!.groupValues[1].toInt()
    }
}