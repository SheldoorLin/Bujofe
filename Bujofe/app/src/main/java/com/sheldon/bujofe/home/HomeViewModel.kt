package com.sheldon.bujofe.home

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.`object`.Notice
import org.threeten.bp.DateTimeUtils
import org.threeten.bp.format.DateTimeTextProvider
import kotlin.collections.ArrayList


class HomeViewModel : ViewModel() {

    private val TAG = "HomeViewModel"

    private val _notices = MutableLiveData<List<Notice>>()
    val notices: LiveData<List<Notice>>
        get() = _notices

    private val noticeLists: ArrayList<Notice> = ArrayList()

    fun getNoticeFirebase() {

        val db = FirebaseFirestore.getInstance()

        db.collection("notice")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(Notice::class.java)

                    val dateTime = java.sql.Date(data.time!!.time)

                    val format = SimpleDateFormat("yyy/MM/dd")

                    val date_time = DateTimeUtils.toLocalDate(dateTime)
                    Log.d(TAG,"date_time ==== $date_time")

                    noticeLists.add(Notice(data.title, data.context, format.format(dateTime) , data.type))

                    _notices.value = noticeLists

                    Log.d(TAG,"originTime = ${data.time}")
                    Log.d(TAG,"Time = ${format.format(dateTime)}")
                    Log.d(TAG,"dateTime = $dateTime")
                    Log.d(TAG, "User =  ${data.title}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}

