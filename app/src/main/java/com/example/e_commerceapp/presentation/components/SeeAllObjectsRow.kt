package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun SeeAllObjectsRow(objectName:String, onCLick:()->Unit){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = objectName,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = raleWay
        )
        Row(modifier = Modifier
            .size(93.dp,30.dp)
            .clickable {
            },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "See All",
                fontSize = 15.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.button_directing),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
                    .clickable { onCLick() }
            )
        }
    }
}