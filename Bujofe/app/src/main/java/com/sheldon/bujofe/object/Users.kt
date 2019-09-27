package com.sheldon.bujofe.`object`

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    val uid: String = "",
    val name: String = "",
    val grade: String = "",
    val email: String = "",
    val classList: List<ClassList> = mutableListOf(),
    val records: List<Records> = mutableListOf()
) : Parcelable


@Parcelize
data class ClassList(
    val classId: String = "",
    val className: String = "",
    val makeUp: Int = 0,
    val toGo: Int = 0
) : Parcelable


@Parcelize
data class Records(
    val classDate: String = "",
    val classId: String = "",

    @ServerTimestamp
    val date: Timestamp = Timestamp.now(),

    val endTime: String = "",
    val reclassId: Int = 0,
    val className: String="",
    val startTime: String = "0",
    val status: String = "",
    val type: String = ""
) : Parcelable