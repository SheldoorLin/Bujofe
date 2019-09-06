package com.sheldon.bujofe

import android.app.Application
import android.content.Context
import android.content.res.Configuration
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