package com.sheldon.bujofe.class_schedule

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.data.ClassEvent
import com.sheldon.bujofe.data.ClassName
import com.sheldon.bujofe.data.TeachList
import org.threeten.bp.LocalDate


//fun generateCalendarDaliyEven(): List<ClassEvent> {
//    val tagString = "generate"
//    val teachListsOnline: ArrayList<TeachList> = ArrayList()
//
//        FirebaseFirestore.getInstance().collection("teachList")
//        .get()
//        .addOnSuccessListener { result ->
//            for (document in result) {
//                val data = document.toObject(TeachList::class.java)
//                teachListsOnline.add(data)
//                }
//        }.addOnFailureListener { exception -> Log.d(tagString, "Error getting documents: ", exception) }
//
//
//    val list = mutableListOf<ClassEvent>()
//    for (item in teachListsOnline) {
//        for (date_item in item.dateList) {


//            val classStartTime = LocalDate.of(year, month, day).atTime(3,3)
//
//            val classMute = ClassEvent(
//                classStartTime,
//                ClassName(
//                    date_item.lesson,
//                    item.teachingRoom,
//                    date_item.rollNameList.size.toString(),
//                    item.title
//                )
//            )
//            list.add(classMute)
//            Log.d("unit","list = $list")
//        }
//    }
//    return list
//}