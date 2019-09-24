package com.sheldon.bujofe.studyroom

import android.icu.text.SimpleDateFormat
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.`object`.OrderedTimes
import com.sheldon.bujofe.`object`.StudyroomSeat
import com.sheldon.bujofe.`object`.SeatList


class StudyRoomViewModel : ViewModel() {

    private var TAG: String = "StudyroomViewModel"

    val pageStatus = MutableLiveData<Int>()

    init {
        pageStatus.value = 0
        getStudyRoomfirebase()
    }

    private val _studyRoomdatas = MutableLiveData<List<StudyroomSeat>>()
    val studyRoomdatas: LiveData<List<StudyroomSeat>>
        get() = _studyRoomdatas

    val recoderList = mutableListOf<StudyroomSeat>()


    val studyRoomdataSeats = MutableLiveData<List<SeatList>>()




    fun getStudyRoomfirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("StudyroomSeat")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(StudyroomSeat::class.java)
                    val dateTime = java.sql.Date(data.date!!.time)
                    val format = SimpleDateFormat("yyy/MM/dd")
                    Log.d(TAG, "format.format(dateTime) is ${format.format(dateTime)}")
                    recoderList.add(data)
                    _studyRoomdatas.value = recoderList
                    Log.d(TAG, "server_data is $data")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }
}