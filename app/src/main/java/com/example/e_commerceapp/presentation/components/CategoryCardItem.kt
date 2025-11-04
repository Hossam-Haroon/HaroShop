package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.Grey1
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun CategoryCardItem(
    sampleImagesUrl: List<String>,
    categoryName: String,
    productNumbers: Int,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(start = 3.dp, bottom = 3.dp)
            .size(165.dp, 192.dp)
            .clickable { onClick(categoryName) },
        shape = RoundedCornerShape(9.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardColors(
            Color.White,
            Color.Black,
            Color.Gray,
            GrayishWhite
        )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            items(sampleImagesUrl) { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(75.dp)
                        .padding(horizontal = 3.dp)
                        .padding(top = 5.dp)
                        .clip(RoundedCornerShape(5.dp))
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryName,
                fontSize = 17.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .size(38.dp, 20.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Grey1),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$productNumbers",
                    fontSize = 12.sp,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryCardItemPreview() {

}