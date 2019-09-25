package com.sheldon.bujofe

import android.content.Context

object UserManager {


    var uid :String? = ""
        get() = BujofeApplication.instance.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            ?.getString("uid", "")
        set(value) {
            field = value
        }

}