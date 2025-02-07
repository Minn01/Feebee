package com.example.feebee_android_project_app.exchangeRateScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feebee_android_project_app.data.APIres
import com.example.feebee_android_project_app.httpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ExchangeRateViewModel: ViewModel() {
    private val _conversionRates = MutableStateFlow(mapOf("" to 0.0))
    private val _timeOfLastUpdate = MutableStateFlow("")
    private val _timeOfNextUpdate = MutableStateFlow("")
    private val _countryCodes = MutableStateFlow(listOf(""))

    val conversionRates = _conversionRates
    val timeOfLastUpdate = _timeOfLastUpdate
    val timeOfNextUpdate = _timeOfNextUpdate
    val countryCodes = _countryCodes

    val basedCountryCode = "USD"

    init {
        viewModelScope.launch {
            fetchExchangeRates(basedCountryCode)
        }
    }

    fun fetchExchangeRates(basedCountryCode: String) {
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
            }
        }
    }
}