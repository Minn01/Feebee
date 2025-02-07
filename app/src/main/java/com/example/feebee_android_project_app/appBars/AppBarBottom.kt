package com.example.feebee_android_project_app.appBars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.feebee_android_project_app.R
import com.example.feebee_android_project_app.data.AppScreens

@Composable
fun AppBarBottom(
    selectedItem: MutableIntState,
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
                                navController.navigate(itemPair.second)
                            },
                            modifier = Modifier
                        )
                    } else {
                        BottomNavButton(
                            iconImage = itemPair.first,
                            iconLabel = itemPair.second,
                            isSelected = selectedItem.intValue == index,
                            onButtonClicked = {
                                selectedItem.intValue = index
                                navController.navigate(itemPair.second)
                            },
                            modifier = Modifier
                        )
                    }
                }
            }
        },
        modifier = modifier
    )
}
