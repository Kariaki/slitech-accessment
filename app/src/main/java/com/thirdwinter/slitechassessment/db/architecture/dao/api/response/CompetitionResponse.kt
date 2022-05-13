package com.thirdwinter.slitechassessment.db.architecture.dao.api.response

import com.thirdwinter.slitechassessment.db.architecture.model.Competition

data class CompetitionResponse(val count: Int, val competitions: List<Competition>)