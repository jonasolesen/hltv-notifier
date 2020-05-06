package com.hltvnotifier.services

import com.google.gson.GsonBuilder
import com.hltvnotifier.data.models.SearchResult
import com.hltvnotifier.parsers.MatchParser
import com.hltvnotifier.parsers.TeamParser
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HltvService {
    private const val baseUrl = "https://hltv.org"

    fun getService(): HltvApi {
        val gson = GsonBuilder().registerTypeAdapterFactory(SearchResult.Team.Deserializer).create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JsoupConverterFactory(TeamParser))
            .addConverterFactory(JsoupConverterFactory(MatchParser))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(HltvApi::class.java)
    }
}