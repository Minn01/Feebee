package com.example.feebee_android_project_app.homeScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.feebee_android_project_app.AuthViewModel
import com.example.feebee_android_project_app.accountScreens.ExpenseCard
import com.example.feebee_android_project_app.accountScreens.checkPickedState
import com.example.feebee_android_project_app.accountScreens.getTransactions
import com.example.feebee_android_project_app.data.RoomViewModel
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors
import com.example.feebee_android_project_app.sideNavigationDrawer.CustomToggleButton

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier
) {
    val roomViewModel: RoomViewModel = hiltViewModel()
    val accountLists = roomViewModel.accountList.collectAsState()
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    val yearSelected = rememberSaveable { mutableStateOf("All") }
    val monthSelected = rememberSaveable { mutableStateOf("All") }
    val isChecked = rememberSaveable { mutableStateOf(false) }

    val selectedAccount = accountLists.value.getOrNull(selectedIndex.intValue)
    val transactionType = if (isChecked.value) "expense" else "income"

    val pickedState = checkPickedState(yearSelected.value, monthSelected.value, "")
    val context = LocalContext.current

    val groupBy = rememberSaveable { mutableStateOf(GroupBy.MONTH) }

    val authViewModel: AuthViewModel = hiltViewModel()
    authViewModel.getAppTheme()
    val appTheme = authViewModel.themeState.collectAsState()

    LaunchedEffect(selectedAccount, yearSelected.value, monthSelected.value, transactionType) {
        selectedAccount?.let {
            getTransactions(
                selectedAccount.accountId, transactionType,
                roomViewModel = roomViewModel,
                pickedState = pickedState,
                yearSelected = yearSelected.value,
                monthSelected = monthSelected.value,
                dateRangeSelected = "",
                context = context,
            )
        }
    }

    val transactionList = roomViewModel.transactionList.collectAsState()
    // Convert transactions into data points for the chart using `createdDate`

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        item {
            AccountSelector(accountLists, selectedIndex, appTheme) {}

            DateFilter(yearSelected, monthSelected, modifier = Modifier, appTheme = appTheme) {}


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
                    if (selectedAccount != null) {
                        roomViewModel.getAllTransactionsFromAccount(selectedAccount.accountId, transactionType)
                    }
                },
                backgroundColor = if (appTheme.value == "light") Color(0xFF999999) else darkModeColors.buttonColor,
                buttonShadeColor = if (appTheme.value == "light") Color(0xFF000000).copy(alpha = 0.7f) else darkModeColors.buttonShadeColor.copy(alpha = 0.7f),
                mode1ContentDescription = "incomes",
                mode2ContentDescription = "expenses",
                innerBoxWidth = 170.dp,
                innerBoxHeight = 30.dp,
                shape = RoundedCornerShape(8.dp)
            )

            Column {

                // Add the BarChart before listing the transactions
                if (transactionList.value.isNotEmpty()) {

                    Button(onClick = {
                        groupBy.value = if (groupBy.value == GroupBy.MONTH) GroupBy.YEAR else GroupBy.MONTH
                    }, modifier = Modifier.padding(top = 16.dp),
                        colors = if (appTheme.value == "light") lightModeColors.customButtonColors else darkModeColors.customButtonColors
                    ) {
                        Text(if (groupBy.value == GroupBy.MONTH) "Switch to Year" else "Switch to Month")
                    }

                    TransactionBarChart(
                        transactionList.value, modifier = Modifier.padding(top = 16.dp),
                        groupBy = groupBy.value,
                    )
                }
            }
        }

        items(transactionList.value) { item ->
            ExpenseCard(
                transaction = item,
                onTransactionClicked = {
                    navController.navigate("transaction/${item.transactionId}")
                },
                modifier = Modifier
            )
        }
    }
}
