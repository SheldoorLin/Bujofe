package com.sheldon.bujofe.home

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import com.sheldon.bujofe.`object`.Notice
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class HomeViewModel : ViewModel() {

    private val TAG: String = "HomeViewModel"

    private val _Notices = MutableLiveData<List<Notice>>()
    val Notices: LiveData<List<Notice>>
        get() = _Notices

    private val list: ArrayList<Notice> = ArrayList()


    fun getNoticefirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("announcement")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(Notice::class.java)
                    val dateTime = java.sql.Date(data.time!!.time)
                    val format = SimpleDateFormat("yyy/MM/dd")
                    list.add(Notice(data.title,data.context,format.format(dateTime)))

                    Log.d("Time", format.format(dateTime))
                    Log.d("dateTime","$dateTime")
                    _Notices.value = list
                    Log.d(TAG, "User ====" + data.title)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}

