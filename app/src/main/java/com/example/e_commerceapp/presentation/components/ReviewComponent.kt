package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.raleWay
import kotlin.math.sin

@Composable
fun ReviewComponent(
    review: Review,
    isCurrentUser:Boolean,
    onDeleteClick:()->Unit,
    onUpdateClick:()->Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Column (
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
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
                        AsyncImage(
                            model = review.userImage,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = review.userName,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (i in 0 until review.userRate) {
                            Image(
                                painter = painterResource(id = R.drawable.selected_star),
                                contentDescription = null,
                                modifier = Modifier.size(15.73.dp, 15.dp)
                            )
                        }
                        for (i in 0 until 5 - review.userRate) {
                            Image(
                                painter = painterResource(id = R.drawable.unselected_star),
                                contentDescription = null,
                                modifier = Modifier.size(15.73.dp, 15.dp)
                            )
                        }
                    }
                }
            }
            if (isCurrentUser){
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.three_vertical_dots),
                        contentDescription = null,
                        modifier = Modifier
                            .size(19.dp)
                            .clickable {
                                expanded = true
                            }
                    )
                    OptionsMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        onUpdateClick = onUpdateClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width(20.dp))
            Box (modifier = Modifier.width(275.dp)){
                Text(
                    text = review.reviewText,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                )
            }
        }
    }
}