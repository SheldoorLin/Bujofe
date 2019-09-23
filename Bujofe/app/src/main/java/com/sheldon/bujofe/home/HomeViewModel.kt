package com.sheldon.bujofe.home

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.`object`.Notice
import kotlin.collections.ArrayList


class HomeViewModel : ViewModel() {

    private val TAG: String = "HomeViewModel"

    private val _notices = MutableLiveData<List<Notice>>()
    val notices: LiveData<List<Notice>>
        get() = _notices

    private val list: ArrayList<Notice> = ArrayList()


    fun getNoticefirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("notice")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(Notice::class.java)
                    val dateTime = java.sql.Date(data.time!!.time)
                    val format = SimpleDateFormat("yyy/MM/dd")

                    list.add(Notice(data.title, data.context, format.format(dateTime) , data.type))
                    Log.d("originTime", "${data.time}")
                    Log.d("Time", format.format(dateTime))
                    Log.d("dateTime", "$dateTime")
                    _notices.value = list
                    Log.d(TAG, "User ====" + data.title)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}

