package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ToReceiveHeadLineComponent(
    userImage: String?,
    onVoucherIconClicked:()->Unit
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Row {
            Card (
                modifier = Modifier.size(50.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = CircleShape,
                colors = CardColors(
                    Color.White,
                    Color.Black,
                    Color.Black,
                    Color.White
                )
            ){
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    AsyncImage(
                        model = userImage ?: R.drawable.profile_picture,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).clip(CircleShape)
                    )
                }
            }
            Column (
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = "To Receive",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "My Orders",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }

        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1F)
        ) {
            Image(painter = painterResource(
                id = R.drawable.vouchers
            ),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        onVoucherIconClicked()
                    }
            )
            Image(painter = painterResource(
                id = R.drawable.top_menu
            ),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {

                    }
            )
            Image(painter = painterResource(
                id = R.drawable.settings
            ),
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {

                    }
            )
        }
    }
}