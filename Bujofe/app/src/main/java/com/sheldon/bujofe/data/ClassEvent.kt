package com.sheldon.bujofe.data

import org.threeten.bp.LocalDateTime


data class ClassEvent(
    val time: LocalDateTime,
    val className: ClassName
)

data class ClassName(
    val class_context : String,
    val teach_class: String,
    val order_people:String,
    val type: String
)
