package com.example.feebee_android_project_app.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import coil3.Uri
import java.time.LocalDate

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
    val accountId: Int = 0,
    val transactionTitle: String = "",
    val transactionType: String = "",
    val transactionAmount: Double = 0.0,
    val transactionDetails: String = "",
    val transactionImageUri: String = "",
    val createdDate: LocalDate = LocalDate.now()
)