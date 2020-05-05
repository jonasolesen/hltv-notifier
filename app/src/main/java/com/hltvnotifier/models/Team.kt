package com.hltvnotifier.models

import com.hltvnotifier.parsers.TeamParser
import org.jsoup.nodes.Document

class Team(private val document: Document) : JsoupResource(document) {
    val id: Int by lazy { TeamParser.parseId(document) }
    val ranking: Int by lazy { TeamParser.parseRanking(this.document) }
}