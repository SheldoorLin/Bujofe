package com.sheldon.bujofe.`object`

import androidx.annotation.ColorRes
import org.threeten.bp.LocalDateTime


data class ClassMute(
    val time: LocalDateTime,
    val departure: className,
    @ColorRes val color: Int
) {
    data class className(
        val teacherName: String,
        val type: String
    )
}