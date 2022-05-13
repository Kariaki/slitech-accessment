package com.thirdwinter.slitechassessment.db.architecture.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thirdwinter.slitechassessment.db.architecture.dao.FootballDao
import com.thirdwinter.slitechassessment.db.architecture.database.converters.*
import com.thirdwinter.slitechassessment.db.architecture.model.*

//TODO DATABASE TABLES OR ENTITIES
@Database(
    entities = [Competition::class, Team::class, Squad::class],
    exportSchema = false,
    version = 6
)
@TypeConverters(
    CompetitionConverter::class,
    TeamConverter::class,
    CurrentSeasonConverter::class,
    AreaConverter::class
)
abstract class FootballDatabase : RoomDatabase() {


    abstract fun footballDao(): FootballDao

}