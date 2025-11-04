package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.e_commerceapp.presentation.theme.HeavyRed
import com.example.e_commerceapp.presentation.theme.LightRed2

@Composable
fun OptionsMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        DropdownMenuItem(
            text = { Text("Update") },
            onClick = {
                onUpdateClick()
            }
        )
        DropdownMenuItem(
            text = { Text("Delete", color = LightRed2) },
            onClick = {
                onDeleteClick()
            }
        )
    }
}
