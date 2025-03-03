package com.example.feebee_android_project_app.data

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FeebeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // No ViewModel here
    }
}
