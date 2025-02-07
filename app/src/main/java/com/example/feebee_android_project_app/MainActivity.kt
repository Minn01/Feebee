package com.example.feebee_android_project_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.feebee_android_project_app.ui.theme.Feebee_Android_Project_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Feebee_Android_Project_AppTheme {
                MainScreen()
            }
        }
    }
}

//@Preview
//@Composable
//fun PreviewScreen() {
//    Feebee_Android_Project_AppTheme {
//        MainScreen()
//    }
//}
