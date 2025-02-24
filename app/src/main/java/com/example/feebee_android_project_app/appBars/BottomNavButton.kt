package com.example.feebee_android_project_app.appBars

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavButton(
    @DrawableRes iconImage: Int,
    iconLabel: String,
    isSelected: Boolean,
    onButtonClicked: () -> Unit,
    modifier: Modifier
) {
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.LightGray,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        ), label = "content_color"
    )

    Column(
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onButtonClicked)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(iconImage),
            contentDescription = iconLabel,
            tint = contentColor,
            modifier = Modifier.size(40.dp)
        )
    }
}
