package com.sheldon.bujofe.home

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.data.Notice
import kotlin.collections.ArrayList


class HomeViewModel : ViewModel() {

    private val TAG = "HomeViewModel"

    private val _notices = MutableLiveData<List<Notice>>()
    val notices: LiveData<List<Notice>>
        get() = _notices

    private val noticeLists: ArrayList<Notice> = ArrayList()

    fun getNoticeFromFirebase() {

        FirebaseFirestore.getInstance().collection("notice")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    document.toObject(Notice::class.java).let { data ->

                        val dateTime = data.time?.let { utilDateToSQLDateFormat(it) }

                        val format = SimpleDateFormat("yyy/MM/dd")

                        noticeLists.add(
                            Notice(
                                data.title,
                                data.context,
                                format.format(dateTime),
                                data.type
                            )
                        )

                        _notices.value = noticeLists
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private fun utilDateToSQLDateFormat(utilDate: java.util.Date): java.sql.Date {
        return java.sql.Date(utilDate.time)
    }
}

