package com.hltvnotifier.parsers

import com.hltvnotifier.data.entities.MatchEntity
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.Instant

object MatchParser {
    fun parseDocument(document: Document, teamId: Int): List<MatchEntity> {
        return document.getElementsByClass("match").map { element -> parseMatch(element, teamId) }
    }

    private fun parseMatch(element: Element, teamId: Int): MatchEntity {
        val id = parseId(element)
        val teams = element.getElementsByClass("team")
        val team1 = teams[0].text()
        val team2 = teams[1].text()
        val date = Instant.ofEpochMilli(element.getElementsByAttribute("data-unix").first().attr("data-unix").toLong())
        return MatchEntity(id, teamId, team1, team2, date)
    }

    private fun parseId(element: Element): Int {
        val regex = Regex("""/matches/(\d*)/""")
        val text = element.getElementsByClass("a-reset").first().attr("href")

        return regex.find(text)!!.groupValues[1].toInt()
    }
}