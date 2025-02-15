package com.example.feebee_android_project_app.accountScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.feebee_android_project_app.DatePickerTextField
import com.example.feebee_android_project_app.convertMillisToDate
import com.example.feebee_android_project_app.data.DateData
import com.example.feebee_android_project_app.sideNavigationDrawer.CustomToggleButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleAccountScreen(
    modifier: Modifier
) {
    val yearButtonExpanded = rememberSaveable { mutableStateOf(false) }
    val yearSelected = rememberSaveable { mutableStateOf("All") }

    val monthButtonExpanded = rememberSaveable { mutableStateOf(false) }
    val monthSelected = rememberSaveable { mutableStateOf("All") }


    val datePickerState = rememberDatePickerState()
    val showDatePicker = rememberSaveable { mutableStateOf(false) }

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    val isChecked = rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.padding(top = 30.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.defaultMinSize(minHeight = 260.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    ) {
                        DateRow(
                            rowText = "Year:",
                            buttonExpanded = yearButtonExpanded,
                            selectedOption = yearSelected,
                            buttonOptions = DateData.yearOptions,
                            modifier = Modifier
                        )

                        DateRow(
                            rowText = "Month:",
                            buttonExpanded = monthButtonExpanded,
                            selectedOption = monthSelected,
                            buttonOptions = DateData.monthOptions,
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Text(
                            "Pick a date:",
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        DatePickerTextField(
                            showDatePicker = showDatePicker,
                            datePickerState = datePickerState,
                            selectedDate = selectedDate,
                            modifier = Modifier
                        )
                    }
                }

                CustomToggleButton(
                    modifier = Modifier.padding(top = 16.dp),
                    mode1Text = "Income",
                    mode2Text = "Expense",
                    thumbOffSetX1 = 165.dp,
                    thumbOffSetX2 = 20.dp,
                    thumbOffSetY = 5.dp,
                    outerBoxHeight = 40.dp,
                    isChecked = isChecked,
                    iconEnabled = false,
                    toggleButtonClicked = { isChecked.value = !isChecked.value },
                    backgroundColor = Color(0xFF999999),
                    buttonShadeColor = Color(0xFF000000).copy(alpha = 0.7f),
                    mode1ContentDescription = "incomes",
                    mode2ContentDescription = "expenses",
                    innerBoxWidth = 160.dp,
                    innerBoxHeight = 30.dp,
                    shape = RoundedCornerShape(8.dp)
                )

                Button(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(50),
                    onClick = {}
                ) {
                    Text("Add Expense")
                }

                DateText(
                    dateText = "02/01/2005",
                    modifier = Modifier
                )
            }
        }

        items(10) {
            ExpenseCard(Modifier)
        }
    }
}
