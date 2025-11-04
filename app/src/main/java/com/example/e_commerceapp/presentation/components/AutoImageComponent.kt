package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.presentation.theme.DarkOrange
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.uiModels.FlashSaleSlideItem

@Composable
fun AutoImageComponent(
    flashSaleSlideItem: FlashSaleSlideItem,
    modifier: Modifier = Modifier
){
    Box (
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(9.dp))
            .background(DarkOrange),
    ){
        Image(
            painter = painterResource(id =  flashSaleSlideItem.backgroundImage),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row (modifier = Modifier.padding(start = 10.dp).fillMaxSize()){
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Big Sale",
                    fontFamily = raleWay,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Up to 50%",
                    fontWeight = FontWeight.Bold,
                    fontFamily = raleWay,
                    fontSize = 12.sp,
                    color = White
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Happening",
                    fontFamily = raleWay,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
                Text(
                    text = "Now",
                    fontFamily = raleWay,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
                Spacer(modifier = Modifier.height(5.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(id = flashSaleSlideItem.productImage),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f),
                contentScale = ContentScale.Fit
            )
        }
    }
}