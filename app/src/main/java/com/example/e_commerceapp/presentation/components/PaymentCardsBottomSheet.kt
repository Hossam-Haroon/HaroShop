package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.data.remote.data.StripeCard
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCardsBottomSheet(
    cardList: List<StripeCard>,
    isCardSelected:(String)->Boolean,
    onDismissRequest: () -> Unit,
    onCardClick: (StripeCard) -> Unit,
    onAddCardClicked:()->Unit
) {
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(LightBlue),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Payment Methods",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyRow (
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            items(cardList) { card ->
                PaymentCardItemComponent(
                    card,
                    onCardClick = {
                        onCardClick(card)
                    },
                    isCardSelected = isCardSelected(card.id)
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(45.dp, 155.dp)
                        .clip(RoundedCornerShape(11.dp))
                        .background(
                            DarkBlue
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = GrayishWhite,
                        modifier = Modifier.clickable {
                            onAddCardClicked()
                        }
                    )
                }
            }
        }
    }
}