package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite

@Composable
fun NavigateToProductFromStoryScreenDialog(
    imageUrl:String,
    onNavigate:()->Unit
){
    Card(
        modifier = Modifier
            .size(147.dp, 200.dp),
        shape = RoundedCornerShape(13.dp)
    ){
        Column(modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
        ) {
            AsyncImage(model = imageUrl, contentDescription = null,
                modifier = Modifier
                    .size(133.22.dp, 138.85.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(3.dp))
            ColorfulButton(
                text = "Shop",
                width = 133.dp,
                height = 40.dp,
                backgroundColor = DarkBlue,
                textSize = 16.sp,
                textColor = GrayishWhite
            ) {
                onNavigate()
            }

        }

    }
}