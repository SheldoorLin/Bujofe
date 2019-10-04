package com.sheldon.bujofe.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QRcode(
    val title : String ,
    val timestamp : Long
):Parcelable