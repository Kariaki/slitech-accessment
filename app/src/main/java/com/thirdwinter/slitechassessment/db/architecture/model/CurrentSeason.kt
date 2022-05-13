package com.thirdwinter.slitechassessment.db.architecture.model

data class CurrentSeason(
    val id: Int?,
    val startDate: String,
    val endDate: String?,
    val currentMatchday: Int?
)