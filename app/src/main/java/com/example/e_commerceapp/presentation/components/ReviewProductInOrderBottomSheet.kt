package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.Grey2
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewProductInOrderBottomSheet(
    orderItem: OrderItem,
    orderNumber: String,
    rate: Int,
    reviewText:String,
    onRateClick: (Int) -> Unit,
    onTextChange:(String)->Unit,
    onSetReview:(String)->Unit,
    onDismissRequest:()->Unit
) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(LightBlue),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Review",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = orderItem.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = orderItem.title,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Order #${orderNumber}",
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            SettingRateComponent(rate) { index ->
                onRateClick(index)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Box(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(335.dp, 100.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(
                        LightBlue
                    ),
                contentAlignment = Alignment.TopStart
            ){
                BasicTextField(
                    value = reviewText,
                    onValueChange = onTextChange,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 17.sp,
                        fontFamily = raleWay
                    ),
                    modifier = Modifier
                        .padding(horizontal = 10.dp).padding(top = 5.dp)
                        .fillMaxSize(),
                )
                if (reviewText.isEmpty()){
                    Text(
                        text = "Your Comment",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(horizontal = 10.dp).padding(top = 5.dp)
                            .fillMaxSize(),
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            ColorfulButton(
                text = "Say it!",
                width = 335.dp,
                height = 40.dp,
                backgroundColor = DarkBlue,
                textSize = 16.sp,
                textColor = GrayishWhite
            ) {
                onSetReview(orderItem.productId)
            }
        }
    }

}