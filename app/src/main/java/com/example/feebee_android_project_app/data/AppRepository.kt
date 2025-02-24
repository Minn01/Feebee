package com.example.feebee_android_project_app.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val accountDAO: AccountDAO,
    private val transactionDAO: TransactionDAO
) {
    // Account Operations
    suspend fun insertAccount(account: Account) {
        accountDAO.insertAccount(account)
    }

    fun getNumOfAccounts(): Flow<Int> = accountDAO.getNumOfAccounts()
    fun getAccountBalanceFromAccount(accountId: Int): Double? = accountDAO.getAccountBalanceFromAccount(accountId)
    fun deleteAccount(accountId: Int) = accountDAO.deleteAccount(accountId)
    fun getAllAccounts(): Flow<List<Account>> = accountDAO.getAllAccounts()
    fun getAccountNames(): Flow<List<String>> = accountDAO.getAccountNames()
    fun getTotalAcrossAccounts(): Flow<Double> = accountDAO.getTotalAcrossAccounts()

    fun getTransactionAmountAcrossAccountsOfDate(transactionType: String, createdDate: LocalDate): Flow<Double> {
        return transactionDAO.getTransactionAmountAcrossAccountsOfDate(transactionType, createdDate)
    }

    // Transaction Operations
    fun insertTransaction(transaction: Transaction) = transactionDAO.insertTransaction(transaction)
    fun getTransactionsFromAllAccounts(transactionType: String): LiveData<Double> = transactionDAO.getTransactionsFromAllAccounts(transactionType)
    fun deleteTransaction(transactionId: Int) = transactionDAO.deleteTransaction(transactionId)
    fun getAllTransactionsFromAccount(accountId: Int, transactionType: String): Flow<List<Transaction>> = transactionDAO.getAllTransactionsFromAccount(accountId, transactionType)
    fun getTransactionWithinYear(accountId: Int, transactionType: String, year: LocalDate): Flow<List<Transaction>> = transactionDAO.getTransactionWithinYear(accountId, transactionType, year)
    fun getTransactionWithinMonth(accountId: Int, transactionType: String, month: LocalDate): Flow<List<Transaction>> = transactionDAO.getTransactionWithinMonth(accountId, transactionType, month)
    fun getTransactionOfDate(accountId: Int, transactionType: String, date: LocalDate): Flow<List<Transaction>> = transactionDAO.getTransactionOfDate(accountId, transactionType, date)

    fun modifyTransaction(
        transactionId: Int,
        transactionType: String,
        transactionAmount: Double,
        transactionDetails: String,
        createdDate: LocalDate
    ) {
        transactionDAO.modifyTransaction(transactionId, transactionType, transactionAmount, transactionDetails, createdDate)
    }
}