package com.hltvnotifier.parsers

import org.jsoup.nodes.Document

abstract class ResourceParser<T> {
    abstract val clazz: Class<T>
    abstract fun fromDocument(document: Document): T
}