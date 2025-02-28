package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feebee_android_project_app.exchangeRateScreen.ExchangeRateViewModel

@Composable
fun SettingScreen(
    modifier: Modifier
) {
    Column(
        modifier
    ) {
        val languageDDExpanded = rememberSaveable { mutableStateOf(false) }
        val languageSelected = rememberSaveable { mutableStateOf("") }

        val exchangeRateViewModel: ExchangeRateViewModel = hiltViewModel()
        val countryCodes = exchangeRateViewModel.countryCodes.collectAsState()
        val countryCodeDDExpanded = rememberSaveable { mutableStateOf(false) }
        val countryCodeSelected = rememberSaveable { mutableStateOf("") }

        val authViewModel: AuthViewModel = hiltViewModel()

        Text("Pick your language: ")
        DropDownButton(
            expanded = languageDDExpanded,
            selectedOption = languageSelected,
            dropDownOptions = listOf("English", "Thai", "Burmese"),
            readOnly = true,
            dropDownLabel = { Text("Select a language") },
            width = 200.dp,
            height = 56.dp,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier,
        )
        Spacer(modifier = Modifier.height(20.dp))
        DropDownButton(
            expanded = countryCodeDDExpanded,
            selectedOption = countryCodeSelected,
            dropDownOptions = countryCodes.value,
            readOnly = true,
            dropDownLabel = { Text("country code") },
            width = 200.dp,
            height = 56.dp,
            textStyle = TextStyle(fontSize = 16.sp),
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextButton(onClick = {
            authViewModel.signOut()
            authViewModel.clearDataStore()
        }) {
            Text("logout")
        }
    }
}
