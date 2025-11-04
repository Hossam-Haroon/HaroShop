package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun FlashSaleComponent(discount : Int, imageUrl : String, onCLick:()->Unit){
    Card(
        modifier = Modifier
            .size(109.dp, 114.dp)
            .padding(top = 10.dp, start = 10.dp)
            .shadow(10.dp)
            .clip(RoundedCornerShape(9.dp))
            .clickable { onCLick() },
        colors = CardColors(
            Color.White,
            Color.Black,
            Color.Gray,
            GrayishWhite
        )
    ) {
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(99.dp, 103.dp)
                    .clip(RoundedCornerShape(9.dp)),
            )
            Box (
                modifier = Modifier
                    .size(99.dp, 103.dp)
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
                Text(
                    text = "-$discount%",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Normal,
                    fontSize = 13.sp,
                    color = Color.White
                )
            }
        }
    }
}