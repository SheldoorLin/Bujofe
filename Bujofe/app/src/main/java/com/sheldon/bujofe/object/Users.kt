package com.sheldon.bujofe.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    val uid: String = "",
    val name: String = "",
    val grade: String = "",
    val email: String = "",
    val classList: ClassList? = null,
    val records: Records? = null
):Parcelable


@Parcelize
data class ClassList(
    val classId: String = "",
    val makeUp: Int = 0,
    val toGo: Int = 0
):Parcelable

@Parcelize
data class Records(
    val id: String = "",
    val classId: String = "",
    val date: String = "",
    val status: String = ""
):Parcelable