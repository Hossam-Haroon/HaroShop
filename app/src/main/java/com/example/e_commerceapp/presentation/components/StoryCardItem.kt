package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.GrayishWhite

@Composable
fun StoryCardItem(image: String,onNavigate:()->Unit) {
    Card(
        modifier = Modifier
            .size(104.dp, 175.dp)
            .clickable { onNavigate() },
        shape = RoundedCornerShape(9.dp),
        elevation = CardDefaults.cardElevation(9.dp),
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
            AsyncImage(model = image, contentDescription = null, modifier = Modifier.fillMaxSize())
            Image(
                painter = painterResource(
                    id = R.drawable.play_story_image
                ),
                contentDescription = null,
                modifier = Modifier.size(29.dp)
            )
        }

    }
}

