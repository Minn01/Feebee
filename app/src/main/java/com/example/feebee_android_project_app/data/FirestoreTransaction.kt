package com.example.feebee_android_project_app.data

import java.time.LocalDate

data class FirestoreTransaction(
    val transactionId: Int = 0,  // ✅ Keep as Int
    val accountId: Int = 0,      // ✅ Keep as Int
    val amount: Double = 0.0,
    val details: String = "",
    val createdDate: String = "" // ✅ Convert LocalDate to String
) {
    fun toTransaction(): Transaction {
        return Transaction(
            transactionId = transactionId,
            accountId = accountId,
            transactionAmount = amount,
            transactionDetails = details,
            createdDate = LocalDate.parse(createdDate) // ✅ Convert back to LocalDate
        )
    }
}
