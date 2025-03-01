package com.example.feebee_android_project_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import com.example.feebee_android_project_app.ui.theme.Feebee_Android_Project_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val fireStoreViewModel: FireStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 Save data when app goes to the background
        lifecycle.addObserver(AppLifecycleListener { _ ->
            fireStoreViewModel.saveAllDataToFireStore()
        })

        enableEdgeToEdge()
        setContent {
            Feebee_Android_Project_AppTheme {
                MainScreen()
            }
        }
    }
}
