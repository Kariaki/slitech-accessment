package com.thirdwinter.slitechassessment.db.architecture.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kcoding.recyclerview_helper.SuperEntity


@Entity
data class Competition(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String?, val emblemUrl: String?, val code: String, val plan: String,
    val currentSeason: CurrentSeason, val area: Area
) : SuperEntity()