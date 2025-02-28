package com.example.feebee_android_project_app.appBars

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.feebee_android_project_app.R
import com.example.feebee_android_project_app.TransactionBottomSheet
import com.example.feebee_android_project_app.accountScreens.addTransaction
import com.example.feebee_android_project_app.accountScreens.removeDateState
import com.example.feebee_android_project_app.data.AppScreens
import com.example.feebee_android_project_app.data.darkModeColors
import com.example.feebee_android_project_app.data.lightModeColors

@Composable
fun AppBarBottom(
    appTheme: State<String>,
    selectedItem: MutableIntState,
    selectedTab: MutableIntState,
    navController: NavController,
    modifier: Modifier
) {
    BottomAppBar(
        actions = {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                listOf(
                    R.drawable.home_icon_dark to AppScreens.Home.route,
                    R.drawable.accounts_icon_dark to AppScreens.Accounts.route,
                    R.drawable.double_arrow_black to AppScreens.QuickActionScreen.route,
                    R.drawable.exchange_rate_icon_dark to AppScreens.ExchangeRate.route,
                    R.drawable.budget_icon_dark to AppScreens.BudgetControl.route,
                ).forEachIndexed { index, itemPair ->
                    if (index == 2) {
                        QuickActionButton(
                            iconLabel = itemPair.second,
                            onButtonClicked = {
                            },
                            modifier = Modifier
                        )
                    } else {
                        BottomNavButton(
                            iconImage = itemPair.first,
                            iconLabel = itemPair.second,
                            isSelected = selectedItem.intValue == index,
                            onButtonClicked = {
                                selectedTab.intValue = 0
                                selectedItem.intValue = index
                                navController.navigate(itemPair.second)
                            },
                            modifier = Modifier
                        )
                    }
                }
            }
        },
        containerColor = if (appTheme.value == "dark") darkModeColors.barColor else lightModeColors.barColor,
        modifier = modifier
    )
}
