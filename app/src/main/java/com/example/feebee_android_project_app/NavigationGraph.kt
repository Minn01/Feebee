package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.feebee_android_project_app.data.AppScreens
import com.example.feebee_android_project_app.exchangeRateScreen.ExchangeRateScreen
import kotlinx.coroutines.CoroutineScope

/*
    TODO: Add the screens here for the navigation
 */

@Composable
fun NavigationGraph(
    navController: NavHostController,
    contentPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route,
    ) {
        // Bottom App Bar Navigation
        composable(AppScreens.Home.route) {
            HomeScreen(
                modifier = Modifier.padding(contentPadding)
            )
        }
        composable(AppScreens.Accounts.route) {
            AccountMainScreen(Modifier.padding(contentPadding))
        }
        composable(AppScreens.BudgetControl.route) {}

        // Side Menu Navigation
        composable(AppScreens.Profile.route) {
            ProfileScreen(Modifier.padding(contentPadding))
        }
        composable(AppScreens.Settings.route) {
            SettingScreen(Modifier.padding(contentPadding))
        }
        composable(AppScreens.Notifications.route) {}
        composable(AppScreens.LanguageSupport.route) {}
        composable(AppScreens.Guide.route) {}

        // Both Bottom App Bar & Side Menu Navigation
        composable(AppScreens.ExchangeRate.route) {
            ExchangeRateScreen(modifier = Modifier.padding(contentPadding))
        }
    }
}
