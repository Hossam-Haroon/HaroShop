package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ItemsCountAndVoucherComponent(
    voucher: Voucher?,
    productsNumber: String,
    onVoucherClick: () -> Unit,
    onVoucherCancel: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Items",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp
            )
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(LightBlue),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = productsNumber,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
        }
        if (voucher == null) {
            Box(
                modifier = Modifier
                    .size(120.dp, 30.dp)
                    .border(1.dp, DarkBlue, RoundedCornerShape(11.dp))
                    .clickable {
                        onVoucherClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add Voucher",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = DarkBlue
                )
            }
        } else {
            Box(
                modifier = Modifier.size(190.dp,30.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(DarkBlue),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = voucher.title,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = GrayishWhite
                    )
                    Image(
                        painter = painterResource(id = R.drawable.cancel_icon),
                        contentDescription = null,
                        modifier = Modifier.size(11.5.dp).clickable {
                            onVoucherCancel()
                            // cancel the voucher and reset to normal value
                        }
                    )
                }
            }
        }
    }
}