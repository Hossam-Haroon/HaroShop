package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.Grey2
import com.example.e_commerceapp.presentation.theme.raleWay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingReviewBottomShowComponent(
    textValue:String,
    rate : Int,
    reviewer:User,
    onRateCLick:(Int)->Unit,
    onDismissRequest:()->Unit,
    onValueChange:(String)->Unit,
    onShareButtonClick:()->Unit
){
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                AsyncImage(
                    model = reviewer.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = reviewer.userName,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            SettingRateComponent(rate = rate) {index ->
                onRateCLick(index)
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = textValue, onValueChange = onValueChange,
                placeholder = {
                    Text(
                        text = "write a review (optional)",
                        color = Grey2,
                        fontSize = 13.83.sp,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Light
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            ColorfulButton(
                text = "Share",
                width = 120.dp,
                height = 50.dp,
                backgroundColor = DarkBlue,
                textSize = 19.sp,
                textColor = GrayishWhite
            ) {
                onShareButtonClick()
                // share
                // close the bottom sheet
            }
        }
    }
}