package com.thirdwinter.slitechassessment.db.architecture.model

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kcoding.recyclerview_helper.SuperEntity


@Entity
data class Team(
    var competitionId: Int?,
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String?,
    val crestUrl: String?,
    val shortName: String?,
    val tla: String?,
    val address: String?,
    val website: String?,
    val founded: Int?,
    val phone: String?,
    val clubColor: String?,
    val email: String?
) : SuperEntity(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {

    }

    companion object CREATOR : Parcelable.Creator<Team> {
        override fun createFromParcel(parcel: Parcel): Team {
            return Team(parcel)
        }

        override fun newArray(size: Int): Array<Team?> {
            return arrayOfNulls(size)
        }
    }

}