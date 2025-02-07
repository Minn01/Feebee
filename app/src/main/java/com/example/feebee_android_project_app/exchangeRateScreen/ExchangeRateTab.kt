package com.example.feebee_android_project_app.exchangeRateScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feebee_android_project_app.R

@Composable
fun ExchangeRateTab(
    countryCode: String,
    conversionRate: Double,
    modifier: Modifier
) {
    ElevatedCard(
        onClick = {},
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = countryCode,
                    fontSize = 20.sp,
                    modifier = Modifier.wrapContentWidth()
                )

                VerticalDivider(modifier = Modifier.padding(start = 16.dp))
            }

            Text(
                text = conversionRate.toString(),
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 16.dp)
            )

            Spacer(modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.exchange_rate_icon_dark),
                    contentDescription = null, // status_icon
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    "1.00",
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}
