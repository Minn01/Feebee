package com.example.feebee_android_project_app

import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownButton(
    expanded: MutableState<Boolean>,
    selectedOption: MutableState<String>,
    dropDownOptions: List<String>,
    readOnly: Boolean = true,
    onValueChange: (String) -> Unit = {},
    onDropDownButtonClicked: (() -> Unit)? = null,
    dropDownLabel: @Composable () -> Unit = {},
    width: Dp,
    height: Dp,
    textStyle: TextStyle,
    modifier: Modifier
) {
    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it },
        modifier = modifier
    ) {
        TextField(
            value = selectedOption.value,
            readOnly = readOnly,
            minLines = 1,
            label = dropDownLabel,
            onValueChange = onValueChange,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true).size(width = width, height = height),
            textStyle = textStyle
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            dropDownOptions.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = { Text(option
                    ) },

                    onClick = {
                        selectedOption.value = dropDownOptions[index]
                        expanded.value = false
                        if (onDropDownButtonClicked != null) {
                            onDropDownButtonClicked()
                        }
                    }
                )
            }
        }
    }
}
