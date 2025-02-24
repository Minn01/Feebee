package com.example.feebee_android_project_app.data

import androidx.compose.ui.graphics.Color

data class AppColors(
    val barColor: Color,
    val backgroundColor: Color,
    val contentColor: Color,
    val buttonColor: Color,
    val buttonShadeColor: Color
)

val lightModeColors = AppColors(
    barColor = Color(0xFF677794),
    backgroundColor = Color.White,
    contentColor = Color.Black,
    buttonColor = Color(0xFECECECF),
    buttonShadeColor = Color.Black,
)

val darkModeColors = AppColors(
    barColor = Color(0xFF161616),
    backgroundColor = Color(0xFF0F1621),
    contentColor = Color.White,
    buttonColor = Color(0xFF18202E),
    buttonShadeColor = Color(0xFF29303C),
)
