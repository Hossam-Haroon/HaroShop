package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.Grey1
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ContactInformationSectionComponent(phoneNumber:String,email:String,onEditIconClick:()->Unit){
    Box (
        modifier = Modifier
            .size(335.dp, 95.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Grey1)
    ){
        Column(
            modifier = Modifier
                .padding(7.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Contact Information",
                fontSize = 14.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(3.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = email,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = phoneNumber,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.pen_button),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            onEditIconClick()
                        }
                )
            }
        }
    }
}