package com.sheldon.bujofe.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QRcode(
    val title : String ,
    val timestep : Long
):Parcelable