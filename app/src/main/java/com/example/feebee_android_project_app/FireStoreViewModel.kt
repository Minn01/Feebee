package com.example.feebee_android_project_app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feebee_android_project_app.data.AppRepository
import com.example.feebee_android_project_app.data.DataStoreManager
import com.example.feebee_android_project_app.data.FirestoreAccount
import com.example.feebee_android_project_app.data.FirestoreTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FireStoreViewModel @Inject constructor(
    private val firestoreDb: FirebaseFirestore,
    private val repository: AppRepository,
    private val fireStoreRepository: FirestoreRepository,
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

            firestoreDb.collection("users").document(userEmail).set(userPrefs)

            // 🔥 Start a batch operation for Firestore
            val batch = firestoreDb.batch()

            // ✅ Fetch all accounts once
            val accounts = repository.getAllAccounts().first()
            accounts.forEach { account ->
                val firestoreAccount = FirestoreAccount(
                    accountId = account.accountId,
                    accountName = account.accountName,
                    accountBalance = account.accountBalance,
                    createdDate = account.createdDate.toString()
                )

                val accountRef = firestoreDb.collection("users").document(userEmail)
                    .collection("accounts").document(account.accountId.toString())

                batch.set(accountRef, firestoreAccount)
            }

            // ✅ Fetch all transactions once
            val transactions = repository.getAllTransactions().first()
            transactions.forEach { transaction ->
                val firestoreTransaction = FirestoreTransaction(
                    transactionId = transaction.transactionId,
                    accountId = transaction.accountId,
                    amount = transaction.transactionAmount,
                    details = transaction.transactionDetails,
                    createdDate = transaction.createdDate.toString()
                )

                val transactionRef = firestoreDb.collection("users").document(userEmail)
                    .collection("accounts").document(transaction.accountId.toString())
                    .collection("transactions").document(transaction.transactionId.toString())

                batch.set(transactionRef, firestoreTransaction)
            }

            // 🔥 Commit the batch write to Firestore
            batch.commit()
                .addOnSuccessListener {
                    Log.d("Firestore", "All data saved successfully!")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error saving data", e)
                }
        }
    }

    fun fetchAllDataFromFireStore() {
        val userEmail = getCurrentUserEmail() ?: return
        if (userEmail.isNullOrEmpty()) {
            Log.e("Firestore", "User not logged in, skipping fetch.")
            return
        }

        fireStoreRepository.fetchAllDataFromFireStore(userEmail) {
            Log.d("Firestore", "fetching complete")
        }
    }

    private fun getCurrentUserEmail(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }
}
