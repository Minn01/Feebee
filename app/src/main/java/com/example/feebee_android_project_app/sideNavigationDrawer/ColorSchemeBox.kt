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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ColorSchemeBox(
    modifier: Modifier
) {
    val isChecked = rememberSaveable {
        mutableStateOf(false)
    }

    CustomToggleButton(
        modifier = modifier.padding(start = 16.dp, end = 16.dp),
        mode1Text = "Light",
        mode2Text = "Dark",
        thumbOffSetX1 = 160.dp,
        thumbOffSetX2 = 30.dp,
        thumbOffSetY = 7.dp,
        outerBoxHeight = 60.dp,
        isChecked = isChecked,
        toggleButtonClicked = { isChecked.value = !isChecked.value },
        backgroundColor = Color(0xFF18202E),
        buttonShadeColor = Color(0xFF29303C).copy(alpha = 0.7f),
        mode1ContentDescription = "light_mode",
        mode2ContentDescription = "dark_mode",
        innerBoxWidth = 150.dp,
        innerBoxHeight = 43.dp,
    )
}

@Composable
fun CustomToggleButton(
    mode1Text: String,
    mode2Text: String,
    thumbOffSetX1: Dp,
    thumbOffSetX2: Dp,
    thumbOffSetY: Dp,
    outerBoxHeight: Dp,
    innerBoxWidth: Dp,
    innerBoxHeight: Dp,
    isChecked: MutableState<Boolean>,
    iconEnabled: Boolean = true,
    mode1Icon: ImageVector = Icons.Default.Check,
    mode2Icon: ImageVector = Icons.Default.Clear,
    toggleButtonClicked: () -> Unit,
    backgroundColor: Color,
    buttonShadeColor: Color,
    shape: Shape = RoundedCornerShape(50),
    mode1ContentDescription: String = "mode1",
    mode2ContentDescription: String = "mode2",
    modifier: Modifier
) {
    val thumbOffsetX by animateDpAsState(
        targetValue = if (isChecked.value) thumbOffSetX1 else thumbOffSetX2,
        label = "switch_animation"
    )


    // Outer box
    Box(
        modifier = modifier
            .height(outerBoxHeight)
            .clickable(onClick = toggleButtonClicked)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .offset(x = thumbOffsetX, y = thumbOffSetY),
    ) {

        // Inner box with offset
        Box(
            modifier = Modifier
                .size(innerBoxWidth, innerBoxHeight) // Adjusted size
                .background(
                    color = buttonShadeColor,
                    shape = shape
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
                modeText = mode1Text,
                modeIcon = mode1Icon,
                modeContentDescription = mode1ContentDescription,
                iconEnabled = iconEnabled,
                modifier = Modifier,
            )

            ModeRow(
                modeText = mode2Text,
                modeIcon = mode2Icon,
                modeContentDescription = mode2ContentDescription,
                iconEnabled = iconEnabled,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun ModeRow(
    iconEnabled: Boolean,
    modeText: String,
    modeIcon: ImageVector,
    modeContentDescription: String,
    modifier: Modifier
) {
    Row(modifier) {
        if (iconEnabled) {
            Icon(
                imageVector = modeIcon,
                contentDescription = modeContentDescription,
                tint = Color.White
            )
        }

        Text(
            modeText,
            Modifier.padding(start = 10.dp),
            color = Color.White
        )
    }
}
