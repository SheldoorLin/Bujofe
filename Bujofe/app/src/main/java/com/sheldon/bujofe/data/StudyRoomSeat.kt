package com.sheldon.bujofe.data

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class StudyRoomSeat(
    @ServerTimestamp
    val date: Date = Date(),
    @ServerTimestamp
    val localDate: Date = Date(),
    val seatList: List<SeatList> = mutableListOf(),
    @get:Exclude
    var documentId: String = ""
) : Parcelable

@Parcelize
data class SeatList(
    val id: String = "",
    val row: Int = 0,
    val column: Int = 0,
    val status: String = "",
    val orderedTimes: OrderedTimes? = null
) : Parcelable

@Parcelize
data class OrderedTimes(
    var firstTimeSlot: String = "",
    var secTimeSlot: String = "",
    var thirdTimeSlot: String = ""
) : Parcelable