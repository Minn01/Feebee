package com.example.feebee_android_project_app.sideNavigationDrawer

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ColorSchemeBox(
    modifier: Modifier
) {
    CustomToggleButton(modifier = Modifier)
}

@Composable
fun CustomToggleButton(
    modifier: Modifier
) {
    val isChecked = rememberSaveable { mutableStateOf(false) }

    val thumbOffsetX by animateDpAsState(
        targetValue = if (isChecked.value) 160.dp else 30.dp,
        label = "switch_animation"
    )

    val thumbOffSetY = 7.dp

    // Outer box
    Box(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .height(60.dp)
            .clickable { isChecked.value = !isChecked.value }
            .background(
                color = Color(0xFF18202E),
                shape = RoundedCornerShape(50)
            )
            .offset(x = thumbOffsetX, y = thumbOffSetY),
    ) {

        // Inner box with offset
        Box(
            modifier = Modifier
                .size(150.dp, 43.dp) // Adjusted size
                .background(
                    color = Color(0xFF29303C).copy(alpha = 0.7f),
                    shape = RoundedCornerShape(50)
                )
        )

        // Row for the icons and text
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(x = thumbOffsetX * (-1), y = thumbOffSetY * (-1)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ModeRow(
                modeText = "Light",
                modeIcon = Icons.Default.Check,
                modeContentDescription = "light_mode",
                modifier = Modifier
            )

            ModeRow(
                modeText = "Dark",
                modeIcon = Icons.Default.Clear,
                modeContentDescription = "dark_mode",
                modifier = Modifier
            )
        }
    }
}

@Composable
fun ModeRow(
    modeText: String,
    modeIcon: ImageVector,
    modeContentDescription: String,
    modifier: Modifier
) {
    Row(modifier) {
        Icon(
            imageVector = modeIcon,
            contentDescription = modeContentDescription,
            tint = Color.White
        )

        Text(
            modeText,
            Modifier.padding(start = 10.dp),
            color = Color.White
        )
    }
}
