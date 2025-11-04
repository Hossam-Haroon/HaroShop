package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightRed
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun FlashSaleScreenProductComponent(
    product: Product,
    cardWidth: Dp,
    cardHeight: Dp,
    imageWidth: Dp,
    imageHeight: Dp,
    onClickAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(end = 4.dp)
            .width(140.dp)
            .wrapContentHeight()
            .clickable { onClickAction() },
    ) {
        Card(
            shape = RoundedCornerShape(9.dp),
            modifier = Modifier
                .size(cardWidth, cardHeight),
            colors = CardColors(
                Color.White,
                Color.Black,
                Color.Gray,
                GrayishWhite
            ),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(imageWidth, imageHeight)
                    .clip(RoundedCornerShape(9.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = product.productImage,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(9.dp))
                )
                Box (
                    modifier = Modifier
                        .size(imageWidth, imageHeight)
                        .clip(RoundedCornerShape(9.dp)),
                    contentAlignment = Alignment.TopEnd
                ){
                    Image(
                        painter = painterResource(
                            id = R.drawable.rectangle_discount
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(39.dp,18.dp)
                    )
                    Column(modifier = Modifier.size(39.dp,18.dp)) {
                        Text(
                            text = "-${product.discount}%",
                            fontFamily = raleWay,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = product.description,
            fontSize = 12.sp,
            fontFamily = raleWay,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.height(3.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$ ${product.productPrice * ((100 - product.discount) / 100)}",
                fontSize = 17.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.width(9.dp))
            Text(
                text = "$ ${product.productPrice}",
                fontSize = 14.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                color = LightRed,
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}