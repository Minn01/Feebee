package com.example.feebee_android_project_app.accountScreens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feebee_android_project_app.AuthViewModel
import com.example.feebee_android_project_app.DropDownButton
import com.example.feebee_android_project_app.data.Account
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors
import com.example.feebee_android_project_app.exchangeRateScreen.ExchangeRateViewModel
import java.time.LocalDate

@Composable
fun AccountDialog (
    onDismiss: () -> Unit,
    onSave: (Account) -> Unit
) {
    // Remember states for each input field.
    val accountName = rememberSaveable { mutableStateOf("") }
    val accountType = rememberSaveable { mutableStateOf("") }
    val accountBalance = rememberSaveable { mutableStateOf("") } // We'll convert this to Double.
    val basedCurrency = rememberSaveable { mutableStateOf("") }
    // For the created date, we'll use the current date by default.
    val createdDate = rememberSaveable { mutableStateOf(LocalDate.now()) }
    // Context and ViewModel
    val context = LocalContext.current
    val exchangeRateViewModel: ExchangeRateViewModel = hiltViewModel()
    val countryCodes = exchangeRateViewModel.countryCodes.collectAsState(initial = emptyList())

    // Define account type options and dropdown state.
    val accountTypeOptions = listOf("Savings", "Checking", "Credit", "Investment")
    val accountTypeExpanded = rememberSaveable { mutableStateOf(false) }

    // Based currency dropdown state.
    val basedCurrencyExpanded = rememberSaveable { mutableStateOf(false) }

    val authViewModel: AuthViewModel = hiltViewModel()
    authViewModel.getAppTheme()
    val appTheme = authViewModel.themeState.collectAsState()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Create New Account") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                OutlinedTextField(
                    value = accountName.value,
                    onValueChange = { accountName.value = it },
                    label = { Text("Account Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Drop-down for Account Type.
                DropDownButton(
                    expanded = accountTypeExpanded,
                    selectedOption = accountType,
                    dropDownOptions = accountTypeOptions,
                    dropDownLabel = { Text("Account Type") },
                    width = 200.dp,
                    height = 56.dp,
                    textStyle = LocalTextStyle.current,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = accountBalance.value,
                    onValueChange = { accountBalance.value = it },
                    label = { Text("Account Balance") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Drop-down for Based Currency.
                DropDownButton(
                    expanded = basedCurrencyExpanded,
                    selectedOption = basedCurrency,
                    dropDownOptions = countryCodes.value,
                    dropDownLabel = { Text("Based Currency") },
                    width = 200.dp,
                    height = 56.dp,
                    textStyle = LocalTextStyle.current,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Display the created date. Optionally, add a date picker.
                Text(text = "Created Date: ${createdDate.value}")
            }
        },
        confirmButton = {
            Button(
                colors = if (appTheme.value == "light") lightModeColors.customButtonColors else darkModeColors.customButtonColors,
                onClick = {
                    // Convert balance to Double or use default if conversion fails.
                    val balance = accountBalance.value.toDoubleOrNull() ?: 0.0

                    // Validate required fields.
                    if (accountName.value.isEmpty() || accountType.value.isEmpty() || basedCurrency.value.isEmpty()) {
                        Toast.makeText(context, "Please fill all the values", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val newAccount = Account(
                        accountName = accountName.value,
                        accountType = accountType.value,
                        accountBalance = balance,
                        basedCurrency = basedCurrency.value,
                        createdDate = createdDate.value
                    )
                    onSave(newAccount)
                }
            ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}
