package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import com.example.e_commerceapp.domain.model.Discount
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.DarkWhite
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun DiscountValuesRowSectionComponent(
    discountValue: String,
    discountValues: List<Discount>,
    onAllSelect: () -> Unit,
    onDiscountValueSelect: (Discount) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(GrayishWhite)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            item {
                if (discountValue == "All") {
                    Box(
                        modifier = Modifier.size(65.dp, 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.flash_sale_clickable_shape
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Unspecified
                        )
                        Text(
                            text = "All",
                            fontFamily = raleWay,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = DarkBlue
                        )
                    }
                } else {
                    Text(
                        text = "All",
                        fontFamily = raleWay,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onAllSelect()
                        }
                    )
                }
            }
            items(discountValues) { discount ->
                if (discount.discountValue == discountValue) {
                    Box(
                        modifier = Modifier.size(65.dp, 60.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.flash_sale_clickable_shape
                            ),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Unspecified
                        )
                        Text(
                            text = "${discount.discountValue}%",
                            fontFamily = raleWay,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = DarkBlue
                        )
                    }
                } else {
                    Text(
                        text = "${discount.discountValue}%",
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        modifier = Modifier.clickable {
                            onDiscountValueSelect(discount)
                        }
                    )
                }
            }
        }
    }
}