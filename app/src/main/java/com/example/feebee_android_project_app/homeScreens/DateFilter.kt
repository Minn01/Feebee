package com.example.feebee_android_project_app.homeScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.feebee_android_project_app.accountScreens.DateRow
import com.example.feebee_android_project_app.data.DateData

@Composable
fun DateFilter(
    yearSelected: MutableState<String>,
    monthSelected: MutableState<String>,
    modifier: Modifier,
    onFilterChanged: () -> Unit
) {
    Column(modifier = modifier) {
        DateRow(
            rowText = "Year:",
            buttonExpanded = rememberSaveable { mutableStateOf(false) },
            selectedOption = yearSelected,
            buttonOptions = DateData.yearOptions,
            onDropDownButtonClicked = onFilterChanged,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        DateRow(
            rowText = "Month:",
            buttonExpanded = rememberSaveable { mutableStateOf(false) },
            selectedOption = monthSelected,
            buttonOptions = DateData.monthOptions,
            onDropDownButtonClicked = onFilterChanged,
            modifier = Modifier.padding(bottom = 20.dp)
        )
    }
}
