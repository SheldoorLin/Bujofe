package com.sheldon.bujofe.`object`

data class TeachList(
    val id: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val title: String = "",
    val teacher: String = "",
    val teachingRoom: String = "",
    val type: String = "",
    val dateList: List<DateList> = mutableListOf(),
    val class_size: List<String> = mutableListOf()
)


data class DateList(
    val date: String = "",
    val date_year: Int = 0,
    val date_month: Int = 0,
    val date_day: Int = 0,
    val lesson: String = "",
    val rollNameList: List<String> = mutableListOf()
)