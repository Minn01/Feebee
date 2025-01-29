package com.example.feebee_android_project_app.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class APIres (
    @SerialName("time_last_update_utc") val timeOfLastUpdate: String,
    @SerialName("time_next_update_utc") val timeOfNextUpdate: String,
    @SerialName("base_code") val currCountryCode: String,
    @SerialName("conversion_rates") val conversionRates: Map<String, Double>
)
