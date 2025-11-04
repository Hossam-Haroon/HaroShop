package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.Grey1
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun CartCheckOutBottomBar(
    productState: String,
    totalPrice: String,
    suitableColor: Color,
    cartIsEmpty: Boolean, onCLick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Grey1)
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(70.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Total",
                fontFamily = raleWay,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "$$totalPrice",
                fontFamily = raleWay,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp
            )
        }
        if (cartIsEmpty) {
            Box(
                modifier = Modifier
                    .size(128.dp, 40.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = productState,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .size(128.dp, 40.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(suitableColor)
                    .clickable { onCLick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = productState,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = GrayishWhite
                )

            }
        }

    }
}