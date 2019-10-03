package com.sheldon.bujofe.studyroom

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.`object`.StudyRoomSeat
import com.sheldon.bujofe.`object`.SeatList
import com.sheldon.bujofe.`object`.SeatOrder


class StudyRoomViewModel : ViewModel() {

    private var TAG: String = "StudyRoomViewModel"

    val pageStatus = MutableLiveData<Int>()

    init {
        pageStatus.value = 0
        getStudyRoomFirebase()
    }


    private val _studyRoomdatas = MutableLiveData<List<StudyRoomSeat>>()
    val studyRoomdatas: LiveData<List<StudyRoomSeat>>
        get() = _studyRoomdatas
    val studyRoomDataSeats = MutableLiveData<List<SeatList>>()


    val userName =  BujofeApplication.instance.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        .getString("displayName", "")

    val checkSeatStatus = MutableLiveData<SeatOrder>()
    val checkSeatId = MutableLiveData<String>()
    val checkedDate = MutableLiveData<String>()
    val checkedDocumentId = MutableLiveData<String>()

    val originSeatList = MutableLiveData<StudyRoomSeat>()



    private fun getStudyRoomFirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("StudyroomSeat")
            .get()
            .addOnSuccessListener { result ->
                val recoderList = mutableListOf<StudyRoomSeat>()
                for (document in result) {
                    val data = document.toObject(StudyRoomSeat::class.java)
                    val dateTime = java.sql.Date(data.date.time)
                    val format = SimpleDateFormat("yyy/MM/dd")
                    Log.d(TAG, "format.format(dateTime) is ${format.format(dateTime)}")
                    recoderList.add(data)
                    val index = recoderList.size -1
                    recoderList[index].documentId = document.id
                    _studyRoomdatas.value = recoderList
//                    Log.d(TAG, "server_data is  $recoderList")
//                    Log.d(TAG, "server_data_doc_id is  ${studyRoomdatas.value.toString()}")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

    }
}