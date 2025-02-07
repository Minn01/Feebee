package com.example.feebee_android_project_app.appBars

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QuickActionButton(
    iconLabel: String,
    onButtonClicked: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onButtonClicked)
            .background(Color(0xFFECAD00)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = iconLabel,
            tint = Color.White,
            modifier = Modifier.size(50.dp)
        )
    }
}
