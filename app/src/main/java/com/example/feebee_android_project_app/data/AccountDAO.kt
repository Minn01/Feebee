package com.example.feebee_android_project_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account)

    @Query("SELECT accountBalance FROM ACCOUNT WHERE accountId = :accountId LIMIT 1")
    fun getAccountBalanceFromAccount(accountId: Int): Flow<Double>

    @Query("DELETE FROM ACCOUNT WHERE accountId = :accountId")
    fun deleteAccount(accountId: Int)

    @Query("SELECT * FROM ACCOUNT")
    fun getAllAccounts(): Flow<List<Account>>

    @Query("SELECT SUM(accountBalance) FROM ACCOUNT")
    fun getTotalAcrossAccounts(): Flow<Double>

    @Query("SELECT COUNT(accountId) FROM ACCOUNT")
    fun getNumOfAccounts(): Flow<Int>

    @Query("SELECT accountName FROM ACCOUNT")
    fun getAccountNames(): Flow<List<String>>

    @Query("SELECT accountId FROM ACCOUNT WHERE accountName = :accountName")
    fun getAccountIdFromName(accountName: String): Flow<Int>

    @Query("""
        UPDATE ACCOUNT 
        SET accountBalance = :amount 
        WHERE accountId = :accountId
    """)
    suspend fun updateAccountBalance(accountId: Int, amount: Double)

    @Update
    suspend fun updateAccount(account: Account)
}
