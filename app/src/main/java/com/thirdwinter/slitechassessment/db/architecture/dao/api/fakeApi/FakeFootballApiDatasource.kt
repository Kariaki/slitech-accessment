package com.thirdwinter.slitechassessment.db.architecture.dao.api.fakeApi

import androidx.core.content.ContextCompat
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thirdwinter.slitechassessment.db.architecture.model.Competition
import com.thirdwinter.slitechassessment.db.architecture.model.Squad
import com.thirdwinter.slitechassessment.db.architecture.model.Team
import com.thirdwinter.slitechassessment.util.TestData.competitionSource
import com.thirdwinter.slitechassessment.util.TestData.squadSource
import com.thirdwinter.slitechassessment.util.TestData.teamSource

class FakeFootballApiDatasource {

    fun getAllCompetitions(): List<Competition> =
        Gson().fromJson(competitionSource, object : TypeToken<List<Competition>>() {}.type)

    fun getTeams(): List<Team> =
        Gson().fromJson(teamSource, object : TypeToken<List<Team>>() {}.type)

    fun getSquads(): List<Squad> =
        Gson().fromJson(squadSource, object : TypeToken<List<Squad>>() {}.type)

}