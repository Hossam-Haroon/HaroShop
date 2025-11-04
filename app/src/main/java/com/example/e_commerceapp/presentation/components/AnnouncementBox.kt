package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.Grey3
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun AnnouncementBox(){
    Box (
        modifier = Modifier
            .size(335.dp, 70.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Grey3)
    ){
        Text(text = "Announcement"
            , fontSize = 14.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
        )
        Row {
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscingelit." +
                        " Maecenas hendrerit luctus libero ac vulputate.",
                fontFamily = raleWay,
                fontSize = 10.sp,
                modifier = Modifier.size(250.dp,29.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.button_directing),
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )

    }
}