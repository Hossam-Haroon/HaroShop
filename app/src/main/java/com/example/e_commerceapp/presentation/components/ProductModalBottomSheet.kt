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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkWhite
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.utils.customCheckModifierBordersForOptionColors
import com.example.e_commerceapp.presentation.utils.customCheckModifierBordersForSizes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductModalBottomSheet(
    imageUrl: String,
    priceAfterDiscount: Float,
    product: Product,
    colorOption: Long,
    sizeOption: String,
    quantity: Int,
    setColorOption: (Long) -> Unit,
    setSizeOption: (String) -> Unit,
    onDismissRequest: () -> Unit,
    decreaseQuantityByOne: () -> Unit,
    increaseQuantityByOne: () -> Unit
) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column (
            modifier = Modifier.padding(horizontal = 5.dp)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(141.dp)
                    .background(BlueishWhite)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.size(75.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "$${priceAfterDiscount}",
                        fontWeight = FontWeight.Bold,
                        fontFamily = raleWay,
                        fontSize = 26.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Color options",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                for (color in product.color) {
                    Box(modifier = Modifier
                        .size(50.dp, 25.dp)
                        .background(Color(color))
                        .customCheckModifierBordersForOptionColors(
                            colorOption,
                            color
                        )
                        .clickable {
                            setColorOption(color)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Size",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ){
                for (size in product.size) {
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        modifier = Modifier
                            .size(50.dp, 25.dp)
                            .background(
                                if (sizeOption == size) {
                                    LightBlue
                                } else {
                                    DarkWhite
                                }
                            )
                            .customCheckModifierBordersForSizes(sizeOption, size)
                            .clickable {
                                setSizeOption(size)
                            },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = size,
                            fontFamily = raleWay,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quantity",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                ProductAmountControllerSection(
                    quantity,
                    decreaseQuantityByOne,
                    increaseQuantityByOne
                )
            }
        }
    }
}