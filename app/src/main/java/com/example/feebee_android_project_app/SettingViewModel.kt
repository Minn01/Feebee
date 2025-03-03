package com.example.feebee_android_project_app

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feebee_android_project_app.data.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor (
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _appLanguage = MutableStateFlow("")
    val appLanguage: StateFlow<String> = _appLanguage

    private val _basedCountryCode = MutableStateFlow("")
    val basedCountryCode: StateFlow<String> = _basedCountryCode

    init {
        getAppLanguage()
    }

    fun getAppLanguage() {
        viewModelScope.launch {
            dataStoreManager.getAppLanguage().collect {
                _appLanguage.value = it
            }
        }
    }

    fun setAppLanguage(context: Context, language: String) {
        viewModelScope.launch {
            val job = launch {
                dataStoreManager.setAppLanguage(language)
            }

            job.invokeOnCompletion {
                val languageString = languageStringMap[language] ?: "my"
                setAppLocale(context, languageString)
            }
        }
    }


    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // Restart the activity
        val activity = context as? Activity
        activity?.let {
            val intent = it.intent
            it.finish()
            it.startActivity(intent)
        }
    }
}

val languageStringMap = mapOf(
    "English" to "en",
    "Thai" to "th",
    "Burmese" to "my"
)
