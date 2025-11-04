package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.Grey1
import com.example.e_commerceapp.presentation.theme.Grey2
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun CustomTextFieldWIthSlider(
    phoneNumber:String,
    selectedCountry:String,
    onValueChanged:(String)->Unit,
    onCountrySelected: (String)->Unit
){
    Row (
        modifier = Modifier
            .size(335.dp, 57.37.dp)
            .clip(RoundedCornerShape(59.29.dp))
            .background(Grey1)
            .border(color = Grey1, width = 1.dp),
        horizontalArrangement = Arrangement.Center
    ){
        CountryCodePicker(selectedCountry = selectedCountry) {
            onCountrySelected(it)
        }
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = onValueChanged,
            textStyle = TextStyle(color = Color.Black),
            placeholder = {
                Text(
                    text = "Your Number",
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