package com.example.feebee_android_project_app.exchangeRateScreen

import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier

@Composable
fun ExchangeRateCard(
    modifier: Modifier
) {
    val options = remember { listOf("USD", "MMK", "THB", "EUR") }

    val primaryButtonIsExpanded = rememberSaveable { mutableStateOf(false) }
    val secondaryButtonIsExpanded = rememberSaveable { mutableStateOf(false) }

    val primarySelectedOption = rememberSaveable { mutableStateOf(options[0]) }
    val secondarySelectedOption = rememberSaveable { mutableStateOf(options[0]) }

    val primaryNum = rememberSaveable { mutableDoubleStateOf(0.0) }
    val secondaryNum = rememberSaveable { mutableDoubleStateOf(0.0) }

    ElevatedCard(modifier = modifier) {
        ExchangeRateCardUpperRow (
            primaryButtonIsExpanded = primaryButtonIsExpanded,
            secondaryButtonIsExpanded = secondaryButtonIsExpanded,
            primarySelectedOption = primarySelectedOption,
            secondarySelectedOption = secondarySelectedOption,
            options = options,
            modifier = Modifier
        )

        ConversionTextFieldRow (
            primaryNum = primaryNum,
            secondaryNum = secondaryNum,
            primaryTextFieldValueChanged = {
                // TODO: This part may raise errors so fix
                primaryNum.doubleValue = it.toDouble()
            },
            secondaryTextFieldValueChanged = {
                // TODO: This part may raise errors so fix
                secondaryNum.doubleValue = it.toDouble()
            },
            modifier = Modifier
        )
    }
}

fun parseCountryCode(countryCode: String): Boolean {
    return false
}
