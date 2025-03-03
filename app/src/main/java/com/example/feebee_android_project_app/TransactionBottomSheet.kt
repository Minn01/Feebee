package com.example.feebee_android_project_app

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.feebee_android_project_app.accountScreens.getDate
import com.example.feebee_android_project_app.data.Account
import com.example.feebee_android_project_app.data.DateData
import com.example.feebee_android_project_app.data.RoomViewModel
import com.example.feebee_android_project_app.data.Transaction
import okhttp3.internal.wait
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionBottomSheet(
    onDismiss: () -> Unit,
    onSave: (Transaction, Int) -> Unit
) {
    val roomViewModel: RoomViewModel = hiltViewModel()
    val context = LocalContext.current

    val typeDDExpanded = rememberSaveable { mutableStateOf(false) }
    val typeSelected = rememberSaveable { mutableStateOf("") }
    val transactionTypeOptions = listOf("income", "expense")

    val accountSelected = rememberSaveable { mutableStateOf("") }
    val accountDDExpanded = rememberSaveable { mutableStateOf(false) }

    roomViewModel.getAllAccounts()
    val accountsList = roomViewModel.accountList.collectAsState()

    val yearDDExpanded = rememberSaveable { mutableStateOf(false) }
    val monthDDExpanded = rememberSaveable { mutableStateOf(false) }
    val dayDDExpanded = rememberSaveable { mutableStateOf(false) }
    val selectedYear = rememberSaveable { mutableStateOf("") }
    val selectedMonth = rememberSaveable { mutableStateOf("") }
    val selectedDay = rememberSaveable { mutableStateOf("") }

    val transactionTitle = rememberSaveable { mutableStateOf("") }
    val transactionAmount = rememberSaveable { mutableStateOf("") }
    val transactionDetails = rememberSaveable { mutableStateOf("") }
    val transactionImageUri = rememberSaveable { mutableStateOf("") }
    val createdDate = rememberSaveable { mutableStateOf(LocalDate.now()) }

    val selectAccountId = roomViewModel.selectedAccountId.collectAsState()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // Photo Picker Launcher
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            transactionImageUri.value = uri.toString()
            Log.d("PhotoPicker", "Selected URI: $uri")
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        content = {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(600.dp)
                ) {
                    item {
                        Text(
                            text = "Add Transaction",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            "Select an account",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                        DropDownButton(
                            expanded = accountDDExpanded,
                            selectedOption = accountSelected,
                            dropDownOptions = accountsList.value.map { it.accountName },
                            width = 250.dp,
                            height = 56.dp,
                            textStyle = TextStyle(fontSize = 13.sp),
                            dropDownLabel = { Text("Select Account") },
                            onOptionsSelected = { accountName ->
                                roomViewModel.getAccountIdFromName(accountName)
                                Log.d("acc1", selectAccountId.value.toString())
                            },
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Select a date",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            DropDownButton(
                                expanded = dayDDExpanded,
                                selectedOption = selectedDay,
                                dropDownOptions = DateData.dayOptions,
                                width = 100.dp,
                                height = 56.dp,
                                textStyle = TextStyle(fontSize = 13.sp),
                                dropDownLabel = { Text("Day") },
                                modifier = Modifier
                            )

                            DropDownButton(
                                expanded = monthDDExpanded,
                                selectedOption = selectedMonth,
                                dropDownOptions = DateData.monthOptionsWithoutAll,
                                width = 150.dp,
                                height = 56.dp,
                                textStyle = TextStyle(fontSize = 13.sp),
                                dropDownLabel = { Text("Month") },
                                modifier = Modifier
                            )

                            DropDownButton(
                                expanded = yearDDExpanded,
                                selectedOption = selectedYear,
                                dropDownOptions = DateData.yearOptionsWithoutAll,
                                width = 120.dp,
                                height = 56.dp,
                                textStyle = TextStyle(fontSize = 13.sp),
                                dropDownLabel = { Text("Year") },
                                modifier = Modifier
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            "Select a date",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )

                        DropDownButton(
                            expanded = typeDDExpanded,
                            selectedOption = typeSelected,
                            dropDownOptions = transactionTypeOptions,
                            width = 200.dp,
                            height = 56.dp,
                            textStyle = TextStyle(fontSize = 16.sp),
                            dropDownLabel = { Text("Transaction Type") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = transactionTitle.value,
                            onValueChange = { transactionTitle.value = it },
                            label = { Text("Transaction Title") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = transactionAmount.value,
                            onValueChange = { transactionAmount.value = it },
                            label = { Text("Transaction Amount") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = transactionDetails.value,
                            onValueChange = { transactionDetails.value = it },
                            label = { Text("Transaction Details") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            maxLines = 5
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Select Image")
                        }

                        if (transactionImageUri.value.isNotEmpty()) {
                            AsyncImage(
                                model = transactionImageUri.value,
                                contentDescription = "Transaction Image",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                Button(
                    // TODO: date fix
                    onClick = {
                        if (selectedYear.value.isNotEmpty() &&
                            selectedMonth.value.isNotEmpty() &&
                            selectedDay.value.isNotEmpty()
                        ) {
                            createdDate.value = getDate(
                                context = context,
                                "${
                                    selectedDay.value.padStart(
                                        2,
                                        '0'
                                    )
                                }/${
                                    (DateData.monthOptionsWithoutAll.indexOf(selectedMonth.value) + 1).toString()
                                        .padStart(2, '0')
                                }/${selectedYear.value}"
                            )
                        }
                        val amount = transactionAmount.value.toDoubleOrNull() ?: 0.0
                        Log.d("accIdAdd", selectAccountId.value.toString())
                        val newTransaction = Transaction(
                            accountId = selectAccountId.value,
                            transactionTitle = transactionTitle.value,
                            transactionType = typeSelected.value,
                            transactionAmount = amount,
                            transactionDetails = transactionDetails.value,
                            transactionImageUri = transactionImageUri.value,
                            createdDate = createdDate.value
                        )
                        onSave(newTransaction, selectAccountId.value)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Transaction")
                }
            }
        }
    )
}
