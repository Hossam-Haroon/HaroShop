package com.example.e_commerceapp.presentation.screens.introductionScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.components.ColorfulButton
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun IntroductionScreen(
    onNavigateToSignUp:()->Unit,
    onNavigateToLogIn:()->Unit
){
    Column (
        modifier  = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(200.dp))
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(134.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.ellipse),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = null,
                modifier = Modifier.size(81.4.dp,90.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "HaroShop",
            fontSize = 52.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.6f),
                    offset = Offset(5f,5f),
                    blurRadius = 8f
                )
            ),
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Beautiful eCommerce UI Kit ",
            fontSize = 19.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 70.dp, end = 70.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "for your online store",
            fontSize = 19.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(80.dp))
        ColorfulButton(
            "Let's get started",
            335.dp,
            61.dp,
            DarkBlue,
            textSize = 22.sp,
            textColor = BlueishWhite
        ){
            onNavigateToSignUp()
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier.clickable {
                onNavigateToLogIn()
            },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "I already have an account",
                fontSize = 15.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(end = 11.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.button),
                contentDescription = null,
                Modifier.size(30.dp)
            )
        }
    }
}
