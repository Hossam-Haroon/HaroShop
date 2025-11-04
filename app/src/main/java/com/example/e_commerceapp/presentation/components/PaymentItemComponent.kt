package com.example.e_commerceapp.presentation.components

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun PaymentItemComponent(
    imageUrl: String,
    productAmount: String,
    description: String,
    price: String,
    onNavigateClick:()->Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigateClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Row(
            modifier = Modifier
                .weight(1f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(70.dp)
            ) {
                Card(
                    modifier = Modifier.size(60.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                            alignment = Alignment.Center,
                        )
                    }
                }
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(27.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(LightBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = productAmount,
                                    fontFamily = raleWay,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
            Text(
                text = description,
                fontFamily = raleWay,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier.weight(1f)
            )
        }
        Text(text = "$$price", fontFamily = raleWay, fontWeight = FontWeight.Bold, fontSize = 18.sp)
    }
}