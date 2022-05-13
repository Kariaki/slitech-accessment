package com.thirdwinter.slitechassessment.db.architecture.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thirdwinter.slitechassessment.db.architecture.model.*

@Dao
interface FootballDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCompetition(competition: Competition)

    @Query("select * from competition")
    fun getCompetitions(): LiveData<List<Competition>>


    @Query("select * from competition where id like:competitionId")
    fun getCompetitionById(competitionId: Int): LiveData<List<Competition>>

    @Query("select * from team where id like:teamId")
    fun getTeamById(teamId: Int): LiveData<List<Team>>

       @Query("select * from team where competitionId like:competitionId")
    fun getCompetitionTeams(competitionId: Int): LiveData<List<Team>>

    @Query("select * from `squad` where teamId like:teamId")
    fun getTeamSquad(teamId: Int): LiveData<List<Squad>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTeam(team: Team)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSquad(squad: Squad)



}