package com.example.feebee_android_project_app.data

// The screens being used for this project
// sealed classes work similarly to an enum 👌

sealed class AppScreens(val route: String, val title: String) {
    // These screens are going to be for the bottom navigation bar
    data object Home: AppScreens("home", "Home")
    data object Accounts: AppScreens("accounts", "Accounts")
    data object BudgetControl: AppScreens("budgetControl", "Budget Control")
    data object QuickActionScreen: AppScreens("quickAction", "Quick Action")

    // These screens are going to be for the side menu navigation
    data object Profile: AppScreens("profile", "Profile")
    data object Settings: AppScreens("settings", "Settings")
    data object Notifications: AppScreens("notifications", "Notifications")
    data object LanguageSupport: AppScreens("language", "Language")
    data object Guide: AppScreens("guide", "Help")

    // This screen is going to be available for both navigation
    data object ExchangeRate: AppScreens("exchangeRate", "Exchange Rate")

    // Initial Setup Screens
    data object Signup: AppScreens("signup", "Signup")
    data object Login: AppScreens("login", "Login")
    data object Loading: AppScreens("loading", "loading")
}
