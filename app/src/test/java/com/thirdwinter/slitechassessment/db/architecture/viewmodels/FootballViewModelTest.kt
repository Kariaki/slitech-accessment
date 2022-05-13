package com.thirdwinter.slitechassessment.db.architecture.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.thirdwinter.slitechassessment.MainCoroutineRule
import com.thirdwinter.slitechassessment.db.architecture.dao.api.fakeApi.FakeFootballApiDatasource
import com.thirdwinter.slitechassessment.db.architecture.model.Competition
import com.thirdwinter.slitechassessment.db.architecture.repository.fake.FakeFootballRepository
import com.thirdwinter.slitechassessment.getOrAwaitValueTest
import com.thirdwinter.slitechassessment.util.LoadState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FootballViewModelTest {

    private lateinit var viewModel: FootballViewModel

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        val datasource = FakeFootballApiDatasource()
        val repository = FakeFootballRepository(datasource)
        viewModel = FootballViewModel(repository)
    }

    @Test
    fun testCheck() {
        assertThat("hello").isEqualTo("hello")
    }

    @Test
    fun `test get competitions`() {
        val competitions = viewModel.competitions.getOrAwaitValueTest()
        assertThat(competitions).isEmpty()
    }

    @Test
    fun `test if competition datasource is empty`() {

        val datasource = FakeFootballApiDatasource().getAllCompetitions()
        assertThat(datasource).isNotEmpty()

    }

    @Test
    fun `test first competition`() = runBlocking {
        viewModel.insertCompetitions()
        val datasource = viewModel.competitions.getOrAwaitValueTest()
        val firsCompeitition = datasource[0]
        assertThat(firsCompeitition.id).isEqualTo(2013)
    }


    @Test
    fun `test insert and get competitions`() = runBlocking {
        viewModel.insertCompetitions()
        val competitions = viewModel.competitions.getOrAwaitValueTest()
        assertThat(competitions).isNotEmpty()
    }


    @Test
    fun `test get competition teams is not empty`() = runBlocking {
        val competitionId = 1765
        viewModel.insertCompetitionTeams(competitionId)
        val competitionTeams = viewModel.getTeamsForCompetition(competitionId).getOrAwaitValueTest()
        assertThat(competitionTeams).isNotEmpty()

    }

    @Test
    fun `test get team squad is not empty`() = runBlocking {
        val teamId = 805
        viewModel.insertTeamSquad(teamId)
        val teamSquads = viewModel.getTeamSquad(teamId).getOrAwaitValueTest()
        assertThat(teamSquads).isNotEmpty()
    }

    @Test
    fun `test if loading state changes to DONE`() = runBlocking {
        val teamId = 805
        viewModel.insertTeamSquad(teamId)
        val loadState = viewModel.loadState.getOrAwaitValueTest()
        assertThat(loadState).isEqualTo(LoadState.DONE)
    }
}