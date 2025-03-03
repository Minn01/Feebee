package com.example.feebee_android_project_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

const val USER_PREFERENCES = "user_prefs"

val Context.preferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

@Serializable
data class NotificationData(
    val title: String,
    val message: String,
    val timestamp: Long
)

class DataStoreManager(val context: Context) {
    companion object {
        val EMAIL = stringPreferencesKey("EMAIL")
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
        val APP_THEME_MODE = stringPreferencesKey("APP_THEME_MODE")
        val BASED_CURRENCY = stringPreferencesKey("BASED_CURRENCY")
        val APP_LANGUAGE = stringPreferencesKey("APP_LANGUAGE")

        // 🔹 keys for budget control
        val BUDGET_AMOUNT = doublePreferencesKey("BUDGET_AMOUNT")
        val BUDGET_CYCLE = stringPreferencesKey("BUDGET_CYCLE") // "weekly" or "monthly"

        val BUDGET_THRESHOLD = doublePreferencesKey("BUDGET_THRESHOLD")
        val NOTIFICATION_LIST = stringPreferencesKey("NOTIFICATION_LIST")
    }

    // 🔹 Save budget amount
    suspend fun saveBudgetAmount(amount: Double) {
        context.preferenceDataStore.edit { storedData ->
            storedData[BUDGET_AMOUNT] = amount
        }
    }

    // 🔹 Retrieve budget amount
    fun getBudgetAmount() = context.preferenceDataStore.data.map {
        it[BUDGET_AMOUNT] ?: 0.0
    }

    // 🔹 Save budget cycle (weekly, monthly, yearly)
    suspend fun saveBudgetCycle(cycle: String) {
        context.preferenceDataStore.edit { storedData ->
            storedData[BUDGET_CYCLE] = cycle
        }
    }

    // 🔹 Retrieve budget cycle
    fun getBudgetCycle() = context.preferenceDataStore.data.map {
        it[BUDGET_CYCLE] ?: "monthly"
    }

    // 🔹 Function to check if the budget period has expired
    fun isBudgetExpired(startTimestamp: Long, cycle: String): Boolean {
        val currentTime = System.currentTimeMillis()
        val oneWeekMillis = 7 * 24 * 60 * 60 * 1000L
        val oneMonthMillis = 30 * 24 * 60 * 60 * 1000L // Approximate
        val oneYearMillis = 365 * 24 * 60 * 60 * 1000L // Approximate

        return when (cycle) {
            "weekly" -> currentTime - startTimestamp >= oneWeekMillis
            "monthly" -> currentTime - startTimestamp >= oneMonthMillis
            "yearly" -> currentTime - startTimestamp >= oneYearMillis
            else -> false
        }
    }


    suspend fun saveToUserDataStore(userDetails: UserDetails) {
        context.preferenceDataStore.edit { storedData ->
            storedData[EMAIL] = userDetails.email
            storedData[USER_NAME] = userDetails.userName
        }
    }

    suspend fun setLoginStatus(status: Boolean) {
        context.preferenceDataStore.edit { storedData ->
            storedData[IS_LOGGED_IN] = status
        }
    }

    fun getUserDataFromDataStore() = context.preferenceDataStore.data.map {
        UserDetails (
            email = it[EMAIL] ?: "",
            userName = it[USER_NAME] ?: ""
        )
    }

    fun getBasedCurrency() = context.preferenceDataStore.data.map {
        it[BASED_CURRENCY] ?: "USD"
    }

    suspend fun saveBasedCurrency(basedCurrency: String) {
        context.preferenceDataStore.edit { storedData ->
            storedData[BASED_CURRENCY] = basedCurrency
        }
    }

    suspend fun saveAppTheme(appTheme: String) {
        context.preferenceDataStore.edit { storedData ->
            storedData[APP_THEME_MODE] = appTheme
        }
    }

    fun confirmLoginStatus() = context.preferenceDataStore.data.map {
        it[IS_LOGGED_IN] ?: false
    }

    suspend fun clearDataFromDataStore() = context.preferenceDataStore.edit { storedData ->
        storedData.clear()
    }

    fun getAppThemeFromDataStore() = context.preferenceDataStore.data.map {
        it[APP_THEME_MODE] ?: "light"
    }

    suspend fun setAppTheme(theme: String) {
        context.preferenceDataStore.edit { storedData ->
            storedData[APP_THEME_MODE] = theme
        }
    }
    // 🔹 Save Budget Threshold
    suspend fun saveBudgetThreshold(threshold: Double) {
        context.preferenceDataStore.edit { storedData ->
            storedData[BUDGET_THRESHOLD] = threshold
        }
    }

    // 🔹 Retrieve Budget Threshold
    fun getBudgetThreshold() = context.preferenceDataStore.data.map {
        it[BUDGET_THRESHOLD] ?: 0.0
    }

    // 🔹 Save Notification List
    suspend fun saveNotificationList(notifications: List<NotificationData>) {
        context.preferenceDataStore.edit { storedData ->
            val jsonString = Json.encodeToString(notifications)
            storedData[NOTIFICATION_LIST] = jsonString
        }
    }

    // 🔹 Retrieve Notification List
    fun getNotificationList() = context.preferenceDataStore.data.map {
        val jsonString = it[NOTIFICATION_LIST] ?: "[]"
        Json.decodeFromString<List<NotificationData>>(jsonString)
    }

    suspend fun setAppLanguage(language: String) = context.preferenceDataStore.edit { storedData ->
        storedData[APP_LANGUAGE] = language
    }

    fun getAppLanguage() = context.preferenceDataStore.data.map {
        it[APP_LANGUAGE] ?: "English"
    }
}