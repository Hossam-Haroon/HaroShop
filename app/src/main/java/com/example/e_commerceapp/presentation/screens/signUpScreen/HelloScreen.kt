package com.example.e_commerceapp.presentation.screens.signUpScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.components.IntroductionCard

@Composable
fun HelloScreen(onNavigateToReadyScreen: ()-> Unit){
    Image(
        painter = painterResource(id = R.drawable.bubbles_hello_screen),
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.TopCenter,
        contentDescription = null
    )
    IntroductionCard(
        R.drawable.blue_dot,
        R.drawable.gray_dot,
        R.drawable.hello_screen_image,
        "Hello",
        "Welcome to HaroShop, where you can achieve your dreams.",
        "Next"
    ){
        onNavigateToReadyScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun HelloScreenPreview(){
    HelloScreen(){}
}