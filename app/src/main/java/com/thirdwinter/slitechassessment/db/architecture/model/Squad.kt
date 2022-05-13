package com.thirdwinter.slitechassessment.db.architecture.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kcoding.recyclerview_helper.SuperEntity

@Entity
data class Squad(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String?,
    val position: String?,
    var teamId: Int?,
    var dateOfBirth:String?,
    val role:String?,
    val nationality:String?
) : SuperEntity()