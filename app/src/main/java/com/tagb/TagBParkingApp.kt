package com.tagb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TagBParkingApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}