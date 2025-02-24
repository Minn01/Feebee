package com.example.feebee_android_project_app

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.feebee_android_project_app.accountScreens.AccountMainScreen
import com.example.feebee_android_project_app.accountScreens.SingleAccountScreen
import com.example.feebee_android_project_app.initialSetUpScreens.LoadingScreen
import com.example.feebee_android_project_app.initialSetUpScreens.LoginScreen
import com.example.feebee_android_project_app.initialSetUpScreens.SignupScreen
import com.example.feebee_android_project_app.data.AppScreens
import com.example.feebee_android_project_app.data.AuthState
import com.example.feebee_android_project_app.exchangeRateScreen.ExchangeRateScreen

/*
    TODO: Add the screens here for the navigation
 */

@Composable
fun NavigationGraph(
    navController: NavHostController,
    contentPadding: PaddingValues,
    authViewModel: AuthViewModel,
    isLoggedIn: State<Boolean>
) {
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState) {
        if (authState.value == AuthState.UnAuthenticated) {
            navController.navigate(AppScreens.Login.route) {
                popUpTo(AppScreens.Home.route) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn.value) AppScreens.Home.route else AppScreens.Signup.route,
    ) {
        // Bottom App Bar Navigation
        composable(
            route = AppScreens.Home.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut() }
        ) {
            HomeScreen(Modifier.padding(contentPadding))
        }

        composable(
            AppScreens.Accounts.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() }
        ) {
            AccountMainScreen(
                navController = navController,
                Modifier.padding(contentPadding)
            )
        }

        composable(
            route = "account/{accountId}",
            arguments = listOf(navArgument("accountId") { NavType.IntType })
        ) { backStackEntry ->
            val accountId = backStackEntry.arguments?.getInt("accountId") ?: -1
            SingleAccountScreen(
                accountId = accountId,
                Modifier.padding(contentPadding)
            )
        }

        composable(
            AppScreens.BudgetControl.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() }
        ) {
            BudgetControlScreen(Modifier.padding(contentPadding))
        }

        // Side Menu Navigation
        composable(
            AppScreens.Profile.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() }
        ) {
            ProfileScreen(Modifier.padding(contentPadding))
        }

        composable(
            AppScreens.Settings.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() }
        ) {
            SettingScreen(Modifier.padding(contentPadding))
        }

        composable(
            AppScreens.Notifications.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() }
        ) {
            NotificationScreen(Modifier.padding(contentPadding))
        }

        composable(
            AppScreens.Guide.route
        ) {

        }

        // Both Bottom App Bar & Side Menu Navigation
        composable(
            AppScreens.ExchangeRate.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut() }
        ) {
            ExchangeRateScreen(Modifier.padding(contentPadding))
        }

        // Initial Screens

        composable(AppScreens.Signup.route) {
            SignupScreen(
                authViewModel = authViewModel,
                navController = navController
            )
        }

        composable(AppScreens.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                navController = navController,
            )
        }

        composable(AppScreens.Loading.route) {
            LoadingScreen(
                navController = navController,
                authState = authViewModel.authState.observeAsState().value ?: AuthState.Error("Null Auth State")
            )
        }

    }
}
