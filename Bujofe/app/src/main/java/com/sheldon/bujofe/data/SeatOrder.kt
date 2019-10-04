package com.sheldon.bujofe.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SeatOrder(
    val id: String,
    val date: String,
    val documentId: String,
    val orderedTimes: OrderedTimes,
    val originSeatList: StudyRoomSeat
) : Parcelable