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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feebee_android_project_app.AuthViewModel
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors

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

    val authViewModel: AuthViewModel = hiltViewModel()
    authViewModel.getAppTheme()
    val appTheme = authViewModel.themeState.collectAsState()

    ElevatedCard(modifier = modifier) {
        ExchangeRateCardUpperRow (
            primaryButtonIsExpanded = primaryButtonIsExpanded,
            secondaryButtonIsExpanded = secondaryButtonIsExpanded,
            primarySelectedOption = primarySelectedOption,
            secondarySelectedOption = secondarySelectedOption,
            appTheme = appTheme,
            modifier = Modifier
        )

        ExchangeRateCardLowerRow (
            primaryNum = primaryNum,
            secondaryNum = secondaryNum,
            primaryTextFieldValueChanged = {
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
                colors = if (appTheme.value == "light") lightModeColors.customButtonColors else darkModeColors.customButtonColors,
                onClick = onConvertButtonClicked,
                modifier = Modifier
            ) {
                Text("Convert")
            }
        }
    }
}
