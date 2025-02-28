package com.example.feebee_android_project_app.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    private val _numOfAccounts = MutableStateFlow(0)
    val numOfAccounts: StateFlow<Int> = _numOfAccounts

    private val _accountList = MutableStateFlow(emptyList<Account>())
    val accountList: StateFlow<List<Account>> = _accountList

    private val _totalAcrossAccounts = MutableStateFlow(0.0)
    val totalAcrossAccounts : StateFlow<Double> = _totalAcrossAccounts

    private val _incomeAcrossAccountsToday = MutableStateFlow(0.0)
    val incomeAcrossAccountsToday  : StateFlow<Double> = _incomeAcrossAccountsToday

    private val _expenseAcrossAccountsToday = MutableStateFlow(0.0)
    val expenseAcrossAccountsToday  : StateFlow<Double> = _expenseAcrossAccountsToday

    private val _transactionList = MutableStateFlow(emptyList<Transaction>())
    val transactionList: StateFlow<List<Transaction>> = _transactionList

    private val _accountBalance = MutableStateFlow(0.0)
    val accountBalance: StateFlow<Double> = _accountBalance

    private val _selectedAccountId = MutableStateFlow(0)
    val selectedAccountId: StateFlow<Int> = _selectedAccountId

    private val _currentTransaction = MutableStateFlow(Transaction())
    val currentTransaction: StateFlow<Transaction> = _currentTransaction

    init {
        getAccountScreenData()
    }

    fun insertAccount(account: Account) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.insertAccount(account)
        }
    }

    fun deleteAccount(accountId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAccount(accountId)
        }
    }

    fun insertTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transactionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(transactionId)
        }
    }

    fun modifyTransaction(
        transactionId: Int,
        transactionType: String,
        transactionAmount: Double,
        transactionDetails: String,
        createdDate: LocalDate
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.modifyTransaction(transactionId, transactionType, transactionAmount, transactionDetails, createdDate)
        }
    }

    fun getAccountScreenData() {
        getNumOfAccounts()
        getAllAccounts()
        getTotalAcrossAccounts()
        getTransactionAmountAcrossAccountsToday()
    }

    fun getNumOfAccounts() {
        viewModelScope.launch {
            repository.getNumOfAccounts().collect { count ->
                _numOfAccounts.update { count }
            }
        }
    }

    fun getAllAccounts() {
        viewModelScope.launch {
            repository.getAllAccounts().collect {
                _accountList.value = it
            }
        }
    }

    fun getTotalAcrossAccounts() {
        viewModelScope.launch {
            repository.getTotalAcrossAccounts().collect {
                _totalAcrossAccounts.value = it
            }
        }
    }

    fun getTransactionAmountAcrossAccountsToday() {
        val currentDate = LocalDate.now()

        viewModelScope.launch {
            combine(
                repository.getTransactionAmountAcrossAccountsOfDate("income", currentDate),
                repository.getTransactionAmountAcrossAccountsOfDate("expense", currentDate)
            ) { income, expense ->
                _incomeAcrossAccountsToday.value = income
                _expenseAcrossAccountsToday.value = expense
            }.collect {}
        }
    }

    fun getAccountBalance(accountId: Int) {
        viewModelScope.launch {
            repository.getAccountBalanceFromAccount(accountId).collect {
                _accountBalance.value = it
            }
        }
    }

    fun getAccountNames(): Flow<List<String>> = repository.getAccountNames()

    fun getTransactionsFromAllAccounts(transactionType: String): LiveData<Double> = repository.getTransactionsFromAllAccounts(transactionType)

    fun getAllTransactionsFromAccount(accountId: Int, transactionType: String) {
        viewModelScope.launch {
            repository.getAllTransactionsFromAccount(accountId, transactionType).collect {
                _transactionList.value = it.toList()
            }
        }
    }

    fun getTransactionWithinYear(accountId: Int, transactionType: String, year: String) {
        viewModelScope.launch {
            repository.getTransactionWithinYear(accountId, transactionType, year).collect {
                _transactionList.value = it.toList()
            }
        }
    }

    fun getTransactionWithinMonth(accountId: Int, transactionType: String, month: String) {
        viewModelScope.launch {
            repository.getTransactionWithinMonth(accountId, transactionType, month).collect {
                _transactionList.value = it.toList()
            }
        }
    }

    fun getTransactionOfDate(accountId: Int, transactionType: String, date: LocalDate) {
        viewModelScope.launch {
            repository.getTransactionOfDate(accountId, transactionType, date).collect {
                _transactionList.value = it.toList()
            }
        }
    }

    fun updateAccountBalanceAccount(accountId: Int, amount: Double) {
        viewModelScope.launch {
            repository.updateAccountBalanceAccount(accountId = accountId, amount)
        }
    }

    fun getAccountIdFromName(accountName: String) {
        viewModelScope.launch {
            repository.getAccountIdFromName(accountName).collect {
                _selectedAccountId.value = it
            }
        }
    }

    fun getTransactionsByYearAndMonth(accountId: Int, transactionType: String, year: String, month: String) {
        viewModelScope.launch {
            repository.getTransactionsByYearAndMonth(
                accountId, transactionType, year, month
            ).collect {
                _transactionList.value = it.toList()
            }
        }
    }

    fun getTransactionsFromDateRange(accountId: Int, transactionType: String, startDate: LocalDate, endDate: LocalDate) {
        viewModelScope.launch {
            repository.getTransactionsWithinDateRange(
                accountId, transactionType, startDate, endDate
            ).collect {
                _transactionList.value = it.toList()
            }
        }
    }

    fun getTransactionFromId(transactionId: Int) {
        viewModelScope.launch {
            repository.getTransactionFromId(transactionId).collect {
                _currentTransaction.value = it
            }
        }
    }
}
