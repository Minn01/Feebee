package com.example.feebee_android_project_app.budgetScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun BudgetSetupDialog(
    onDismiss: () -> Unit,
    onConfirm: (Double, String) -> Unit
) {
    val budgetAmount = rememberSaveable { mutableStateOf("") }
    val selectedPeriod = rememberSaveable  { mutableStateOf("Monthly") }

    val budgetPeriods = listOf("monthly", "yearly")

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Set Up Your Budget") },
        text = {
            Column  {
                // Budget Amount Input
                OutlinedTextField(
                    value = budgetAmount.value,
                    onValueChange = { budgetAmount.value = it },
                    label = { Text("Enter Budget Amount") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Budget Period Selection
                Text("Select Budget Period:")
                budgetPeriods.forEach { period ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedPeriod.value = period }
                            .padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = (selectedPeriod.value == period),
                            onClick = { selectedPeriod.value = period }
                        )
                        Text(text = period, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val budget = budgetAmount.value.toDoubleOrNull() ?: 0.0
                    if (budget > 0) {
                        onConfirm(budget, selectedPeriod.value)
                        onDismiss()
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        }
    )
}
