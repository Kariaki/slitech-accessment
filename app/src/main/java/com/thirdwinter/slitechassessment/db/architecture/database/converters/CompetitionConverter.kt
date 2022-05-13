package com.thirdwinter.slitechassessment.db.architecture.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.thirdwinter.slitechassessment.db.architecture.model.Competition

class CompetitionConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromCompetitionToString(value: String?): Competition? {
            return if (value == null) null else Gson().fromJson(value, Competition::class.java)
        }


        @TypeConverter
        @JvmStatic
        fun fromStringToCompetition(score: Competition?): String? {
            return Gson().toJson(score)
        }
    }
}