package com.hltvnotifier.services

import android.app.IntentService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

abstract class WebService() {
    protected val coroutineScope = CoroutineScope(Dispatchers.IO)

    protected fun getDocument(url: String): Document {
        try {
            return Jsoup.connect(url).get()
        } catch (e: Throwable) {
            throw e
        }
    }
}