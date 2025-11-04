package com.example.e_commerceapp.presentation.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.e_commerceapp.presentation.theme.DarkBlue

fun Modifier.customCheckModifierBordersForSizes(option: String, currentValue: String): Modifier {
    return if (option == currentValue) {
        this.border(1.dp, DarkBlue, RoundedCornerShape(4.dp))
    } else {
        this.clip(shape = RoundedCornerShape(4.dp))
    }
}
fun Modifier.customCheckModifierBordersForOptionColors(option: Long, currentValue: Long): Modifier{
    return if (option == currentValue) {
        this.border(width = 1.dp, color = DarkBlue, shape = RoundedCornerShape(4.dp))
    } else {
        this.clip(RoundedCornerShape(4.dp))
    }
}

