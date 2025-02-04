package com.example.feebee_android_project_app.exchangeRateScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConversionTextFieldRow (
    primaryNum: MutableState<Double>,
    secondaryNum: MutableState<Double>,
    primaryTextFieldValueChanged: (String) -> Unit,
    secondaryTextFieldValueChanged: (String) -> Unit,
    modifier: Modifier
) {
    Row (
        modifier = modifier
            .padding(top = 25.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth().wrapContentWidth(),
    ) {
        TextField(
            value = primaryNum.value.toString(),
            onValueChange = primaryTextFieldValueChanged,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(25.dp))

        TextField(
            value = secondaryNum.value.toString(),
            onValueChange = secondaryTextFieldValueChanged,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier.weight(1f),
        )
    }
}
