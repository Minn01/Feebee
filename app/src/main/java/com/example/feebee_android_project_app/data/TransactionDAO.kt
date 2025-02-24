package com.example.feebee_android_project_app.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TransactionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTransaction(transaction: Transaction)

    @Query("SELECT SUM(transactionAmount) FROM `TRANSACTION` WHERE transactionType = :transactionType")
    fun getTransactionsFromAllAccounts(transactionType: String): LiveData<Double>

    @Query("DELETE FROM `TRANSACTION` WHERE transactionId = :transactionId")
    fun deleteTransaction(transactionId: Int)

    @Query(
        "SELECT * FROM `TRANSACTION` WHERE accountId = :accountId AND transactionType = :transactionType"
    )
    fun getAllTransactionsFromAccount(accountId: Int, transactionType: String): Flow<List<Transaction>>

    // Fetch transactions within a specific year
    @Query(
        "SELECT * FROM `TRANSACTION` WHERE accountId = :accountId AND transactionType = :transactionType AND strftime('%Y', createdDate) = strftime('%Y', :year)"
    )
    fun getTransactionWithinYear(accountId: Int, transactionType: String, year: LocalDate): Flow<List<Transaction>>

    // Fetch transactions within a specific month
    @Query(
        "SELECT * FROM `TRANSACTION` WHERE accountId = :accountId AND transactionType = :transactionType AND strftime('%Y-%m', createdDate) = strftime('%Y-%m', :month)"
    )
    fun getTransactionWithinMonth(accountId: Int, transactionType: String, month: LocalDate): Flow<List<Transaction>>

    // Fetch transactions of a specific date
    @Query(
        "SELECT * FROM `TRANSACTION` WHERE accountId = :accountId AND transactionType = :transactionType AND createdDate = :date"
    )
    fun getTransactionOfDate(accountId: Int, transactionType: String, date: LocalDate): Flow<List<Transaction>>

    @Query(
        """
        SELECT COALESCE(SUM(transactionAmount), 0.0) 
        FROM `TRANSACTION` 
        WHERE transactionType = :transactionType 
        AND createdDate = :transactionDate
        """
    )
    fun getTransactionAmountAcrossAccountsOfDate(transactionType: String, transactionDate: LocalDate): Flow<Double>

    // Modify a transaction
    @Query(
        """
        UPDATE `TRANSACTION`
        SET transactionType = :transactionType, 
            transactionAmount = :transactionAmount, 
            transactionDetails = :transactionDetails, 
            createdDate = :createdDate 
        WHERE transactionId = :transactionId
        """
    )
    fun modifyTransaction(
        transactionId: Int,
        transactionType: String,
        transactionAmount: Double,
        transactionDetails: String,
        createdDate: LocalDate
    )

    // Fetch transactions within a date range
    @Query(
        """
        SELECT * FROM `TRANSACTION` 
        WHERE accountId = :accountId 
        AND transactionType = :transactionType 
        AND createdDate BETWEEN :startDate AND :endDate
        """
    )
    fun getTransactionsWithinDateRange(
        accountId: Int,
        transactionType: String,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<Transaction>>
}