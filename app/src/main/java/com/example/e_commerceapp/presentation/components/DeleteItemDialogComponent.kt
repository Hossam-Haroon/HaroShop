package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightRed2
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun DeleteItemDialogComponent(
    itemName:String,
    onConfirm:()->Unit,
    onCancel:()->Unit
) {
    Box(
        modifier = Modifier.size(347.dp, 267.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(40.dp))
            Box(
                modifier = Modifier
                    .size(347.dp, 225.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .background(Color.White),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column (
                    modifier = Modifier.padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ){
                    Spacer(modifier = Modifier.height(35.dp))
                    Text(
                        text = "Are you sure you want to delete this $itemName?",
                        fontSize = 16.sp,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row (
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically){
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
                        ColorfulButton(
                            text = "Cancel",
                            width = 128.dp,
                            height = 40.dp,
                            backgroundColor = LightRed2,
                            textSize = 16.sp,
                            textColor = GrayishWhite
                        ) {
                            onCancel()
                        }
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