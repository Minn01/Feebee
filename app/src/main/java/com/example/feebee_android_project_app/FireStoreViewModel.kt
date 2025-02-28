package com.example.feebee_android_project_app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feebee_android_project_app.data.Account
import com.example.feebee_android_project_app.data.AppRepository
import com.example.feebee_android_project_app.data.DataStoreManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FireStoreViewModel @Inject constructor(
    private val firestoreDb: FirebaseFirestore,
    private val repository: AppRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    fun saveAllDataToFireStore() {
        viewModelScope.launch(Dispatchers.IO) {
            val userEmail = getCurrentUserEmail() ?: return@launch

            // Collect user preferences (DataStore)
            val userDetails = dataStoreManager.getUserDataFromDataStore().first()
            val currency = dataStoreManager.getBasedCurrency().first()
            val themeMode = dataStoreManager.getAppThemeFromDataStore().first()
            val isLoggedIn = dataStoreManager.confirmLoginStatus().first()

            // Store user preferences
            val userPrefs = mapOf(
                "email" to userDetails.email,
                "userName" to userDetails.userName,
                "baseCurrency" to currency,
                "appThemeMode" to themeMode,
                "isLoggedIn" to isLoggedIn
            )

            firestoreDb.collection("users").document(userEmail)
                .set(userPrefs)

            // Collect account data from Room
            repository.getAllAccounts().collect { accounts ->
                accounts.forEach { account ->
                    firestoreDb.collection("users").document(userEmail)
                        .collection("accounts").document(account.accountId.toString())
                        .set(account)
                }
            }

            // Collect transaction data from Room
            repository.getAllTransactions().collect { transactions ->
                transactions.forEach { transaction ->
                    firestoreDb.collection("users").document(userEmail)
                        .collection("accounts").document(transaction.accountId.toString())
                        .collection("transactions").document(transaction.transactionId.toString())
                        .set(transaction)
                }
            }
        }
    }

    fun fetchAllDataFromFireStore() {
        firestoreDb.collection("accounts") // 🔥 Your Firestore collection
            .get()
            .addOnSuccessListener { result ->
                val firestoreAccounts = result.documents.mapNotNull { it.toObject(Account::class.java) }
                mergeWithRoomDatabase(firestoreAccounts) // 🔥 Merge Firestore data with Room
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching data: ", exception)
            }
    }

    private fun mergeWithRoomDatabase(firestoreAccounts: List<Account>) {
        viewModelScope.launch {
            repository.getAllAccounts().collect { roomAccounts -> // Collect Flow here

                firestoreAccounts.forEach { firestoreAccount ->
                    val existingAccount = roomAccounts.find { it.accountId == firestoreAccount.accountId }

                    if (existingAccount == null) {
                        repository.insertAccount(firestoreAccount) // 🔥 Insert if not found
                    } else if (existingAccount != firestoreAccount) {
                        repository.updateAccount(firestoreAccount) // 🔥 Update if different
                    }
                }

                Log.d("Sync", "Firestore data synced with Room")
            }
        }
    }

    private fun getCurrentUserEmail(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }
}
