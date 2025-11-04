package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun IntroductionCard(
    firstDot:Int,
    secondDot:Int,
    imageId:Int,
    headLineText:String,
    text:String,
    buttonText:String,
    onNavigate: ()->Unit){
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(70.dp))
        Box(
            modifier = Modifier
                .size(370.dp, 700.dp)
                .clip(RoundedCornerShape(30.dp))
        ){
            Image(
                painter = painterResource(id = R.drawable.card_shape),
                modifier = Modifier.fillMaxSize(),
                contentDescription = null,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(35.dp))
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(322.dp,338.dp)
                        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))

                )
                Spacer(modifier = Modifier.height(35.dp))
                Text(
                    text = headLineText,
                    fontSize = 28.sp,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = text,
                    fontSize = 19.sp,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(start = 35.dp, end = 35.dp)
                )
                Spacer(modifier = Modifier.height(35.dp))
                ColorfulButton(
                    text = buttonText,
                    width = 201.dp,
                    height = 50.dp,
                    backgroundColor = DarkBlue,
                    textSize = 22.sp,
                    textColor = BlueishWhite
                ){
                    onNavigate()
                }
            }
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.width(100.dp)) {
            Image(
                painter = painterResource(id = firstDot),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Image(
                painter = painterResource(id = secondDot),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}