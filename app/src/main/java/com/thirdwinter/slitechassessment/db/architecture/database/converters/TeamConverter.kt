package com.thirdwinter.slitechassessment.db.architecture.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.thirdwinter.slitechassessment.db.architecture.model.Team

class TeamConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromStringToTeam(value: String?): Team? {
            return if (value == null) null else Gson().fromJson(value, Team::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromTeamToString(score: Team?): String? {
            return Gson().toJson(score)
        }
    }
}