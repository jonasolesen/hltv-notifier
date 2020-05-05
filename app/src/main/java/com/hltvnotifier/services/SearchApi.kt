package com.hltvnotifier.services

import com.hltvnotifier.models.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search")
    suspend fun findTeam(@Query("term") input: String): Response<List<SearchResult.Team>>
}