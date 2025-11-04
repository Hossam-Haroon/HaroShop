package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.example.e_commerceapp.R

@Composable
fun ImageWithPortraitComponent(
    portraitSize: Dp,
    imageSize: Dp,
    imageUrl: String?,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(portraitSize)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ellipse),
            modifier = Modifier.size(portraitSize),
            contentDescription = null
        )
        AsyncImage(
            model = imageUrl ?: R.drawable.profile_picture,
            contentDescription = null,
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
        )
    }
}



