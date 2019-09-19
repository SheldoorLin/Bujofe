package com.sheldon.bujofe.studyroom

import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.sheldon.bujofe.`object`.OrderedTimes
import com.sheldon.bujofe.`object`.StudyroomSeat
import com.sheldon.bujofe.`object`.SeatList


class StudyRoomViewModel : ViewModel() {

    private var TAG: String = "StudyroomViewModel"

    private val _studyRoomdatas = MutableLiveData<List<StudyroomSeat>>()
    val studyRoomdatas: LiveData<List<StudyroomSeat>>
        get() = _studyRoomdatas


    val studyRoomdataSeats = MutableLiveData<List<SeatList>>()

    private val _studyRoomSeats = MutableLiveData<List<SeatList>>()
    val studyRoomSeats: LiveData<List<SeatList>>
        get() = _studyRoomSeats


    fun getBooked() {
        val testBook: ArrayList<SeatList> = ArrayList()
        testBook.add(SeatList("001", 0, 0, "",OrderedTimes("", "", "")))
        testBook.add(SeatList("002", 0, 1, "", OrderedTimes("", "林添財", "")))
        testBook.add(SeatList("003", 0, 2, "", OrderedTimes("", "", "")))
        testBook.add(SeatList("004", 1, 0, "", OrderedTimes("", "", "")))
        testBook.add(SeatList("005", 1, 1, "", OrderedTimes("", "王大明", "")))
        testBook.add(SeatList("006", 1, 2, "", OrderedTimes("", "", "陳小玉")))
        testBook.add(SeatList("007", 2, 0, "", OrderedTimes("林小明", "", "")))
        testBook.add(SeatList("008", 2, 1, "", OrderedTimes("", "", "")))
        testBook.add(SeatList("009", 2, 2, "", OrderedTimes("", "", "藍中天")))
        _studyRoomSeats.value = testBook
        Log.d("_studyRoomSeats", _studyRoomSeats.toString())
    }


    fun getStudyRoomfirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("StudyroomSeat")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(StudyroomSeat::class.java)
                    val dateTime = java.sql.Date(data.date!!.time)
                    val format = SimpleDateFormat("yyy/MM/dd")

                    Log.d(TAG,"format.format(dateTime) is ${format.format(dateTime)}")
                    _studyRoomdatas.value = listOf(data)




                    Log.d(TAG, "server_data is $data")

                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

}
