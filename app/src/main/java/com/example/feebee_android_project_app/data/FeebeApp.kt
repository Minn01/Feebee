package com.example.feebee_android_project_app.data

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FeebeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // No ViewModel here
    }
}
