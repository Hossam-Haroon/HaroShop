package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun MyActivityOrdersStatsCircleShapeComponent(enteredStat: String, typeOfOrderState: String) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Card(
            modifier = Modifier.size(80.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardColors(
                Color.White,
                Color.Black,
                Color.White,
                Color.Black
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(50.dp).clip(CircleShape).background(DarkBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = enteredStat,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 21.sp,
                        color = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = typeOfOrderState,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}