package com.example.feebee_android_project_app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AccountDAO {
    @Insert
    fun insertAccount(account: Account)

    @Query("SELECT accountBalance FROM account WHERE accountId = :accountId")
    fun getAccountBalanceFromAccount(accountId: Int)

    @Query("DELETE FROM ACCOUNT WHERE accountId = :accountId")
    fun deleteAccount(accountId: Int)

    @Query("SELECT * FROM ACCOUNT")
    fun getAllAccounts(): List<Account>

    @Query("SELECT accountName FROM ACCOUNT")
    fun getAccountNames(): List<Account>
}
