package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.feebee_android_project_app.data.RoomViewModel

@Composable
fun TransactionDetailScreen(
    navController: NavController,
    transactionId: Int,
    modifier: Modifier
) {
    val roomViewModel: RoomViewModel = hiltViewModel()
    roomViewModel.getTransactionFromId(transactionId)
    val transaction = roomViewModel.currentTransaction.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = transaction.value.transactionTitle,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(top = 16.dp, end = 16.dp, bottom = 16.dp, start = 16.dp)) {
                Text(
                    text = "Type: ${transaction.value.transactionType.replaceFirstChar { it.uppercase() }}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Amount: \$${transaction.value.transactionAmount}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Date: ${transaction.value.createdDate}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Details: ${transaction.value.transactionDetails.ifEmpty { "No details provided" }}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                )
            }
        }

        if (transaction.value.transactionImageUri.isNotEmpty()) {
            AsyncImage(
                model = transaction.value.transactionImageUri,
                contentDescription = "Transaction Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = "No image attached",
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back")
        }
    }
}