package com.sheldon.bujofe.`object`

import androidx.annotation.ColorRes
import org.threeten.bp.LocalDateTime


data class ClassMute(
    val time: LocalDateTime,
    val departure: ClassName
)


data class ClassName(
    val class_context : String,
    val teach_class: String,
    val order_people:String,
    val type: String
)
