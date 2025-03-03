package com.example.feebee_android_project_app.data

import java.time.LocalDate

data class FirestoreAccount(
    val accountId: Int = 0,      // ✅ Keep as Int
    val accountName: String = "",
    val accountBalance: Double = 0.0,
    val basedCurrency: String = "",
    val createdDate: String = "" // ✅ Convert LocalDate to String
) {
    fun toAccount(): Account {
        return Account(
            accountId = accountId,
            accountName = accountName,
            accountBalance = accountBalance,
            basedCurrency = basedCurrency,
            createdDate = LocalDate.parse(createdDate) // ✅ Convert back to LocalDate
        )
    }
}
