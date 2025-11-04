package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite

@Composable
fun EmailAndPhoneNumberUpdateDialogComponent(
    email:String,
    phoneNumber:String,
    onEmailChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onConfirm:()->Unit
){
    Box(
        modifier = Modifier.size(347.dp, 320.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .size(347.dp, 280.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .background(Color.White),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(50.dp))
                    CustomGrayTextField(
                        hintText = "Enter Your Email",
                        textValue = email,
                        onValueChange = onEmailChange,
                        width = 293.dp,
                        height = 57.37.dp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    CustomGrayTextField(
                        hintText = "Enter Your PhoneNumber",
                        textValue = phoneNumber,
                        onValueChange = onPhoneNumberChange,
                        width = 293.dp,
                        height = 57.37.dp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    ColorfulButton(
                        text = "Confirm",
                        width = 128.dp,
                        height = 40.dp,
                        backgroundColor = DarkBlue,
                        textSize = 16.sp,
                        textColor = GrayishWhite
                    ) {
                        onConfirm()
                    }
                }
            }
        }
        Box(
            modifier = Modifier.size(80.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ellipse_dialog),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.red_ecllipse),
                contentDescription = null,
                modifier = Modifier.size(50.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.exclamation),
                contentDescription = null,
                modifier = Modifier.size(2.dp, 13.dp)
            )
        }
    }
}