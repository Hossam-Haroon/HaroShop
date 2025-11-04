package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun SettingUserReviewComponent(rate:Int,onRateCLick:(Int)->Unit,onWriteReviewClick:()->Unit){
    Column {
        Text(
            text = "Rate this product",
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Tell others about your opinion",
            fontFamily = raleWay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
        SettingRateComponent(rate = rate) {index ->
            onRateCLick(index)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Write a review",
            fontFamily = raleWay,
            fontSize = 14.sp,
            color = DarkBlue,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.clickable {
                onWriteReviewClick()
            }
        )
    }
}