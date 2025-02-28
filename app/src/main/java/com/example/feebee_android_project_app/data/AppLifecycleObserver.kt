package com.example.feebee_android_project_app.data

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.feebee_android_project_app.FireStoreViewModel

class AppLifecycleObserver(
    private val fireStoreViewModel: FireStoreViewModel
) : DefaultLifecycleObserver {

    override fun onStop(owner: LifecycleOwner) {
        fireStoreViewModel.saveAllDataToFireStore()
    }
}