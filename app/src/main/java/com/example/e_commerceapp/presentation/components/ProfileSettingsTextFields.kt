package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ProfileSettingsTextFields(
    value:String,
    onValueChange:(String)->Unit,
    hint : String
){
    Box(
        modifier = Modifier
            .size(335.dp, 50.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(LightBlue),
        contentAlignment = Alignment.CenterStart
    ){
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 17.sp,
                fontFamily = raleWay
            ),
            modifier = Modifier
                .padding(horizontal = 10.dp)
        )
        if (value.isEmpty()){
            Text(
                text = "Enter $hint",
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
            )
        }
    }
}