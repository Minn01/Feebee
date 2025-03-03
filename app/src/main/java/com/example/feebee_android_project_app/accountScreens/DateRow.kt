package com.example.feebee_android_project_app.accountScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feebee_android_project_app.DropDownButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRow(
    rowText: String,
    buttonExpanded: MutableState<Boolean>,
    selectedOption: MutableState<String>,
    buttonOptions: List<String>,
    onDropDownButtonClicked: (() -> Unit)? = null,
    appTheme: State<String>,
    modifier: Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rowText,
            fontSize = 20.sp
        )

        DropDownButton(
            expanded = buttonExpanded,
            selectedOption = selectedOption,
            dropDownOptions = buttonOptions,
            width = 150.dp,
            height = 50.dp,
            textStyle = TextStyle(fontSize = 16.sp),
            onDropDownButtonClicked = onDropDownButtonClicked,
            modifier = Modifier
        )

    }
}
