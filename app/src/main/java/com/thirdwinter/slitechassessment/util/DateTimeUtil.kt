package com.thirdwinter.slitechassessment.util

import java.sql.Date
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat


object DateTimeUtils {


    fun getDate(utcDate: String): String {
        val split = utcDate.split("T")
        return split[0]
    }


}