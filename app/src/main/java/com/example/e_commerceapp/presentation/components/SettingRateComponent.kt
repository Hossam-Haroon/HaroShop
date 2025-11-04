package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.e_commerceapp.R

@Composable
fun SettingRateComponent(rate:Int,onRateCLick:(Int)->Unit){
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        for (i in 0 until 5) {
            val painter = if (i < rate) {
                painterResource(id = R.drawable.selected_star)
            } else {
                painterResource(id = R.drawable.unselected_star)
            }
            Image(
                painter = painter,
                contentDescription = "Star ${i + 1}",
                modifier = Modifier
                    .size(30.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current
                    ) {
                        onRateCLick(i + 1)
                    }
            )
        }
    }
}