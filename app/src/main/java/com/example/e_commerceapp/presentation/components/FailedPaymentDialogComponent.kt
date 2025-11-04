package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun FailedPaymentDialogComponent(
    onTryAgainClicked:()->Unit
){
    Box(modifier = Modifier.size(347.dp, 263.dp)) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .size(347.dp, 225.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .background(
                        Color.White
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "We couldn't proceed",
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "your payment",
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Please, change your payment method or try again",
                        fontFamily = raleWay,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    ColorfulButton(
                        text = "Try Again",
                        width = 128.dp,
                        height = 40.dp,
                        backgroundColor = Color.Black,
                        textSize = 16.sp,
                        textColor = GrayishWhite
                    ) {
                        onTryAgainClicked()
                    }
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                modifier = Modifier.size(80.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = CircleShape,
                colors = CardColors(
                    Color.White,
                    Color.Black,
                    Color.Gray,
                    GrayishWhite
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
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
        }
    }
}