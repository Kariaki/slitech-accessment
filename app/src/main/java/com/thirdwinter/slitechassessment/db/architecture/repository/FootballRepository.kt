package com.thirdwinter.slitechassessment.db.architecture.repository

import androidx.lifecycle.LiveData
import com.thirdwinter.slitechassessment.db.architecture.dao.FootballDao
import com.thirdwinter.slitechassessment.db.architecture.dao.api.FootballApi
import com.thirdwinter.slitechassessment.db.architecture.model.*
import com.thirdwinter.slitechassessment.util.DateTimeUtils

class FootballRepository(private val api: FootballApi, private val dao: FootballDao) : Repository {

    override fun getAllCompetitions(): LiveData<List<Competition>> = dao.getCompetitions()

    override fun getCompetitionTeams(competitionId: Int): LiveData<List<Team>> =
        dao.getCompetitionTeams(competitionId)

    override fun getTeamSquad(teamId: Int): LiveData<List<Squad>> = dao.getTeamSquad(teamId)


    override suspend fun insertCompetitions(onSuccess: () -> Unit, onError: () -> Unit) {
        val competitionResponse = api.getCompetitions()
        if (competitionResponse.isSuccessful) {
            val body = competitionResponse.body()?.competitions
            body?.forEach {
                dao.insertCompetition(it)
            }

            onSuccess()
        } else {
            onError()
        }
    }


    override suspend fun insertCompetitionTeams(
        competitionId: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val competitionResponse = api.getTeamForCompetition(competitionId)
        if (competitionResponse.isSuccessful) {
            val body = competitionResponse.body()?.teams

            body?.forEach {

                it.competitionId = competitionId
                dao.insertTeam(it)
            }

            onSuccess()
        } else {
            onError()
        }
    }


    override suspend fun insertTeamSquad(teamId: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        val competitionResponse = api.getTeamSquad(teamId)
        if (competitionResponse.isSuccessful) {
            val body = competitionResponse.body()?.squad
            body?.forEach {
                it.teamId = teamId
                it.dateOfBirth = DateTimeUtils.getDate(it.dateOfBirth!!)
                dao.insertSquad(it)
            }

            onSuccess()
        } else {
            onError()
        }
    }


}