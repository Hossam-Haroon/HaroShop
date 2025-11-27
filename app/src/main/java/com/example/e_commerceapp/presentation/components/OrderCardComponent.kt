package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.Grey1
import com.example.e_commerceapp.presentation.theme.Grey2
import com.example.e_commerceapp.presentation.theme.LightRed2
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun OrderCardComponent(
    order: Order,
    onTrackClicked:()->Unit,
    onReviewClicked:()->Unit,
    onTicketCLicked:()->Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card (
            modifier = Modifier.size(89.dp),
            shape = RoundedCornerShape(9.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardColors(
                Color.White,
                Color.Black,
                Color.White,
                Color.Black
            )
        ){
            Box (
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                when(order.items.size){
                    1 -> {
                        AsyncImage(
                            model = order.items[0].imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(9.dp))
                        )
                    }
                    2 -> {
                        Row (
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                            ){
                            order.items.forEach { item ->
                                AsyncImage(
                                    model = item.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .width(39.17.dp)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(9.dp))
                                )
                            }
                        }
                    }
                    3 -> {
                        Column(
                            Modifier.fillMaxSize()
                        ) {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ){
                                AsyncImage(
                                    model = order.items[0].imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(9.dp))
                                )
                                AsyncImage(
                                    model = order.items[1].imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(9.dp))
                                )
                            }
                            Spacer(modifier = Modifier.height(1.dp))
                            AsyncImage(
                                model = order.items[2].imageUrl,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(9.dp))
                            )
                        }
                    }
                    else -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.size(79.dp)
                        ) {
                            items(order.items){orderItem ->
                                AsyncImage(
                                    model = orderItem.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(39.17.dp)
                                        .clip(
                                            RoundedCornerShape(9.dp)
                                        )
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(3.dp))
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "#${order.orderNumber}",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Box(
                    modifier = Modifier
                        .size(61.dp, 22.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Grey1),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${order.items.size} items",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = raleWay
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "${order.deliveryType} Delivery",
                fontFamily = raleWay,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                when (order.status) {
                    "UnDelivered" -> {
                        Row {
                            Text(
                                text = order.status,
                                fontFamily = raleWay,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(LightRed2),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.close),
                                    contentDescription = null,
                                    modifier = Modifier.size(8.07.dp,8.61.dp))
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(86.dp, 30.dp)
                                .border(2.dp, LightRed2, RoundedCornerShape(9.dp))
                                .background(Color.White)
                                .clickable {
                                    onTicketCLicked()
                                },
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Ticket",
                                fontFamily = raleWay,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = LightRed2
                            )
                        }
                    }
                    "Delivered" -> {
                        Row {
                            Text(
                                text = order.status,
                                fontFamily = raleWay,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(DarkBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.icon_feather_check),
                                    contentDescription = null,
                                    modifier = Modifier.size(8.07.dp,8.61.dp))
                            }
                        }
                        Box(
                            modifier = Modifier
                                .size(86.dp, 30.dp)
                                .border(2.dp, DarkBlue, RoundedCornerShape(9.dp))
                                .background(Color.White)
                                .clickable {
                                    onReviewClicked()
                                },
                            contentAlignment = Alignment.Center
                        ){
                            Text(
                                text = "Review",
                                fontFamily = raleWay,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = DarkBlue
                            )
                        }
                    }
                    else -> {
                        Text(
                            text = order.status,
                            fontFamily = raleWay,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        ColorfulButton(
                            text = "Track",
                            width = 86.dp,
                            height = 30.dp,
                            backgroundColor = DarkBlue,
                            textSize = 16.sp,
                            textColor = GrayishWhite
                        ) {
                            onTrackClicked()
                        }
                    }
                }
            }
        }
    }
}