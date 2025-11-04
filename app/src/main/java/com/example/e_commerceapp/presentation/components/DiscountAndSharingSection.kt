package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.presentation.theme.LightRed
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun DiscountAndSharingSection(
    product: Product,
    priceAfterDiscount: Float
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (product.discount == 0f) {
            Text(
                text = "$${product.productPrice.toFloat()}",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
        } else {
            Column {
                Text(
                    text = "$${priceAfterDiscount}",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
                Row(
                    modifier = Modifier.width(95.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${product.productPrice.toFloat()}",
                        fontFamily = raleWay,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp,
                        color = LightRed
                    )
                    Box(
                        modifier = Modifier.size(39.dp, 18.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(
                                id = R.drawable.rectangle_discount
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            text = "-${product.discount}%",
                            fontFamily = raleWay,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.share),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    // share bottom sheet
                }
        )
    }
}