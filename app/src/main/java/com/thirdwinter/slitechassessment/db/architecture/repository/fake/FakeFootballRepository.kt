package com.thirdwinter.slitechassessment.db.architecture.repository.fake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thirdwinter.slitechassessment.db.architecture.dao.api.fakeApi.FakeFootballApiDatasource
import com.thirdwinter.slitechassessment.db.architecture.model.*
import com.thirdwinter.slitechassessment.db.architecture.repository.Repository

class FakeFootballRepository(private val dataSource: FakeFootballApiDatasource) : Repository {


    val competitions: MutableList<Competition> = mutableListOf()
    val squads: MutableList<Squad> = mutableListOf()
    val teams: MutableList<Team> = mutableListOf()


    override fun getAllCompetitions(): LiveData<List<Competition>> {
        return MutableLiveData(competitions)
    }

    override fun getCompetitionTeams(competitionId: Int): LiveData<List<Team>> {

        val filtered = teams.filter { it.competitionId == competitionId }
        return MutableLiveData(filtered)
    }

    override fun getTeamSquad(teamId: Int): LiveData<List<Squad>> {

        val filtered = squads.filter { it.teamId == teamId }

        return MutableLiveData(filtered)
    }

    override suspend fun insertCompetitions(onSuccess: () -> Unit, onError: () -> Unit) {
        competitions.clear()
        dataSource.getAllCompetitions().forEach {
            competitions.add(it)
        }
        onSuccess()

    }

    override suspend fun insertCompetitionTeams(
        competitionId: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        teams.clear()
        dataSource.getTeams().forEach {
            it.competitionId = competitionId
            teams.add(it)
        }
        onSuccess()

    }

    override suspend fun insertTeamSquad(teamId: Int, onSuccess: () -> Unit, onError: () -> Unit) {
        dataSource.getSquads().forEach {
            it.teamId = teamId
            squads.add(it)
        }

        onSuccess()
    }


}