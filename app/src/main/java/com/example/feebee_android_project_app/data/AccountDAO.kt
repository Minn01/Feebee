package com.example.feebee_android_project_app.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account)

    @Query("SELECT accountBalance FROM ACCOUNT WHERE accountId = :accountId")
    fun getAccountBalanceFromAccount(accountId: Int): Double?

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
}
