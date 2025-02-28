package com.example.feebee_android_project_app.exchangeRateScreen

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feebee_android_project_app.data.APIres
import com.example.feebee_android_project_app.data.DataStoreManager
import com.example.feebee_android_project_app.httpClient
import dagger.hilt.android.lifecycle.HiltViewModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private val _conversionRates = MutableStateFlow(mapOf("" to 0.0))
    private val _timeOfLastUpdate = MutableStateFlow("")
    private val _timeOfNextUpdate = MutableStateFlow("")
    private val _countryCodes = MutableStateFlow(listOf(""))
    private val _basedCountryCode = MutableStateFlow("USD")

    val conversionRates: StateFlow<Map<String,Double>>  = _conversionRates
    val timeOfLastUpdate: StateFlow<String> = _timeOfLastUpdate
    val timeOfNextUpdate: StateFlow<String> = _timeOfNextUpdate
    val countryCodes: StateFlow<List<String>> = _countryCodes
    val basedCountryCode: StateFlow<String> = _basedCountryCode

    init {
        getBasedCurrency()
        viewModelScope.launch {
            fetchExchangeRates(basedCountryCode.value)
        }
    }

    private fun fetchExchangeRates(basedCountryCode: String) {
        val apiKey = "79413bd8b3a9d1fcbcf6a67d"
        val url = "https://v6.exchangerate-api.com/v6/$apiKey/latest/$basedCountryCode"

        viewModelScope.launch {
            try {
                val response = httpClient.get(url).body<APIres>()
                _conversionRates.value = response.conversionRates
                _timeOfLastUpdate.value = response.timeOfLastUpdate
                _timeOfNextUpdate.value = response.timeOfNextUpdate
                _countryCodes.value = _conversionRates.value.keys.toList()
            } catch (e: Exception) {
                Log.d("API_ERROR", e.message.toString())
                _conversionRates.value = emptyMap()
                _timeOfLastUpdate.value = ""
                _timeOfNextUpdate.value = ""
                _countryCodes.value = emptyList()
            }
        }
    }

    private fun getBasedCurrency() {
        viewModelScope.launch {
            dataStoreManager.getBasedCurrency().collect {
                _basedCountryCode.value = it
            }
        }
    }
}