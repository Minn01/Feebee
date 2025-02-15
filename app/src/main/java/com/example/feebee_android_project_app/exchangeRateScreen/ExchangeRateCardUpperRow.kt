package com.example.feebee_android_project_app.exchangeRateScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feebee_android_project_app.DropDownButton
import com.example.feebee_android_project_app.R

@Composable
fun ExchangeRateCardUpperRow (
    primaryButtonIsExpanded: MutableState<Boolean>,
    secondaryButtonIsExpanded: MutableState<Boolean>,
    primarySelectedOption: MutableState<String>,
    secondarySelectedOption: MutableState<String>,
    modifier: Modifier
) {
    val dropDownOptions = listOf(
        "USD", "MMK", "THB", "EUR"
    )
    Row(
        modifier = modifier
            .padding(top = 25.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DropDownButton(
            expanded = primaryButtonIsExpanded,
            selectedOption = primarySelectedOption,
            dropDownOptions = dropDownOptions,
            modifier = Modifier,
            readOnly = false,
            onValueChange = {
                if (it.length < 4) {
                    primarySelectedOption.value = it.uppercase()
                }
            },
            width = 130.dp,
            height = 50.dp,
            textStyle = TextStyle(fontSize = 16.sp)
        )

        IconButton(
            onClick = {
                val temp = primarySelectedOption.value
                primarySelectedOption.value = secondarySelectedOption.value
                secondarySelectedOption.value = temp
            },
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(R.drawable.double_arrow_black),
                contentDescription = "conversion_icon"
            )
        }

        DropDownButton(
            expanded = secondaryButtonIsExpanded,
            selectedOption = secondarySelectedOption,
            dropDownOptions = dropDownOptions,
            width = 130.dp,
            height = 50.dp,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
        )
    }
}
