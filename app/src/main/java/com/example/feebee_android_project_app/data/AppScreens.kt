package com.example.feebee_android_project_app.data

// The screens being used for this project
// sealed classes work similarly to an enum 👌

sealed class AppScreens(val screen: String) {
    // These screens are going to be for the bottom navigation bar
    data object Home: AppScreens("home")
    data object Accounts: AppScreens("accounts")
    data object BudgetControl: AppScreens("budgetControl")

    // These screens are going to be for the side menu navigation
    data object Settings: AppScreens("settings")
    data object Notifications: AppScreens("notifications")
    data object LanguageSupport: AppScreens("language")
    data object Guide: AppScreens("guide")

    // This screen is going to be available for both navigation
    data object ExchangeRate: AppScreens("exchangeRate")
}
