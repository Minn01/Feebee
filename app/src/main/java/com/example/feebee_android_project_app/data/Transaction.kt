package com.example.feebee_android_project_app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "TRANSACTION",
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["accountId"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
) data class Transaction(
    @PrimaryKey(autoGenerate = true) val transactionId: Int = 0,
    val accountId: Int,
    val transactionType: String,
    val transactionAmount: Double,
    val transactionDetails: String,
    val createdDate: String
)
