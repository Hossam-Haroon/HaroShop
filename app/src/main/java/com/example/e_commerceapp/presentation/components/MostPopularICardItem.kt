package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun MostPopularICardItem(
    productImageUrl:String,
    numberOfLikes:Long,
    onCLick : ()->Unit
){
    Card(
        onClick = { onCLick() },
        shape = RoundedCornerShape(9.dp),
        modifier = Modifier
            .padding(end = 6.dp)
            .size(104.dp, 140.dp),
        colors = CardColors(Color.White, Color.Black, GrayishWhite, Color.Gray),
        elevation = CardDefaults.cardElevation(8.dp)
        ) {
        Box (
            modifier = Modifier
                .padding(top = 3.dp)
                .height(103.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            AsyncImage(
                model = productImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(93.41.dp, 103.dp)
                    .clip(RoundedCornerShape(9.dp))
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .size(50.50.dp, 20.dp)
                .padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "$numberOfLikes",
                fontSize = 17.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(id = R.drawable.like_icon_popular),
                contentDescription = null,
                modifier = Modifier.size(15.dp,15.dp),
                tint = DarkBlue
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun MostPopularICardItemPreview(){

}