package com.example.feebee_android_project_app

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.feebee_android_project_app.data.Account
import com.example.feebee_android_project_app.data.AccountDAO
import com.example.feebee_android_project_app.data.AppDatabase
import com.example.feebee_android_project_app.data.Transaction
import com.example.feebee_android_project_app.data.TransactionDAO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.time.LocalDate

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

// Instrumented Testing for the room database queries
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var db: AppDatabase
    private lateinit var accountDao: AccountDAO
    private lateinit var transactionDao: TransactionDAO

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        accountDao = db.accountDAO()
        transactionDao = db.transactionDAO()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndRetrieveAccount() = runBlocking {
        val account = Account(accountName = "Savings", accountType = "Bank", accountBalance = 1000.0)
        accountDao.insertAccount(account)
        val accounts = accountDao.getAllAccounts().first()
        assertEquals(1, accounts.size)
        assertEquals("Savings", accounts[0].accountName)
    }

    @Test
    fun insertAndRetrieveTransaction() = runBlocking {
        val account = Account(accountName = "Savings", accountType = "Bank", accountBalance = 1000.0)
        accountDao.insertAccount(account)
        val accountId = accountDao.getAccountIdFromName("Savings").first()

        val transaction = Transaction(
            accountId = accountId,
            transactionTitle = "Groceries",
            transactionType = "Expense",
            transactionAmount = 50.0,
            createdDate = LocalDate.now()
        )
        transactionDao.insertTransaction(transaction)

        val transactions = transactionDao.getAllTransactionsFromAccount(accountId, "Expense").first()
        assertEquals(1, transactions.size)
        assertEquals("Groceries", transactions[0].transactionTitle)
    }
}

