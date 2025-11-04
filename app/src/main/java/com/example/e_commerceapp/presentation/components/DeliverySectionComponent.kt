package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun DeliverySectionComponent(typeOfDelivery: String, numOfDays: String, price: String) {
    Box(
        modifier = Modifier
            .size(335.dp, 40.dp)
            .border(2.dp, DarkBlue, RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Text(
                    text = typeOfDelivery,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Box(
                    modifier = Modifier
                        .size(72.dp, 26.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            BlueishWhite
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$numOfDays days",
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = DarkBlue
                    )
                }
            }
            Text(
                text = "$$price",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}