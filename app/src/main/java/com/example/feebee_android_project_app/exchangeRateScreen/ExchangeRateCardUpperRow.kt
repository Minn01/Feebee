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
import androidx.compose.ui.unit.dp
import com.example.feebee_android_project_app.R

@Composable
fun ExchangeRateCardUpperRow (
    primaryButtonIsExpanded: MutableState<Boolean>,
    secondaryButtonIsExpanded: MutableState<Boolean>,
    primarySelectedOption: MutableState<String>,
    secondarySelectedOption: MutableState<String>,
    options: List<String>,
    modifier: Modifier
) {
    Row(
        modifier = modifier
            .padding(top = 25.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DropDownButton(
            expanded = primaryButtonIsExpanded,
            selectedCountry = primarySelectedOption,
            options = options,
            modifier = Modifier
        )

        IconButton(
            onClick = {},
            modifier = Modifier
        ) {
            Icon(
                painter = painterResource(R.drawable.double_arrow_black),
                contentDescription = "conversion_icon"
            )
        }

        DropDownButton(
            expanded = secondaryButtonIsExpanded,
            selectedCountry = secondarySelectedOption,
            options = options,
            modifier = Modifier
        )
    }
}
