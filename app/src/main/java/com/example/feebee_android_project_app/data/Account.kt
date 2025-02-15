package com.example.feebee_android_project_app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ACCOUNT")
data class Account(
    @PrimaryKey(autoGenerate = true) val accountId: Int = 0,
    val accountName: String,
    val accountType: String,
    val accountBalance: Double,
    val basedCurrency: String,
    val createdDate: String
)
