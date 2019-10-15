package com.sheldon.bujofe.studyroom

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.login.UserManager
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

    private val _serverStudyRoomListData = MutableLiveData<List<StudyRoomSeat>>()
    val serverStudyRoomListData: LiveData<List<StudyRoomSeat>>
        get() = _serverStudyRoomListData

    val serverStudyRoomSeatsLists = MutableLiveData<StudyRoomSeat>()

    val localStudyRoomSeatsList = MutableLiveData<List<SeatList>>()

    val userName = UserManager.userName

    val chosenSeat = MutableLiveData<SeatOrder>()

    val chosenSeatId = MutableLiveData<String>()

    val chosenDate = MutableLiveData<String>()

    val chosenSeatOnServerDocumentId = MutableLiveData<String>()

    val clickedDateOnTopCalendar = MutableLiveData<LocalDate>()


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

                    _serverStudyRoomListData.value = recodeList
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}