package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.Grey1
import com.example.e_commerceapp.presentation.theme.Grey2
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun CustomGrayFieldForPassword(
    textValue:String,
    hintText:String,
    width : Dp,
    height : Dp,
    onValueChange: (String)->Unit
){
    Box (
        modifier = Modifier
            .size(width,height)
            .clip(RoundedCornerShape(16.dp))
            .background(Grey1)
            .border(color = Grey1, width = 1.dp)
    ){
        OutlinedTextField(
            value = textValue,
            onValueChange = onValueChange,
            textStyle = TextStyle(color = Color.Black),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            placeholder = {
                Text(
                    text = hintText,
                    color = Grey2,
                    fontSize = 13.83.sp,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Light
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}