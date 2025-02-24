package com.example.feebee_android_project_app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Account::class, Transaction::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun accountDAO(): AccountDAO
    abstract fun transactionDAO(): TransactionDAO
}