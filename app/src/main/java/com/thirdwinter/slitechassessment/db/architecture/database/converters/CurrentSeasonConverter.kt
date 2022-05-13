package com.thirdwinter.slitechassessment.db.architecture.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.thirdwinter.slitechassessment.db.architecture.model.CurrentSeason
import com.thirdwinter.slitechassessment.db.architecture.model.Team

class CurrentSeasonConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromStringToCurrentSeason(value: String?): CurrentSeason? =
            if (value == null) null else Gson().fromJson(value, CurrentSeason::class.java)

        @TypeConverter
        @JvmStatic
        fun fromCurrentSeasonToString(value: CurrentSeason?): String? {
            return Gson().toJson(value)
        }

    }
}