package com.sheldon.bujofe.`object`

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
data class StudyroomSeat(

    @ServerTimestamp
    val date: Date? = null,
    val local_date: String = "",
    val seatList: List<SeatList> = mutableListOf()

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
    val firstTimeSlot: String = "",
    val secTimeSlot: String = "",
    val thirdTimeSlot: String = ""
) : Parcelable