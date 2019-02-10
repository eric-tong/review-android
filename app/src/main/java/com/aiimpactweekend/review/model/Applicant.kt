package com.aiimpactweekend.review.model

import android.os.Parcel
import android.os.Parcelable

data class Applicant(
    val firstName: String,
    val lastName: String,
    val workPosition: String,
    val workName: String,
    val workDuration: Int,
    val schoolDegree: String,
    val schoolName: String,
    val schoolYear: Int,
    val traits: Array<String>,
    val isRecommended: Boolean
    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.createStringArray(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(workPosition)
        parcel.writeString(workName)
        parcel.writeInt(workDuration)
        parcel.writeString(schoolDegree)
        parcel.writeString(schoolName)
        parcel.writeInt(schoolYear)
        parcel.writeStringArray(traits)
        parcel.writeByte(if (isRecommended) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Applicant> {
        override fun createFromParcel(parcel: Parcel): Applicant {
            return Applicant(parcel)
        }

        override fun newArray(size: Int): Array<Applicant?> {
            return arrayOfNulls(size)
        }
    }
}