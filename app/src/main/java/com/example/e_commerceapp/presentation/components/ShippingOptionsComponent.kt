package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.example.e_commerceapp.presentation.theme.BlueishWhite2
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.DarkWhite2
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ShippingOptionsComponent(
    isSelected: Boolean,
    shippingOption:String,
    shippingDuration:String,
    shippingPrice:String,
    onClick:()->Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .size(335.dp, 40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) LightBlue else GrayishWhite)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row (
                modifier = Modifier.padding(start = 3.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(DarkBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(
                                id = R.drawable.icon_feather_check),
                                contentDescription = null,
                                modifier = Modifier.size(9.dp,6.5.dp)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(DarkWhite2)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = shippingOption,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Box (
                modifier = Modifier
                    .size(72.dp, 26.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(BlueishWhite2),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = shippingDuration,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
            }
        }
        Text(
            text = "$$shippingPrice",
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(end = 2.dp)
        )
    }
}