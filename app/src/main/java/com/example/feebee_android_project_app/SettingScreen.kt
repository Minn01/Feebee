package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.dataStore
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feebee_android_project_app.data.RoomViewModel
import com.example.feebee_android_project_app.exchangeRateScreen.ExchangeRateViewModel
import com.example.feebee_android_project_app.exchangeRateScreen.ExchangeRateViewModel_HiltModules

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier.padding(start = 16.dp)
    ) {
        val settingViewModel: SettingViewModel = hiltViewModel()
        val selectedLanguage = settingViewModel.appLanguage.collectAsState() // Observe language from ViewModel

        val languageDDExpanded = rememberSaveable { mutableStateOf(false) }

        val exchangeRateViewModel: ExchangeRateViewModel = hiltViewModel()
        val countryCodes = exchangeRateViewModel.countryCodes.collectAsState()
        val countryCodeDDExpanded = rememberSaveable { mutableStateOf(false) }
        val countryCodeSelected = rememberSaveable { mutableStateOf("") }

        val authViewModel: AuthViewModel = hiltViewModel()
        val context = LocalContext.current

        Text("Pick your language: ")
        DropDownButton(
            expanded = languageDDExpanded,
            selectedOption = remember { mutableStateOf(selectedLanguage.value) },  // Update from StateFlow
            dropDownOptions = listOf("English", "Thai", "Burmese"),
            readOnly = true,
            dropDownLabel = { Text("Select a language") },
            onOptionsSelected = { option ->
                settingViewModel.setAppLanguage(context, option)
            },
            width = 300.dp,
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
            onOptionsSelected = { option ->
                exchangeRateViewModel.setBasedCountryCode(option)
            },
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
