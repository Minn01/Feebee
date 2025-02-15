package com.example.feebee_android_project_app.accountScreens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DateText(
    dateText: String,
    modifier: Modifier
) {
    Row(
        modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(Modifier.weight(1f))
        Text(
            text = dateText,
            modifier = Modifier.padding(start = 15.dp, end = 15.dp)
        )
        HorizontalDivider(Modifier.weight(1f))
    }
}
