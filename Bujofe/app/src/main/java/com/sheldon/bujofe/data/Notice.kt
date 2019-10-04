package com.sheldon.bujofe.data

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import java.util.*


data class Notice(

    val title: String? = null,
    val context: String? = null,

    @get:Exclude
    val date: String? = null,

    val type: Int? = null,

    @ServerTimestamp
    val time: Date? = null
)
