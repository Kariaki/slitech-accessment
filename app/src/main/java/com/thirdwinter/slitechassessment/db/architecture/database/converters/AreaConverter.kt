package com.thirdwinter.slitechassessment.db.architecture.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.thirdwinter.slitechassessment.db.architecture.model.Area

class AreaConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromStringToArea(value: String?): Area? =
            if (value == null) null else Gson().fromJson(value, Area::class.java)

        @TypeConverter
        @JvmStatic
        fun fromAreaToString(value: Area): String? = Gson().toJson(value)

    }
}