package com.thirdwinter.slitechassessment.db.architecture.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.thirdwinter.slitechassessment.db.architecture.dao.api.fakeApi.FakeFootballApiDatasource
import com.thirdwinter.slitechassessment.db.architecture.database.FootballDatabase
import com.thirdwinter.slitechassessment.db.architecture.model.Team
import com.thirdwinter.slitechassessment.db.architecture.repository.fake.FakeFootballRepository
import com.thirdwinter.slitechassessment.db.architecture.viewmodels.FootballViewModel
import com.thirdwinter.slitechassessment.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FootballDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FootballDatabase
    private lateinit var dao: FootballDao
    private lateinit var viewModel: FootballViewModel

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FootballDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.footballDao()
        val dataSource = FakeFootballApiDatasource()
        val repository = FakeFootballRepository(dataSource)
        viewModel = FootballViewModel(repository)

    }

    @After
    fun tearDown() {
        database.close()
    }

    /*
    test for teams
     */
    @Test
    fun testInsertAndFetchTeamToLocalDb() = runBlocking {
        val team = Team(
            2013,
            1,
            "Manchester united",
            "",
            null,
            null,
            null,
            null,
            1992,
            null,
            null,
            null
        )
        dao.insertTeam(team)
        val allTeams = dao.getCompetitionTeams(2013).getOrAwaitValue()
        assertThat(allTeams).contains(team)

    }

    /*
    test for competition
     */
    @Test
    fun testInsertAndFetchCompetitionToLocalDb() = runBlocking {

        val competition = FakeFootballApiDatasource().getAllCompetitions()[0]
        val competitions = dao.getCompetitions().getOrAwaitValue()
        assertThat(competitions).contains(competition)

    }

    /*
  test for squad
   */
    @Test
    fun testInsertAndFetchSquadToLocalDb() = runBlocking {

        val squad = FakeFootballApiDatasource().getSquads()[0]
        val competitions = dao.getTeamSquad(squad.teamId!!).getOrAwaitValue()
        assertThat(competitions).contains(squad)

    }


}
