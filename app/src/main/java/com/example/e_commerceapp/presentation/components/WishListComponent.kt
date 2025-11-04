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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.LightRed2
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun WishListComponent(
    imageUrl: String,
    product: Product,
    onCLick:()->Unit,
    onNavigateClick:()->Unit,
    onDelete:()->Unit
) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 5.dp).clickable {
            onNavigateClick()
        }
    ){
        Card(
            shape = RoundedCornerShape(9.dp),
            modifier = Modifier
                .size(129.dp, 120.dp),
            colors = CardColors(
                Color.White,
                Color.Black,
                Color.Gray,
                GrayishWhite
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.18.dp, 100.64.dp)
                        .clip(RoundedCornerShape(9.dp))
                )
                Box(
                    modifier = Modifier
                        .size(120.18.dp, 100.64.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 10.dp, start = 10.dp)
                            .size(35.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = null,
                            modifier = Modifier.size(15.dp,15.05.dp).
                            clickable { onDelete() },
                            tint = LightRed2
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = product.description,
                fontFamily = raleWay,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .fillMaxWidth()
            )
            Text(
                text = "$${product.productPrice}",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ){
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Box (
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightBlue)
                    ){
                        Text(
                            text = product.productType,
                            fontFamily = raleWay,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Box (
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightBlue)
                    ){
                        Text(
                            text = "remaining: ${product.productAmount}",
                            fontFamily = raleWay,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(6.dp)
                        )
                    }
                }
                Icon(
                    painter = painterResource(id = R.drawable.add_to_cart),
                    contentDescription = null,
                    modifier = Modifier
                        .size(29.dp, 25.dp)
                        .clickable {
                            onCLick()
                        },
                    tint = DarkBlue
                )
            }
        }
    }
}