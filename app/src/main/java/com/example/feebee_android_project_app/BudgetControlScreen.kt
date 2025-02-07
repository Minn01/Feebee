package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun BudgetControlScreen(
    modifier: Modifier
) {
    Box(modifier.fillMaxSize().wrapContentSize(Alignment.Center)) {
        Text("BUDGET SCREEN", fontSize = 30.sp)
    }
}
