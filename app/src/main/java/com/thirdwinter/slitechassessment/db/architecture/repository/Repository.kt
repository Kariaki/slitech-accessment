package com.thirdwinter.slitechassessment.db.architecture.repository

import androidx.lifecycle.LiveData
import com.thirdwinter.slitechassessment.db.architecture.model.*

interface Repository {

    fun getAllCompetitions(): LiveData<List<Competition>>

    fun getCompetitionTeams(competitionId: Int): LiveData<List<Team>>
    fun getTeamSquad(teamId: Int): LiveData<List<Squad>>

    suspend fun insertCompetitions(onSuccess: () -> Unit, onError: () -> Unit)

    suspend fun insertCompetitionTeams(
        competitionId: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    )



    suspend fun insertTeamSquad(teamId: Int, onSuccess: () -> Unit, onError: () -> Unit)



}