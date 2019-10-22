package com.sheldon.bujofe.studyroom

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.sheldon.bujofe.data.SeatOrder
import com.sheldon.bujofe.data.StudyRoomSeat

class OrderSeatViewModel(seatOrder: SeatOrder, app: Application) : AndroidViewModel(app) {
    private val tagString: String = "OrderSeatViewModel"

    private val _seatOrders = MutableLiveData<SeatOrder>()
    val seatOrders: LiveData<SeatOrder>
        get() = _seatOrders


    private val _studyRoomdatas = MutableLiveData<List<StudyRoomSeat>>()
    val studyRoomdatas: LiveData<List<StudyRoomSeat>>
        get() = _studyRoomdatas

    private val recodedList = mutableListOf<StudyRoomSeat>()


    init {
        _seatOrders.value = seatOrder
        getStudyRoomfirebase()
    }


    fun setNewData() {
        val db = FirebaseFirestore.getInstance()
        val washingtonRef = db.collection("StudyRoomSeat").document(seatOrders.value?.documentId!!)
        washingtonRef.set(seatOrders.value!!.originSeatList, SetOptions.merge())
    }


    fun getStudyRoomfirebase() {
        val db = FirebaseFirestore.getInstance()
        db.collection("StudyRoomSeat")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val data = document.toObject(StudyRoomSeat::class.java)
                    recodedList.add(data)
                    val index = recodedList.size - 1
                    recodedList[index].documentId = document.id
                    _studyRoomdatas.value = recodedList
                }
            }
            .addOnFailureListener { exception ->
                Log.d(tagString, "Error getting documents: ", exception)
            }
    }
}
