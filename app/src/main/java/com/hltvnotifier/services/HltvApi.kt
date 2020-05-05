package com.hltvnotifier.services

import com.hltvnotifier.models.SearchResult
import com.hltvnotifier.models.Team
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HltvApi {
    @GET("team/{id}/-")
    suspend fun getTeam(@Path("id") id: Int): Team

    @GET("search")
    suspend fun findTeam(@Query("term") input: String): Response<List<SearchResult.Team>>
}