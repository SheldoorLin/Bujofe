package com.sheldon.bujofe.class_schedule

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.`object`.ClassMute
import com.sheldon.bujofe.`object`.ClassName
import com.sheldon.bujofe.`object`.TeachList
import org.threeten.bp.LocalDate


fun generateCalendarDaliyEven(): List<ClassMute> {
    val TAG = "generate"
    val teachListsOnline: ArrayList<TeachList> = ArrayList()


    val db = FirebaseFirestore.getInstance()
    db.collection("teachList")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                val data = document.toObject(TeachList::class.java)
                teachListsOnline.add(data)
                }
        }.addOnFailureListener { exception -> Log.d(TAG, "Error getting documents: ", exception) }


    val list = mutableListOf<ClassMute>()
    for (item in teachListsOnline) {
        for (date_item in item.dateList) {

            val year = date_item.date_year
            val month = date_item.date_month
            val day = date_item.date_day
            val time = LocalDate.of(year, month, day).atTime(3,3)

            val classMute = ClassMute(
                time,
                ClassName(
                    date_item.lesson,
                    item.teachingRoom,
                    date_item.rollNameList.size.toString(),
                    item.title
                )
            )
            list.add(classMute)
            Log.d("unit","list = $list")
        }
    }
    return list
}