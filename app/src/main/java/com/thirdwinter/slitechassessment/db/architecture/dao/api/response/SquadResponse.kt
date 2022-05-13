package com.thirdwinter.slitechassessment.db.architecture.dao.api.response

import com.thirdwinter.slitechassessment.db.architecture.model.Squad

data class SquadResponse(
    val id: Int,
    val squad: List<Squad>?
)