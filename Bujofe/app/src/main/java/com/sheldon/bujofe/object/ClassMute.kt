package com.sheldon.bujofe.`object`

import androidx.annotation.ColorRes
import org.threeten.bp.LocalDateTime


data class ClassMute(
    val time: LocalDateTime,
    val departure: ClassName,
    @ColorRes val color: Int
)



data class ClassName(
    val teacherName: String,
    val type: String
)
