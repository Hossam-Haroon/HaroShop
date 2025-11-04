package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightRed
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun VoucherCardComponent(voucher: Voucher, onVoucherApplyClick: () -> Unit) {
    Column (
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .height(120.dp)
            .border(1.dp, DarkBlue, RoundedCornerShape(9.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Voucher",
                fontSize = 18.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                color = DarkBlue
            )
            Box(
                modifier = Modifier
                    .height(19.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(LightRed),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Valid until ${voucher.expiryDate}",
                    fontSize = 11.sp,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Image(
            painter = painterResource(id = R.drawable.blue_line),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.buy_icon),
                contentDescription = null,
                modifier = Modifier.size(13.74.dp, 14.47.dp),
                tint = DarkBlue
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = voucher.title,
                fontSize = 17.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = voucher.description,
                fontSize = 12.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.SemiBold
            )
            ColorfulButton(
                text = "Apply",
                width = 79.dp,
                height = 25.dp,
                backgroundColor = DarkBlue,
                textSize = 14.sp,
                textColor = GrayishWhite
            ) {
                onVoucherApplyClick()
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}