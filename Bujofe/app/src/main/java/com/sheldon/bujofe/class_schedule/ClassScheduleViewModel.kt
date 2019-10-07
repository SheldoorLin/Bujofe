package com.sheldon.bujofe.class_schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.data.ClassEvent
import com.sheldon.bujofe.data.ClassName
import com.sheldon.bujofe.data.TeachList
import com.sheldon.bujofe.util.Logger
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate
import java.sql.Timestamp
import kotlin.collections.ArrayList


class ClassScheduleViewModel : ViewModel() {

    private val TAG: String = "ClassScheduleViewModel"

    var classEvents: Map<LocalDate, List<ClassEvent>> = mapOf()

    private val _teachLists = MutableLiveData<List<TeachList>>()
    val teachLists: LiveData<List<TeachList>>
        get() = _teachLists

    private val serverTeachList: ArrayList<TeachList> = ArrayList()

    init {
        getTeachListFirebase()
    }

    fun getTeachListFirebase() {

        FirebaseFirestore.getInstance().collection("teachList")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    serverTeachList.add(document.toObject(TeachList::class.java))

                    _teachLists.value = serverTeachList
                }
            }.addOnFailureListener { exception ->
                Logger.d(TAG + "Error getting documents: " + exception)
            }
    }


    fun getTeacherList(): List<ClassEvent> {
        val classEventList = mutableListOf<ClassEvent>()
        for (teachList in teachLists.value!!) {
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
}
