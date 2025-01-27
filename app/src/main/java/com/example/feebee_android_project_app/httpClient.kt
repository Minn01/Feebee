package com.example.feebee_android_project_app

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

val httpClient = HttpClient(Android) {
    install(ContentNegotiation) {
        json( Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })

        install(Logging) {
            level = LogLevel.ALL
        }
    }
}
