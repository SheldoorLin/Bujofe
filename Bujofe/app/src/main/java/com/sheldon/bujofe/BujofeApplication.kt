package com.sheldon.bujofe

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import kotlin.properties.Delegates

class BujofeApplication : Application() {


    companion object {
        var instance: BujofeApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}