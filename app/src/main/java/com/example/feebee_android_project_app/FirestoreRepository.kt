package com.example.feebee_android_project_app

import android.util.Log
import com.example.feebee_android_project_app.data.Account
import com.example.feebee_android_project_app.data.AppRepository
import com.example.feebee_android_project_app.data.DataStoreManager
import com.example.feebee_android_project_app.data.FirestoreAccount
import com.example.feebee_android_project_app.data.FirestoreTransaction
import com.example.feebee_android_project_app.data.Transaction
import com.example.feebee_android_project_app.data.UserDetails
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreRepository @Inject constructor(
    private val firestoreDb: FirebaseFirestore,
    private val dataStoreManager: DataStoreManager,
    private val repository: AppRepository
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun fetchAllDataFromFireStore(userEmail: String, onComplete: () -> Unit) {
        val userDocRef = firestoreDb.collection("users").document(userEmail)

        // 🔥 Step 1: Fetch User Preferences
        userDocRef.get().addOnSuccessListener { document ->
            document?.let {
                val userPrefs = document.data ?: return@let
                coroutineScope.launch {
                    dataStoreManager.saveToUserDataStore(
                        UserDetails(
                            email = userPrefs["email"] as? String ?: "",
                            userName = userPrefs["userName"] as? String ?: ""
                        )
                    )
                    dataStoreManager.saveBasedCurrency(userPrefs["baseCurrency"] as? String ?: "")
                    dataStoreManager.saveAppTheme(userPrefs["appThemeMode"] as? String ?: "")
                    dataStoreManager.setLoginStatus(userPrefs["isLoggedIn"] as? Boolean ?: false)
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("Firestore", "Error fetching user preferences", exception)
        }

        // 🔥 Step 2: Fetch All Accounts
        userDocRef.collection("accounts").get().addOnSuccessListener { accountResult ->
            val firestoreAccounts = accountResult.documents.mapNotNull { doc ->
                doc.toObject(FirestoreAccount::class.java)?.toAccount() // Convert FirestoreAccount to Account
            }

            // 🔥 Merge Firestore Accounts with Room
            mergeAccountsWithRoom(firestoreAccounts)

            // 🔥 Step 3: Fetch All Transactions in One Batch
            fetchAllTransactions(userEmail, firestoreAccounts, onComplete)
        }.addOnFailureListener { exception ->
            Log.e("Firestore", "Error fetching accounts", exception)
        }
    }

    // 🔥 Fetch All Transactions at Once
    private fun fetchAllTransactions(userEmail: String, accounts: List<Account>, onComplete: () -> Unit) {
        val allTransactionFetches = mutableListOf<Task<QuerySnapshot>>()

        accounts.forEach { account ->
            val transactionFetch = firestoreDb.collection("users").document(userEmail)
                .collection("accounts").document(account.accountId.toString())
                .collection("transactions").get()
            allTransactionFetches.add(transactionFetch)
        }

        // 🔥 Execute all fetches at once
        Tasks.whenAllSuccess<QuerySnapshot>(allTransactionFetches)
            .addOnSuccessListener { results ->
                val allTransactions = results.flatMap { snapshot ->
                    snapshot.documents.mapNotNull { doc ->
                        doc.toObject(FirestoreTransaction::class.java)?.toTransaction()
                    }
                }

                // 🔥 Merge Transactions with Room
                mergeTransactionsWithRoom(allTransactions)
                onComplete()
            }
            .addOnFailureListener { exception ->
                Log.e("Firestore", "Error fetching transactions", exception)
            }
    }

    // 🔥 Merge Firestore Accounts into Room
    private fun mergeAccountsWithRoom(firestoreAccounts: List<Account>) {
        coroutineScope.launch {
            val roomAccounts = repository.getAllAccounts().first() // Fetch once instead of collecting

            firestoreAccounts.forEach { firestoreAccount ->
                val existingAccount = roomAccounts.find { it.accountId == firestoreAccount.accountId }

                if (existingAccount == null) {
                    repository.insertAccount(firestoreAccount) // 🔥 Insert if not found
                } else if (existingAccount != firestoreAccount) {
                    repository.updateAccount(firestoreAccount) // 🔥 Update if different
                }
            }
            Log.d("Sync", "Firestore accounts synced with Room")
        }
    }

    // 🔥 Merge Firestore Transactions into Room
    private fun mergeTransactionsWithRoom(firestoreTransactions: List<Transaction>) {
        coroutineScope.launch {
            val roomTransactions = repository.getAllTransactions().first() // Fetch once instead of collecting

            firestoreTransactions.forEach { firestoreTransaction ->
                val existingTransaction = roomTransactions.find { it.transactionId == firestoreTransaction.transactionId }

                if (existingTransaction == null) {
                    repository.insertTransaction(firestoreTransaction) // 🔥 Insert if not found
                } else if (existingTransaction != firestoreTransaction) {
                    repository.updateTransaction(firestoreTransaction) // 🔥 Update if different
                }
            }

            Log.d("Sync", "Firestore transactions synced with Room")
        }
    }
}
