package com.sheldon.bujofe.studyroom

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.BujofeApplication
import com.sheldon.bujofe.UserManager
import com.sheldon.bujofe.data.StudyRoomSeat
import com.sheldon.bujofe.data.SeatList
import com.sheldon.bujofe.data.SeatOrder
import org.threeten.bp.LocalDate


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

    val userName = UserManager.userName

    val checkSeatStatus = MutableLiveData<SeatOrder>()

    val checkSeatId = MutableLiveData<String>()

    val checkedDate = MutableLiveData<String>()

    val checkedDocumentId = MutableLiveData<String>()

    val originSeatList = MutableLiveData<StudyRoomSeat>()


    val checkdate = MutableLiveData<LocalDate>()


    private fun getStudyRoomFirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("StudyRoomSeat")
            .get()
            .addOnSuccessListener { result ->

                val recodeList = mutableListOf<StudyRoomSeat>()

                for (document in result) {

                    val data = document.toObject(StudyRoomSeat::class.java)

                    val dateTime = java.sql.Date(data.date.time)

                    val format = SimpleDateFormat("yyy/MM/dd")

                    Log.d(TAG, "format.format(dateTime) is ${format.format(dateTime)}")

                    recodeList.add(data)

                    val index = recodeList.size - 1

                    recodeList[index].documentId = document.id

                    _studyRoomdatas.value = recodeList
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}