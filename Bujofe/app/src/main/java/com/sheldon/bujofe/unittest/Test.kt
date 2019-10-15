package com.sheldon.bujofe.unittest

import android.content.Context
import com.sheldon.bujofe.R
import com.sheldon.bujofe.data.ClassEvent
import com.sheldon.bujofe.data.ClassName
import com.sheldon.bujofe.data.TeachList
import org.threeten.bp.DateTimeUtils
import java.sql.Timestamp

fun getStringFromMockito(context: Context): String {
    return context.getString(R.string.hello_word)
}

fun getTeacherList(teachListVelue: List<TeachList>): List<ClassEvent> {
    val classEventList = mutableListOf<ClassEvent>()
    for (teachList in teachListVelue) {
        for (dateList in teachList.dateList) {
            val date = Timestamp(dateList.date.seconds * 1000)
            val classStartTime = DateTimeUtils.toLocalDateTime(date)
            val classFinishTime = DateTimeUtils.toLocalDateTime(date).plusHours(teachList.lessonTime)
            classEventList.add(
                ClassEvent(
                    classStartTime,
                    classFinishTime,
                    ClassName(
                        dateList.lesson,
                        teachList.teachingRoom,
                        "${dateList.rollNameList.size}/${teachList.classSize.size}",
                        teachList.title
                    )
                )
            )
        }
    }
    return classEventList
}