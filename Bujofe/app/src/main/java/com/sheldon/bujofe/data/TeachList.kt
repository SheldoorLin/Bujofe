package com.sheldon.bujofe.data

import com.google.firebase.Timestamp

data class TeachList(

    var id: String = "",

    val lessonTime: Long = 0,

    val title: String = "",

    val teacher: String = "",

    val teachingRoom: String = "",

    val type: String = "",

    val dateList: List<DateList> = mutableListOf(),

    val classSize: List<String> = mutableListOf()
)

data class DateList(

    val date: Timestamp = Timestamp.now(),

    val lesson: String = "",

    val rollNameList: ArrayList<String> = ArrayList()
)