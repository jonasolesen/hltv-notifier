package com.hltvnotifier.models

import com.hltvnotifier.parsers.TeamParser
import org.jsoup.nodes.Document

class Team(private val document: Document) {
    val ranking : Int by lazy { TeamParser.parseRanking(this.document) }
    val name : String by lazy { TeamParser.parseName(this.document) }
}