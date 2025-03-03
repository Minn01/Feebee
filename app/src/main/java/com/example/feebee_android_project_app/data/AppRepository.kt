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
    fun getAccountBalanceFromAccount(accountId: Int): Flow<Double> = accountDAO.getAccountBalanceFromAccount(accountId)
    fun deleteAccount(accountId: Int) = accountDAO.deleteAccount(accountId)
    fun getAllAccounts(): Flow<List<Account>> = accountDAO.getAllAccounts()
    fun getAccountNames(): Flow<List<String>> = accountDAO.getAccountNames()
    fun getTotalAcrossAccounts(): Flow<Double> = accountDAO.getTotalAcrossAccounts()
    fun getAccountIdFromName(accountName: String): Flow<Int> = accountDAO.getAccountIdFromName(accountName)
    suspend fun updateAccountBalanceAccount(accountId: Int, amount: Double) = accountDAO.updateAccountBalance(accountId, amount)
    suspend fun updateAccount(account: Account) = accountDAO.updateAccount(account)
    fun getTransactionAmountAcrossAccountsOfDate(transactionType: String, createdDate: String): Flow<Double> {
        return transactionDAO.getTransactionAmountAcrossAccountsOfDate(transactionType, createdDate)
    }

    // Transaction Operations
    fun insertTransaction(transaction: Transaction) = transactionDAO.insertTransaction(transaction)
    fun getTransactionFromId(transactionId: Int): Flow<Transaction> = transactionDAO.getTransactionFromId(transactionId)
    fun getTransactionsFromAllAccounts(transactionType: String): LiveData<Double> = transactionDAO.getTransactionsFromAllAccounts(transactionType)
    fun deleteTransaction(transactionId: Int) = transactionDAO.deleteTransaction(transactionId)
    fun getAllTransactionsFromAccount(accountId: Int, transactionType: String): Flow<List<Transaction>> = transactionDAO.getAllTransactionsFromAccount(accountId, transactionType)
    fun getTransactionWithinYear(accountId: Int, transactionType: String, year: String): Flow<List<Transaction>> = transactionDAO.getTransactionWithinYear(accountId, transactionType, year)
    fun getTransactionWithinMonth(accountId: Int, transactionType: String, month: String): Flow<List<Transaction>> = transactionDAO.getTransactionWithinMonth(accountId, transactionType, month)
    fun getTransactionOfDate(accountId: Int, transactionType: String, date: LocalDate): Flow<List<Transaction>> = transactionDAO.getTransactionOfDate(accountId, transactionType, date)
    fun getTransactionsByYearAndMonth(accountId: Int, transactionType: String, year: String, month: String) = transactionDAO.getTransactionsByYearAndMonth(accountId, transactionType, year, month)
    fun getTransactionsWithinDateRange(accountId: Int, transactionType: String, startDate: String, endDate: String) = transactionDAO.getTransactionsWithinDateRange(accountId, transactionType, startDate, endDate)
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDAO.getAllTransactions()
    fun getCurrentMonthTransactions(transactionType: String): Flow<Double> = transactionDAO.getCurrentMonthTransactions(transactionType = transactionType)
    fun getCurrentYearTransactions(transactionType: String): Flow<Double> = transactionDAO.getCurrentYearTransactions(transactionType)
    suspend fun updateTransaction(transaction: Transaction) = transactionDAO.updateTransaction(transaction)

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