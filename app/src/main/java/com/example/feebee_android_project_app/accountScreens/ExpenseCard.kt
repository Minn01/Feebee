package com.example.feebee_android_project_app.accountScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feebee_android_project_app.data.Transaction
import java.time.temporal.TemporalAmount

@Composable
fun ExpenseCard(
    transaction: Transaction,
    onTransactionClicked: () -> Unit,
    modifier: Modifier
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(60.dp)
            .clickable { onTransactionClicked() }
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(start = 20.dp, end = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Info, contentDescription = null)
            Text(transaction.transactionTitle, modifier = Modifier.padding(start = 16.dp))
            Spacer(Modifier.weight(1f))
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
            Text("${transaction.transactionAmount}$")
        }
    }
}
