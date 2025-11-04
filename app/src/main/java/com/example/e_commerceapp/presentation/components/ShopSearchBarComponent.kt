package com.example.e_commerceapp.presentation.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.Grey1
import com.example.e_commerceapp.presentation.theme.Grey2
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ShopSearchBarComponent(query:String,onValueChange:(String)->Unit,onCameraSearch:()->Unit){
    Box (
        modifier = Modifier
            .size(248.dp, 50.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Grey1)
            .border(color = Grey1, width = 1.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Spacer(modifier = Modifier.width(5.dp))
            Box (
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                contentAlignment = Alignment.CenterStart
            ){
                BasicTextField(
                    value = query,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontFamily = raleWay
                    ),
                    cursorBrush = SolidColor(Color.Black)
                )
                if (query.isEmpty()) {
                    Text(
                        text = "Search",
                        color = Grey2,
                        fontSize = 13.83.sp,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Light
                    )
                }
            }
            Spacer(modifier = Modifier.width(3.dp))
            Icon(
                painter = painterResource(id = R.drawable.camera_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onCameraSearch()
                    },
                tint = DarkBlue
            )
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}