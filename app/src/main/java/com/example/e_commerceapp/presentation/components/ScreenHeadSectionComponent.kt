package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ScreenHeadSectionComponent(
    userImageUrl:String?,
    screenName:String,
    onVoucherIconClicked:()->Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1F)
        ) {
            ImageWithPortraitComponent(
                portraitSize = 43.dp, imageSize = 40.dp,
                userImageUrl
            ) {
            }
            Text(
                text = screenName,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = raleWay
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
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