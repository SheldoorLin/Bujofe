package com.sheldon.bujofe.`object`

data class TeachList(
    val id: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val title: String = "",
    val teacher: String = "",
    val teachingRoom: String = "",
    val type: String = "",
    val dataList: List<DateList> = mutableListOf(),
    val class_size: List<String> = mutableListOf()
)


data class DateList(
    val date: String,
    val lesson: String,
    val rollNameList: List<String> = mutableListOf()
)