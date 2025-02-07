package com.example.feebee_android_project_app.exchangeRateScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ExchangeRateCard(
    primarySelectedOption: MutableState<String>,
    secondarySelectedOption: MutableState<String>,
    primaryNum: MutableState<String>,
    secondaryNum: MutableState<String>,
    onConvertButtonClicked: () -> Unit,
    modifier: Modifier
) {
    val primaryButtonIsExpanded = rememberSaveable { mutableStateOf(false) }
    val secondaryButtonIsExpanded = rememberSaveable { mutableStateOf(false) }

    ElevatedCard(modifier = modifier) {
        ExchangeRateCardUpperRow (
            primaryButtonIsExpanded = primaryButtonIsExpanded,
            secondaryButtonIsExpanded = secondaryButtonIsExpanded,
            primarySelectedOption = primarySelectedOption,
            secondarySelectedOption = secondarySelectedOption,
            modifier = Modifier
        )

        ExchangeRateCardLowerRow (
            primaryNum = primaryNum,
            secondaryNum = secondaryNum,
            primaryTextFieldValueChanged = {
                // TODO: This part may raise errors so fix
                primaryNum.value = it
            },
            modifier = Modifier
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button (
                onClick = onConvertButtonClicked,
                modifier = Modifier
            ) {
                Text("Convert")
            }
        }
    }
}

fun parseCountryCode(countryCode: String): Boolean {
    return false
}
