package com.example.feebee_android_project_app.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Account::class, Transaction::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDAO(): AccountDAO
    abstract fun transactionDAO(): TransactionDAO
}
