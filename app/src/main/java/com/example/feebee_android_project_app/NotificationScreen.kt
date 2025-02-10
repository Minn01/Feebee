package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NotificationScreen(modifier: Modifier) {
    Box(modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        Text("Notifications Screen")
    }
}
