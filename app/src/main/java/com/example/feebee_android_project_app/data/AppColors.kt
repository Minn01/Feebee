package com.example.feebee_android_project_app.data

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TextFieldDefaults


data class AppColors(
    val barColor: Color,
    val backgroundColor: Color,
    val contentColor: Color,
    val buttonColor: Color,
    val buttonShadeColor: Color,
    val customButtonColors:  ButtonColors,
)

val lightModeColors = AppColors(
    barColor = Color(0xFF677794),
    backgroundColor = Color.White,
    contentColor = Color.Black,
    buttonColor = Color(0xFECECECF),
    buttonShadeColor = Color.Black,
    customButtonColors = ButtonColors(
        containerColor = Color(0xFF677794),
        contentColor = Color.Black,
        disabledContainerColor = Color.Black,
        disabledContentColor = Color.White,
    ),
)

val darkModeColors = AppColors(
    barColor = Color(0xFF161616),
    backgroundColor = Color(0xFF0F1621),
    contentColor = Color.White,
    buttonColor = Color(0xFF18202E),
    buttonShadeColor = Color(0xFF29303C),
    customButtonColors = ButtonColors(
        containerColor = Color(0xFF18202E),
        contentColor = Color.White,
        disabledContainerColor = Color.Black,
        disabledContentColor = Color.White,
    )
)
