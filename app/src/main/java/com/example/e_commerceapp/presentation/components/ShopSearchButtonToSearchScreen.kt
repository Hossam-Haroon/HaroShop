package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.Grey1
import com.example.e_commerceapp.presentation.theme.Grey2
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ShopSearchButtonToSearchScreen(
    onNavigateToSearchScreen:()->Unit
){
    Box (
        modifier = Modifier
            .size(248.dp, 50.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Grey1)
            .border(color = Grey1, width = 1.dp)
            .clickable { onNavigateToSearchScreen() },
        contentAlignment = Alignment.Center
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "Search",
                color = Grey2,
                fontSize = 13.83.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 10.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.camera_icon),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(20.dp)
                    .clickable {

                    },
                tint = DarkBlue
            )
        }
    }
}