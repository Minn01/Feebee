package com.example.feebee_android_project_app.accountScreens

import android.content.Context
import android.util.Log
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.feebee_android_project_app.DatePickerTextField
import com.example.feebee_android_project_app.TransactionBottomSheet
import com.example.feebee_android_project_app.data.DateData
import com.example.feebee_android_project_app.data.RoomViewModel
import com.example.feebee_android_project_app.data.Transaction
import com.example.feebee_android_project_app.sideNavigationDrawer.CustomToggleButton
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

enum class PickedState {
    DATE, DATE_RANGE, YEAR_MONTH, YEAR, MONTH, NONE_SELECTED
}

@Composable
fun SingleAccountScreen(
    navController: NavController,
    accountId: Int,
    modifier: Modifier
) {
    val roomViewModel: RoomViewModel = hiltViewModel()
    roomViewModel.getAccountBalance(accountId)

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

    // Instead of a regular variable
    var pickedState = remember(yearSelected.value, monthSelected.value, dateRangeSelected.value) {
        checkPickedState(yearSelected.value, monthSelected.value, dateRangeSelected.value)
    }

    val transactionType = remember(isChecked.value) {
        if (isChecked.value) "expense" else "income"
    }

    val showBottomSheet = rememberSaveable { mutableStateOf(false) }

    // Add this effect that will run whenever the necessary parameters change
    LaunchedEffect(accountId, transactionType, pickedState, yearSelected.value, monthSelected.value, dateRangeSelected.value) {
        getTransactions(
            accountId,
            transactionType,
            roomViewModel,
            pickedState,
            yearSelected.value,
            monthSelected.value,
            dateRangeSelected.value,
            context
        )
    }

    val transactionList = roomViewModel.transactionList.collectAsState()
    val accountBalance = roomViewModel.accountBalance.collectAsState()
    Log.d("transactionList", "transactionList: ${transactionList.value}")
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.padding(top = 30.dp)
            ) {
                Text("Account Balance: ${accountBalance.value}",
                    fontSize = 20.sp, modifier = Modifier.padding(bottom = 20.dp)
                )

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
                                pickedState = checkPickedState(yearSelected.value, monthSelected.value, dateRangeSelected.value)
                                getTransactions(accountId, transactionType, roomViewModel, pickedState, yearSelected.value, monthSelected.value, dateRangeSelected.value, context)
                                Log.d("dbg1", "transactionList:${transactionList.value}")
                                dateRangeSelected.value = ""
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
                                pickedState = checkPickedState(yearSelected.value, monthSelected.value, dateRangeSelected.value)
                                getTransactions(accountId, transactionType, roomViewModel, pickedState, yearSelected.value, monthSelected.value, dateRangeSelected.value, context)
                                Log.d("dbg2", "transactionList:${transactionList.value}")
                                dateRangeSelected.value = ""
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
                                if (dateRangeSelected.value.length <= 10) {
                                    dateRangeSelected.value = dateRangeSelected.value.slice(0..<10)
                                }

                                pickedState = checkPickedState(yearSelected.value, monthSelected.value, dateRangeSelected.value)
                                getTransactions(accountId, transactionType, roomViewModel, pickedState, yearSelected.value, monthSelected.value, dateRangeSelected.value, context)
                                Log.d("dbg3", "transactionList:${transactionList.value}")
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
                    toggleButtonClicked = {
                        isChecked.value = !isChecked.value
//                        getTransactions(accountId, transactionType, roomViewModel, pickedState, yearSelected.value, monthSelected.value, dateRangeSelected.value, context)
                    },
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
                        showBottomSheet.value = !showBottomSheet.value
                    }
                ) {
                    Text("Add Expense")
                }

//                DateText(
//                    dateText = "02/01/2005",
//                    modifier = Modifier
//                )
            }

            if (showBottomSheet.value) {
                TransactionBottomSheet(
                    onDismiss = {showBottomSheet.value = !showBottomSheet.value},
                    onSave = { transaction, accountIdToAdd ->
                        showBottomSheet.value = !showBottomSheet.value
                        addTransaction(roomViewModel, transaction, accountIdToAdd, accountBalance.value)
                        Toast.makeText(context, "transaction added", Toast.LENGTH_SHORT).show()
                        removeDateState(yearSelected, monthSelected, dateString, dateRangeSelected)
                        navController.navigate("account/$accountIdToAdd")
                    }
                )
            }
        }

        items(transactionList.value) { item ->
            ExpenseCard(
                transaction = item,
                onTransactionClicked = {
                    navController.navigate("transaction/${item.transactionId}")
                },
                Modifier
            )
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

    if (yearSelected != "All") {
        return PickedState.YEAR
    }

    return PickedState.NONE_SELECTED
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

fun addTransaction(
    roomViewModel: RoomViewModel,
    transaction: Transaction,
    accountIdToAdd: Int,
    accountBalance: Double
) {
    roomViewModel.insertTransaction(transaction)
    val updateAmount = when (transaction.transactionType) {
        "income" -> accountBalance + transaction.transactionAmount
        "expense" -> accountBalance - transaction.transactionAmount
        else -> accountBalance
    }
    roomViewModel.updateAccountBalanceAccount(accountId = accountIdToAdd, amount = updateAmount)
}

fun removeDateState(
    yearSelected: MutableState<String>,
    monthSelected: MutableState<String>,
    dateString: MutableState<String>,
    dateRangeSelected: MutableState<String>,
) {
    yearSelected.value = "All"
    monthSelected.value = "All"
    dateString.value = ""
    dateRangeSelected.value = ""
}

fun getTransactions(
    accountId: Int, transactionType: String, roomViewModel: RoomViewModel,
    pickedState: PickedState, yearSelected: String, monthSelected: String,
    dateRangeSelected: String, context: Context
) {
    when (pickedState) {
        PickedState.DATE_RANGE -> {
            val twoDates = dateRangeSelected.split("-").map { it.trim() }
            val startDate = getDate(context, twoDates.first()).toString()
            val endDate = getDate(context, twoDates.last()).toString()
            roomViewModel.getTransactionsFromDateRange(accountId, transactionType, startDate, endDate)
        }
        PickedState.DATE -> {
            val formatDate = getDate(context, dateRangeSelected)
            roomViewModel.getTransactionOfDate(accountId, transactionType, formatDate)
        }
        PickedState.YEAR_MONTH -> {
            // Format month as a two-digit string (e.g., "01" for January)
            val formattedMonth =  Month.valueOf(monthSelected.uppercase()).value.toString().padStart(2, '0')
            roomViewModel.getTransactionsByYearAndMonth(accountId, transactionType, yearSelected, formattedMonth)
        }
        PickedState.MONTH -> {
            // Format month as a two-digit string
            val formattedMonth = Month.valueOf(monthSelected.uppercase()).value.toString().padStart(2, '0')
            // You need to modify your DAO to handle just month, or use the current year
            roomViewModel.getTransactionWithinMonth(accountId, transactionType, formattedMonth)
        }
        PickedState.YEAR -> {
            roomViewModel.getTransactionWithinYear(accountId, transactionType, yearSelected)
        }
        PickedState.NONE_SELECTED -> {
            roomViewModel.getAllTransactionsFromAccount(accountId, transactionType)
        }
    }
    Log.d("dbgps", "PickedState: $pickedState, TransactionType: $transactionType")
}
