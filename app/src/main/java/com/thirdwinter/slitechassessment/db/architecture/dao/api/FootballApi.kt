package com.thirdwinter.slitechassessment.db.architecture.dao.api

import com.thirdwinter.slitechassessment.db.architecture.dao.api.response.*
import com.thirdwinter.slitechassessment.util.Constants.apiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FootballApi {


    @GET("competitions?plan=TIER_ONE")
    @Headers("X-Auth-Token: $apiKey")
    suspend fun getCompetitions(): Response<CompetitionResponse>

    @GET("competitions/{competitionId}/teams")
    @Headers("X-Auth-Token: $apiKey")
    suspend fun getTeamForCompetition(@Path("competitionId") competitionId: Int?): Response<TeamResponse>


    @GET("teams/{teamId}")
    @Headers("X-Auth-Token: $apiKey")
    suspend fun getTeamSquad(@Path("teamId") teamId: Int?): Response<SquadResponse>




}