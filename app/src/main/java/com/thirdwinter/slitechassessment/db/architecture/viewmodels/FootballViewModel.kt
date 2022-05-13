package com.thirdwinter.slitechassessment.db.architecture.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thirdwinter.slitechassessment.db.architecture.model.*
import com.thirdwinter.slitechassessment.db.architecture.repository.Repository
import com.thirdwinter.slitechassessment.util.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FootballViewModel @Inject constructor(@Named("datasource") private val repository: Repository) :
    ViewModel() {

    private val _loadState: MutableLiveData<LoadState> = MutableLiveData(LoadState.LOADING)
    val loadState: LiveData<LoadState> = _loadState

    val competitions: LiveData<List<Competition>> = repository.getAllCompetitions()


    suspend fun insertCompetitions() {

        repository.insertCompetitions({
            _loadState.postValue(LoadState.DONE)
        }) {
            _loadState.postValue(LoadState.ERROR)
        }

    }


    suspend fun insertTeamSquad(teamId: Int) {
        repository.insertTeamSquad(teamId, {

            _loadState.postValue(LoadState.DONE)
        }) {
            _loadState.postValue(LoadState.ERROR)
        }
    }


    suspend fun insertCompetitionTeams(competitionId: Int) {
        repository.insertCompetitionTeams(competitionId, {

            _loadState.postValue(LoadState.DONE)
        }) {
            _loadState.postValue(LoadState.ERROR)
        }
    }


    fun getTeamsForCompetition(competitionId: Int): LiveData<List<Team>> =
        repository.getCompetitionTeams(competitionId)

    fun getTeamSquad(teamId: Int): LiveData<List<Squad>> = repository.getTeamSquad(teamId)
    fun publishLoadState(done: LoadState) {
        _loadState.postValue(done)
    }


}