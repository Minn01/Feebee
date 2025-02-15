package com.example.feebee_android_project_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDAO {
    @Insert
    fun insertTransaction(transaction: Transaction)

    @Query("SELECT SUM(transactionAmount) FROM `TRANSACTION` WHERE transactionType = :transactionType")
    fun getTransactionsFromAllAccounts(transactionType: String): Double?

    // Delete a transaction
    @Query("DELETE FROM `TRANSACTION` WHERE transactionId = :transactionId")
    fun deleteTransaction(transactionId: Int)

    // Fetch all transactions for an account (filtered by type)
    @Query(
        "SELECT * FROM `TRANSACTION` WHERE accountId = :accountId AND transactionType = :transactionType"
    ) fun getAllTransactionsFromAccount(accountId: Int, transactionType: String): List<Transaction>

    // Fetch transactions within a specific year
    @Query(
        "SELECT * FROM `TRANSACTION` WHERE accountId = :accountId AND transactionType = :transactionType AND strftime('%Y', createdDate) = :year"
    ) fun getTransactionWithinYear(accountId: Int, transactionType: String, year: String): List<Transaction>

    // Fetch transactions within a specific month
    @Query(
        "SELECT * FROM `TRANSACTION` WHERE accountId = :accountId AND transactionType = :transactionType AND strftime('%m', createdDate) = :month"
    ) fun getTransactionWithinMonth(accountId: Int, transactionType: String, month: String): List<Transaction>

    // Fetch transactions of a specific date
    @Query(
        "SELECT * FROM `TRANSACTION` WHERE accountId = :accountId AND transactionType = :transactionType AND createdDate = :date"
    ) fun getTransactionOfDate(accountId: Int, transactionType: String, date: String): List<Transaction>

    // Modify a transaction
    @Query(
        "UPDATE `TRANSACTION` " +
        "SET accountId = :accountId, " +
        "transactionType = :transactionType, " +
        "transactionAmount = :transactionAmount, " +
        "transactionDetails = :transactionDetails, " +
        "createdDate = :createdDate " +
        "WHERE transactionId = :transactionId"
    )
    fun modifyTransaction(
        transactionId: Int,
        transactionType: String,
        transactionAmount: Double,
        transactionDetails: String,
        createdDate: String
    )
}
