package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.R
import com.example.e_commerceapp.data.remote.data.StripeCard
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@Composable
fun PaymentCardItemComponent(
    stripeCard: StripeCard,
    isCardSelected:Boolean,
    onCardClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(290.dp, 165.dp)
    ) {
        Box(
            modifier = Modifier
                .size(280.dp, 155.dp)
                .clip(RoundedCornerShape(11.dp))
                .background(LightBlue)
                .clickable {
                    onCardClick()
                }
        ) {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.visa_logo),
                        contentDescription = null,
                        modifier = Modifier.size(90.dp, 50.dp)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 0..2){
                        Text(
                            text = "****",
                            fontFamily = raleWay,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )
                    }
                    Text(
                        text = stripeCard.card.last4,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )

                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Expiry Date: ${stripeCard.card.expMonth}/${stripeCard.card.expYear}",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
        if (isCardSelected){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopEnd
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(DarkBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_feather_check),
                        contentDescription = null,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
    }
}