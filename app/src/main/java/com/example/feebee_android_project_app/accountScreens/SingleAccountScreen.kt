package com.example.feebee_android_project_app.accountScreens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.feebee_android_project_app.DatePickerTextField
import com.example.feebee_android_project_app.data.DateData
import com.example.feebee_android_project_app.data.RoomViewModel
import com.example.feebee_android_project_app.data.Transaction
import com.example.feebee_android_project_app.sideNavigationDrawer.CustomToggleButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter

enum class PickedState {
    DATE, DATE_RANGE, YEAR_MONTH, YEAR, MONTH, UNDETERMINED
}

@Composable
fun SingleAccountScreen(
    accountId: Int,
    modifier: Modifier
) {
    val roomViewModel: RoomViewModel = hiltViewModel()

    // for the date picker
    val context = LocalContext.current
    val yearButtonExpanded = rememberSaveable { mutableStateOf(false) }
    val yearSelected = rememberSaveable { mutableStateOf("All") }
    val monthButtonExpanded = rememberSaveable { mutableStateOf(false) }
    val monthSelected = rememberSaveable { mutableStateOf("All") }
    val dateRangeSelected = rememberSaveable { mutableStateOf("") }
    val dateString = rememberSaveable { mutableStateOf("") }

    // for the toggle button
    val isChecked = rememberSaveable { mutableStateOf(false) }

    val pickedState = checkPickedState(yearSelected.value, monthSelected.value, dateRangeSelected.value)
    val transactionType = if (isChecked.value) "income" else "expense"

    LaunchedEffect(accountId, transactionType) {
        // TODO: implement expense list querying and sticky header is needed as well
        if (pickedState == PickedState.UNDETERMINED) {
            roomViewModel.getAllTransactionsFromAccount(accountId, transactionType)
        } else if (pickedState == PickedState.DATE_RANGE) {

        } else if (pickedState == PickedState.DATE) {

        } else if (pickedState == PickedState.MONTH) {

        } else if (pickedState == PickedState.YEAR) {

        }
    }
    val transactionList = roomViewModel.transactionList.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.padding(top = 30.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.defaultMinSize(minHeight = 260.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    ) {
                        DateRow(
                            rowText = "Year:",
                            buttonExpanded = yearButtonExpanded,
                            selectedOption = yearSelected,
                            buttonOptions = DateData.yearOptions,
                            onDropDownButtonClicked = {
                                dateString.value = ""
                            },
                            modifier = Modifier
                        )

                        DateRow(
                            rowText = "Month:",
                            buttonExpanded = monthButtonExpanded,
                            selectedOption = monthSelected,
                            buttonOptions = DateData.monthOptions,
                            onDropDownButtonClicked = {
                                dateString.value = ""
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        Text(
                            "Pick a date:",
                            modifier = Modifier.padding(top = 16.dp)
                        )

                        DatePickerTextField(
                            dateString = dateString,
                            context = context,
                            yearSelected = yearSelected,
                            monthSelected = monthSelected,
                            dateRangeSelected = dateRangeSelected,
                            modifier = Modifier,
                            onButtonClicked = {
                                if (dateRangeSelected.value.length > 10) {
                                    dateRangeSelected.value = dateRangeSelected.value.slice(0..<10)
                                }
                                yearSelected.value = getDate(context = context, dateRangeSelected.value).year.toString()
                                monthSelected.value = getDate(context = context, dateRangeSelected.value).month.toString()
                            },
                        )
                    }
                }

                CustomToggleButton(
                    modifier = Modifier.padding(top = 16.dp),
                    mode1Text = "Income",
                    mode2Text = "Expense",
                    thumbOffSetX1 = 180.dp,
                    thumbOffSetX2 = 30.dp,
                    thumbOffSetY = 5.dp,
                    outerBoxHeight = 40.dp,
                    isChecked = isChecked,
                    iconEnabled = false,
                    toggleButtonClicked = { isChecked.value = !isChecked.value },
                    backgroundColor = Color(0xFF999999),
                    buttonShadeColor = Color(0xFF000000).copy(alpha = 0.7f),
                    mode1ContentDescription = "incomes",
                    mode2ContentDescription = "expenses",
                    innerBoxWidth = 170.dp,
                    innerBoxHeight = 30.dp,
                    shape = RoundedCornerShape(8.dp)
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(50),
                    onClick = {
                        roomViewModel.insertTransaction(
                            Transaction(

                            )
                        )
                    }
                ) {
                    Text("Add Expense")
                }

                DateText(
                    dateText = "02/01/2005",
                    modifier = Modifier
                )
            }
        }

        items(transactionList.value) {
            ExpenseCard(Modifier)
        }

    }
}

fun checkPickedState(
    yearSelected: String,
    monthSelected: String,
    dateRangeSelected: String
): PickedState {
    // TODO: Maybe needs fixing
    if (dateRangeSelected.isNotEmpty()) {
        if (dateRangeSelected.length > 10) {
            return PickedState.DATE_RANGE
        }
        return PickedState.DATE
    }

    if (monthSelected != "All" && yearSelected != "All") {
        return PickedState.YEAR_MONTH
    }

    if (monthSelected != "All") {
        return PickedState.MONTH
    }

    if (yearSelected == "All") {
        return PickedState.YEAR
    }

    return PickedState.UNDETERMINED
}

fun getDate(
    context: Context,
    date: String,
): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    try {
        return LocalDate.parse(date, formatter)
    } catch (e: Exception) {
        Toast.makeText(context, "Error 10", Toast.LENGTH_SHORT).show()
    }

    return LocalDate.now()
}
