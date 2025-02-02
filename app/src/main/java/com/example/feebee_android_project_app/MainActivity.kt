package com.example.feebee_android_project_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.feebee_android_project_app.ui.theme.Feebee_Android_Project_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Feebee_Android_Project_AppTheme {
//                MainScreen()
                Scaffold { contentPadding ->
                    ExchangeRateScreen(Modifier.padding(contentPadding))
                }
            }
        }
    }
}
