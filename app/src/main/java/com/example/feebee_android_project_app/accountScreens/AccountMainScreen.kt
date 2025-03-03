package com.example.feebee_android_project_app.accountScreens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.feebee_android_project_app.AuthViewModel
import com.example.feebee_android_project_app.data.Account
import com.example.feebee_android_project_app.data.RoomViewModel
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors

@Composable
fun AccountMainScreen(
    navController: NavController,
    modifier: Modifier
) {
    val roomViewModel: RoomViewModel = hiltViewModel()
    val numOfAccounts = roomViewModel.numOfAccounts.collectAsState()
    val accountList = roomViewModel.accountList.collectAsState()
    val totalAcrossAccounts = roomViewModel.totalAcrossAccounts.collectAsState()
    val incomeAcrossAccountsToday = roomViewModel.incomeAcrossAccountsToday.collectAsState()
    val expenseAcrossAccountsToday = roomViewModel.expenseAcrossAccountsToday.collectAsState()
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val authViewModel: AuthViewModel = hiltViewModel()
    authViewModel.getAppTheme()
    val appTheme = authViewModel.themeState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp)
    ) {
        item {
            OverViewCard(
                numOfAccounts = numOfAccounts,
                totalAcrossAccounts = totalAcrossAccounts,
                incomeAcrossAccountsToday = incomeAcrossAccountsToday,
                expenseAcrossAccountsToday = expenseAcrossAccountsToday,
                modifier = Modifier
            )

            Button(
                colors = if (appTheme.value == "light") lightModeColors.customButtonColors else darkModeColors.customButtonColors,
                onClick = { showDialog = true },
                modifier = Modifier.padding(top = 16.dp, bottom = 20.dp)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    Text("add account")
                }
            }
        }

        items(accountList.value) { account ->
            AccountCard(
                account = account,
                onAccountClicked = { navController.navigate("account/${account.accountId}/${account.accountName}") },
                modifier = Modifier // Explicitly label the modifier for clarity.
            )
        }
    }

    // Show the dialog if triggered
    if (showDialog) {
        AccountDialog(
            onDismiss = { showDialog = false }
        ) { newAccount ->
            roomViewModel.insertAccount(newAccount)
            showDialog = false
        }
    }
}
