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
import kotlinx.coroutines.CoroutineScope

/*
    TODO: Add the screens here for the navigation
 */

@Composable
fun NavigationGraph(
    navController: NavHostController,
    contentPadding: PaddingValues,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.screen,
    ) {
        // Bottom App Bar Navigation
        composable(AppScreens.Home.screen) {
            HomeScreen(
                modifier = Modifier.padding(contentPadding),
                drawerState = drawerState,
                coroutineScope = coroutineScope
            )
        }
        composable(AppScreens.Accounts.screen) {}
        composable(AppScreens.BudgetControl.screen) {}

        // Side Menu Navigation
        composable(AppScreens.Settings.screen) {}
        composable(AppScreens.Notifications.screen) {}
        composable(AppScreens.LanguageSupport.screen) {}
        composable(AppScreens.Guide.screen) {}

        // Both Bottom App Bar & Side Menu Navigation
        composable(AppScreens.ExchangeRate.screen) {}
    }
}
