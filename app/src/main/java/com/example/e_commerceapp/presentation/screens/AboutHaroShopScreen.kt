package com.example.e_commerceapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun AboutHaroShopScreen() {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_splash_logo),
                contentDescription = null,
                modifier = Modifier.size(82.dp, 92.dp)
            )
        }
        Spacer(modifier = Modifier.height(65.dp))
        Text(
            text = "About HaroShop",
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = R.string.about_app.toString(),
            fontSize = 16.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = R.string.help_section.toString(),
            fontFamily = raleWay,
            fontWeight = FontWeight.Light,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = R.string.my_email.toString(),
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}