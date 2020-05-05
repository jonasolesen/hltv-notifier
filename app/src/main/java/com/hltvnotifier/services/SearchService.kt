package com.hltvnotifier.services

import com.google.gson.GsonBuilder
import com.hltvnotifier.models.SearchResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SearchService {
    private const val baseUrl = "https://hltv.org"

    fun getSearchService(): SearchApi {
        val gson = GsonBuilder().registerTypeAdapterFactory(SearchResult.Team.Deserializer).create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(SearchApi::class.java)
    }
}