package com.sheldon.bujofe.class_schedule

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.`object`.ClassMute
import com.sheldon.bujofe.`object`.ClassName
import com.sheldon.bujofe.`object`.TeachList
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.LocalDate

class ClassScheduleViewModel : ViewModel() {
    private val TAG: String = "ClassScheduleViewModel"

    var classMutes: Map<LocalDate, List<ClassMute>> = mapOf()

    private val _teachLists = MutableLiveData<List<TeachList>>()
    val teachLists: LiveData<List<TeachList>>
        get() = _teachLists

    private val teachListsOnline: ArrayList<TeachList> = ArrayList()

//    val classMutes = getTeacherList().groupBy { it.time.toLocalDate() }


    init {
        getTeachListFirebase()
    }


    fun getTeachListFirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("teachList")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(TeachList::class.java)
                    teachListsOnline.add(data)
                    _teachLists.value = teachListsOnline
                }
            }.addOnFailureListener { exception ->
                Log.d(
                    TAG,
                    "Error getting documents: ",
                    exception
                )
            }

    }


    fun getTeacherList(): List<ClassMute> {
        val list = mutableListOf<ClassMute>()
        for (item in teachLists.value!!) {
            for (date_item in item.dateList) {

                val year = date_item.date_year
                val month = date_item.date_month
                val day = date_item.date_day
                val time = LocalDate.of(year, month, day).atStartOfDay()


//                val testOnline = date_item.date
//
//                val test = DateTimeUtils.toSqlTimestamp(testOnline)
//                Log.d(TAG, "test = ${test}")
//                val test_2 = DateTimeUtils.toLocalDateTime(test)
//                Log.d(TAG, "test_2 = ${test_2}")


                val classMute = ClassMute(
                    time,
                    ClassName(
                        date_item.lesson,
                        item.teachingRoom,
                        date_item.rollNameList.size.toString() + "/" + item.class_size.size.toString(),
                        item.title
                    )
                )
                list.add(classMute)
            }
        }
        return list
    }


}
