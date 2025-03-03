package com.example.feebee_android_project_app.budgetScreens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feebee_android_project_app.NotificationRepository
import com.example.feebee_android_project_app.data.AppRepository
import com.example.feebee_android_project_app.data.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val repository: AppRepository,
    private val notificationRepository: NotificationRepository // ✅ Now using repository for notifications
) : ViewModel() {

    private val _budgetAmount = MutableStateFlow(0.0)
    val budgetAmount: StateFlow<Double> = _budgetAmount

    private val _spentAmount = MutableStateFlow(0.0)
    val spentAmount: StateFlow<Double> = _spentAmount

    private val _budgetCycle = MutableStateFlow("monthly")
    val budgetCycle: StateFlow<String> = _budgetCycle

    init {
        viewModelScope.launch {
            dataStoreManager.getBudgetAmount().collect { _budgetAmount.value = it }
            dataStoreManager.getBudgetCycle().collect { _budgetCycle.value = it }
        }
    }

    fun getCurrentSpending(budgetCycle: String) {
        _spentAmount.value = 0.0 // ✅ Reset spent amount before calculating

        if (budgetCycle == "monthly") {
            getCurrentIncomeMonth()
            getCurrentExpenseMonth()
        } else if (budgetCycle == "yearly") {
            getCurrentIncomeYear()
            getCurrentExpenseYear()
        }
    }

    fun getCurrentIncomeMonth() {
        viewModelScope.launch {
            repository.getCurrentMonthTransactions("income").collect {
                _spentAmount.value -= it
                Log.d("DBG3", _spentAmount.value.toString())
            }
        }
    }

    fun getCurrentExpenseMonth() {
        viewModelScope.launch {
            Log.d("DBG4", _budgetCycle.value)
            repository.getCurrentMonthTransactions("expense").collect {
                _spentAmount.value += it
                Log.d("DBG3", _spentAmount.value.toString())
            }
        }
    }

    fun getCurrentExpenseYear() {
        viewModelScope.launch {
            repository.getCurrentYearTransactions("expense").collect {
                _spentAmount.value += it
            }
        }
    }

    fun getCurrentIncomeYear() {
        viewModelScope.launch {
            Log.d("DBG5", _budgetCycle.value)
            repository.getCurrentYearTransactions("income").collect {
                _spentAmount.value -= it
                Log.d("DBG4", _spentAmount.value.toString())
            }
        }
    }

    fun setBudget(amount: Double) {
        viewModelScope.launch {
            dataStoreManager.saveBudgetAmount(amount)
            _budgetAmount.value = amount
        }
    }

    fun setBudgetCycle(cycle: String) {
        viewModelScope.launch {
            dataStoreManager.saveBudgetCycle(cycle)
            _budgetCycle.value = cycle
        }
    }

    fun checkBudgetExceeded(spentAmount: Double, budgetAmount: Double) {
        if (spentAmount > budgetAmount) {
            notificationRepository.sendBudgetExceededNotification(spentAmount, budgetAmount)
        }
    }
}

