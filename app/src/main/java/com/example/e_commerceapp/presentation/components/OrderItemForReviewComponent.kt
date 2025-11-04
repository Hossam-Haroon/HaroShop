package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightGray
import com.example.e_commerceapp.presentation.theme.raleWay
import com.google.firebase.Timestamp

@Composable
fun OrderItemForReviewComponent(
    orderItem: OrderItem,
    orderDate:Timestamp,
    doesProductHaveReview:Boolean,
    onReviewClicked:()->Unit
    ) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Card(
            modifier = Modifier.size(129.dp, 109.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(9.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = orderItem.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(121.18.dp, 101.64.dp)
                        .clip(RoundedCornerShape(9.dp))
                )
            }
        }
        Column (
            modifier = Modifier.padding(start = 3.dp)
        ){
            Text(
                text = orderItem.title,
                fontSize = 12.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .size(103.dp, 30.dp)
                        .clip(RoundedCornerShape(9.dp))
                        .background(
                            LightGray
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = orderDate.toDate().toString(),
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        color = Color.Black,
                        modifier = Modifier.offset(x = (5).dp)
                    )
                }
                if (doesProductHaveReview){
                    Box(
                        modifier = Modifier
                            .size(103.dp, 30.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .background(
                                DarkBlue
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Reviewed",
                            fontFamily = raleWay,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = GrayishWhite
                        )
                    }
                }else{
                    Box(
                        modifier = Modifier
                            .size(103.dp, 30.dp)
                            .border(2.dp, DarkBlue, RoundedCornerShape(9.dp))
                            .background(
                                Color.White
                            ),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Review",
                            fontFamily = raleWay,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            color = DarkBlue,
                            modifier = Modifier.clickable {
                                onReviewClicked()
                            }
                        )
                    }
                }
            }
        }
    }
}