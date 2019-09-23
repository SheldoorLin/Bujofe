package com.sheldon.bujofe.`object`

import kotlinx.android.parcel.Parcelize


data class Users(
    val uid: String = "",
    val name: String = "",
    val grade: String = "",
    val email: String = "",
    val classList: ClassList? = null,
    val records: Records? = null
)

data class ClassList(
    val classId: String = "",
    val makeUp: Int = 0,
    val toGo: Int = 0
)

data class Records(
    val id: String = "",
    val classId: String = "",
    val date: String = "",
    val status: String = ""
)