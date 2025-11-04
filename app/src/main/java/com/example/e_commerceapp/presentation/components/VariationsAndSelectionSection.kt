package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.DarkWhite
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun VariationsAndSelectionSection(
    colorOption:Long,
    sizeOption:String,
    showBottomSheet:()->Unit
){
    Row {
        Text(
            text = "Variations",
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.width(7.dp))
        Box(
            modifier = Modifier
                .size(54.dp, 25.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(
                    if (colorOption == 0L) {
                        DarkWhite
                    } else {
                        Color(colorOption)
                    }
                )
        )
        Box(
            modifier = Modifier
                .size(54.dp, 25.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(DarkWhite),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = sizeOption,
                fontWeight = FontWeight.Medium,
                fontFamily = raleWay,
                fontSize = 14.sp
            )
        }
        Image(
            painter = painterResource(id = R.drawable.button_directing),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    showBottomSheet()
                }
        )
    }
}