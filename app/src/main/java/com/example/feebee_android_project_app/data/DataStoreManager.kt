package com.example.feebee_android_project_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

const val USER_PREFERENCES = "user_prefs"

val Context.preferenceDataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)

class DataStoreManager(val context: Context) {
    companion object {
        val EMAIL = stringPreferencesKey("EMAIL")
        val USER_NAME = stringPreferencesKey("USER_NAME")
        val IS_LOGGED_IN = booleanPreferencesKey("IS_LOGGED_IN")
        val APP_THEME_MODE = stringPreferencesKey("APP_THEME_MODE")
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
}