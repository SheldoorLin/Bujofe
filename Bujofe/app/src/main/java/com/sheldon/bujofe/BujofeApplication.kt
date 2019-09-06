package com.sheldon.bujofe

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlin.properties.Delegates

class BujofeApplication : Application() {


    companion object {
        var instance: BujofeApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        instance = this
    }
}