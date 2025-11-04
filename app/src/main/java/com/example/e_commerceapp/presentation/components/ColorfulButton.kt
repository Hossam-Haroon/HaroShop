package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ColorfulButton(
    text:String,
    width:Dp,
    height:Dp,
    backgroundColor:Color,
    textSize:TextUnit,
    textColor: Color,
    clickable:()->Unit
){
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(width,height).clip(RoundedCornerShape(20.dp))
            .background(color = backgroundColor)
            .clickable {
                clickable()
            }

    ){
        Text(
            text = text,
            fontSize = textSize ,
            fontFamily = raleWay,
            fontWeight = FontWeight.Light,
            color = textColor
        )
    }

}