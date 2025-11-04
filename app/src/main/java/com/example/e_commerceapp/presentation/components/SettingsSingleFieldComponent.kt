package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun SettingsSingleFieldComponent(
    fieldName: String,
    onFieldArrowClick:()->Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = fieldName,
                fontFamily = raleWay,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                painter = painterResource(id = R.drawable.right_arrow),
                contentDescription = null,
                modifier = Modifier
                    .size(10.dp, 17.dp)
                    .clickable {
                        onFieldArrowClick()
                    },
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(14.dp))
        Spacer(modifier = Modifier
            .height(1.dp)
            .background(Color.Black)
        )
    }
}