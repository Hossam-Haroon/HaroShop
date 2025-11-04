package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ProductAmountControllerSection(
    quantity: Int,
    decreaseQuantityByOne: () -> Unit,
    increaseQuantityByOne: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painter = painterResource(id = R.drawable.less),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    if (quantity != 0) {
                        decreaseQuantityByOne()
                    }
                }
        )
        Spacer(modifier = Modifier.width(3.dp))
        Box(
            modifier = Modifier
                .size(62.dp, 37.dp)
                .background(LightBlue),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = quantity.toString(),
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 23.sp
            )
        }
        Spacer(modifier = Modifier.width(3.dp))
        Image(
            painter = painterResource(id = R.drawable.more),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    increaseQuantityByOne()
                }
        )
    }
}