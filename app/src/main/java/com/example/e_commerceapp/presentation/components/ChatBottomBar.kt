package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun ChatBottomBar(
    textValue: String,
    onValueChanged: (String) -> Unit,
    onSendButtonCLick:()->Unit
    ) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlueishWhite),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(80.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = textValue,
                onValueChange = onValueChanged
            )
            if (textValue.isEmpty()) {
                Text(
                    text = "Message",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    color = DarkBlue
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = painterResource(id = R.drawable.button),
            contentDescription = null,
            modifier = Modifier.size(50.dp).clickable {
                if (textValue.isNotEmpty()){
                    onSendButtonCLick()
                }
            }
        )
    }
}