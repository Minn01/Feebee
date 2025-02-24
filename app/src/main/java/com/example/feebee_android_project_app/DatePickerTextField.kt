package com.example.feebee_android_project_app

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun DatePickerTextField(
    dateString: MutableState<String>,
    context: Context,
    yearSelected: MutableState<String>,
    monthSelected: MutableState<String>,
    dateRangeSelected: MutableState<String>,
    onButtonClicked: () -> Unit,
    modifier: Modifier
) {
    val showDatePicker = rememberSaveable { mutableStateOf(false) }
    val keyBoardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = dateString.value,
        label = { Text("DD/MM/YYYY") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                // Pressing Enter on text field
                try {
                    val parsedDate = parseDate(dateString.value)
                    if (parsedDate != null) {
                        yearSelected.value = parsedDate.year.toString()
                        monthSelected.value = parsedDate.month.toString()
                    }
                    keyBoardController?.hide()
                } catch (e: Exception) {
                    Toast.makeText(context, "Wrong Format Try Again", Toast.LENGTH_SHORT).show()
                }
            }
        ),
        trailingIcon = {
            IconButton(
                onClick = {
                    showDatePicker.value = true
                }
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "date_range_icon")
            }
        },
        onValueChange = {
            dateString.value = it
            yearSelected.value = "All"
            monthSelected.value = "All"
        },
        modifier = modifier.fillMaxWidth()
    )

    if (showDatePicker.value) {
        DateRangePickerModal(
            dateString = dateString,
            context = context,
            onDismiss = {
                showDatePicker.value = false
            },
            onButtonClicked = onButtonClicked,
            dateRange = dateRangeSelected,
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerModal(
    dateString: MutableState<String>,
    context: Context,
    dateRange: MutableState<String>,
    onButtonClicked: () -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    try {
                        dateRange.value = formatDateRange(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                        dateString.value = dateRange.value
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error Please Try Again", Toast.LENGTH_SHORT).show()
                    }
                    onButtonClicked()
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Select date range",
                    modifier = Modifier.padding(start = 16.dp)
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(16.dp)
        )
    }
}

fun parseDate(dateString: String): LocalDate? {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return LocalDate.parse(dateString, formatter)
}

fun formatDateRange(startMillis: Long?, endMillis: Long?): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    val startDate = startMillis?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
    } ?: "N/A"

    val endDate = endMillis?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
    } ?: "N/A"

    if (endDate == "N/A") {
        return startDate
    }

    return "$startDate-$endDate"
}
