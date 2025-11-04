package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.Grey1
import com.example.e_commerceapp.presentation.theme.Grey3
import com.example.e_commerceapp.presentation.theme.HeavyRed

@Composable
fun ProductScreenBottomBar(isLiked: Boolean, onClick: () -> Unit, onAddToCart: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(47.dp, 40.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(Grey1)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = if (isLiked) {
                    painterResource(id = R.drawable.liked_product)
                } else {
                    painterResource(id = R.drawable.not_liked_product)
                },
                contentDescription = null,
                modifier = Modifier
                    .size(26.dp, 24.dp),
                tint = if (isLiked) HeavyRed else Color.Unspecified
            )
        }
        ColorfulButton(
            text = "Add to cart",
            width = 128.dp,
            height = 40.dp,
            backgroundColor = Color.Black,
            textSize = 16.sp,
            textColor = BlueishWhite
        ) {
            onAddToCart()
        }
        ColorfulButton(
            text = "Buy now",
            width = 128.dp,
            height = 40.dp,
            backgroundColor = DarkBlue,
            textSize = 16.sp,
            textColor = BlueishWhite
        ) {

        }
    }
}