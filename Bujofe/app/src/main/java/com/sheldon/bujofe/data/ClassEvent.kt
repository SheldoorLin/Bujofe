package com.sheldon.bujofe.data

import org.threeten.bp.LocalDateTime


data class ClassEvent(

    val classStartTime: LocalDateTime,
    val classFinishTime:LocalDateTime,
    val className: ClassName
)

data class ClassName(
    val courseContent : String,
    val teachClass: String,
    val rollCallSituation:String,
    val type: String
)
