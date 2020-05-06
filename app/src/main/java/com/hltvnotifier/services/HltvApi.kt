package com.hltvnotifier.services

import com.hltvnotifier.data.models.Match
import com.hltvnotifier.data.models.SearchResult
import com.hltvnotifier.data.models.Team
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HltvApi {
    @GET("team/{id}/-")
    suspend fun getTeam(@Path("id") id: Int): Team

    @GET("matches")
    suspend fun getMatchesFromTeam(@Query("team") id: Int): List<Match>

    @GET("search")
    suspend fun findTeam(@Query("term") input: String): Response<List<SearchResult.Team>>
}