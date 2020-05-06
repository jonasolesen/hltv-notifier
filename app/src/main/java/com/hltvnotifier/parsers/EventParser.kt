package com.hltvnotifier.parsers

import com.hltvnotifier.data.models.Event
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

object EventParser {
    fun parseDocument(document: Document): List<Event> {
        return document.getElementsByClass("events-month").map { parse(it) }
            .reduce { acc, events -> acc + events }
    }

    private fun parse(element: Element): List<Event> {
        val events = mutableListOf<Event>()
        if (!element.getElementsByClass("small-event").isEmpty()) {
            events.addAll(parseSmallEvents(element.getElementsByClass("small-event").first()))
        }
        if (!element.getElementsByClass("big-events").isEmpty()) {
            events.addAll(parseBigEvents(element.getElementsByClass("big-events").first()))
        }

        return events
    }

    private fun parseSmallEvents(element: Element): List<Event> {
        val events = mutableListOf<Event>()

        element.getElementsByClass("small-event").forEach { event ->
            val id = parseId(event)
            val (name, teams, prize) = event.getElementsByClass("col-value").map { it.text() }
            events.add(Event(id, name, teams, prize))
        }

        return events
    }

    private fun parseBigEvents(element: Element): List<Event> {
        val events = mutableListOf<Event>()

        element.getElementsByClass("big-event").forEach { event ->
            if (!event.children().isEmpty()) {
                val id = parseId(event)
                val name = event.getElementsByClass("big-event-name").first().text()
                val (_, prize, teams) = event.getElementsByClass("col-value").map { it.text() }
                events.add(Event(id, name, teams, prize))
            }
        }

        return events
    }

    private fun parseId(element: Element): Int {
        val regex = Regex("""/events/(\d*)/""")
        val text = element.getElementsByTag("a").first().attr("href")

        return regex.find(text)!!.groupValues[1].toInt()
    }
}