package com.thirdwinter.slitechassessment.db.architecture.dao.api.response

import com.thirdwinter.slitechassessment.db.architecture.model.Competition
import com.thirdwinter.slitechassessment.db.architecture.model.Team

data class TeamResponse(
    val competition: Competition?,
    val teams: List<Team>?
)