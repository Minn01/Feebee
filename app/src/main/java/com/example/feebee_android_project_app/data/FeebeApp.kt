package com.example.feebee_android_project_app.data

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.feebee_android_project_app.FireStoreViewModel
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FeebeApp : Application() {
    @Inject
    lateinit var fireStoreViewModel: FireStoreViewModel

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver(fireStoreViewModel))

        // 🔥 Fetch data when the app starts
        fireStoreViewModel.fetchAllDataFromFireStore()
    }

    override fun onStop(owner: LifecycleOwner) {
        fireStoreViewModel.saveAllDataToFireStore() // Save data when app goes to background
    }
}

