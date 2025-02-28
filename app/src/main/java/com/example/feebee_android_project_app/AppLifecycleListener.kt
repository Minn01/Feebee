package com.example.feebee_android_project_app

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

class AppLifecycleListener(
    private val saveData: (LifecycleOwner) -> Unit // Accepts LifecycleOwner
) : DefaultLifecycleObserver {

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        saveData(owner) // Pass the lifecycle owner
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        saveData(owner) // Ensure data is saved when app goes to background
    }
}
