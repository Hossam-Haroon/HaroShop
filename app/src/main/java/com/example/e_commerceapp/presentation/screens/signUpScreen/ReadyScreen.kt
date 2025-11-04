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
fun ReadyScreen(onNavigateToProfileScreen: ()-> Unit){
    Image(
        painter = painterResource(id = R.drawable.bubbles_ready_screen),
        modifier = Modifier.fillMaxSize(),
        alignment = Alignment.TopCenter,
        contentDescription = null
    )
    IntroductionCard(
        firstDot = R.drawable.gray_dot,
        secondDot = R.drawable.blue_dot,
        imageId = R.drawable.ready_card_image,
        headLineText = "Ready?",
        text = "are you ready to discover this new world with us?",
        buttonText = "Let's Start"
    ){
        onNavigateToProfileScreen()
    }
}
@Preview(showBackground = true)
@Composable
fun ReadyScreenPreview(){
    ReadyScreen(){}
}