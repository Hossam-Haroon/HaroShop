package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.uiModels.UserReviewStateCheck
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowAllItemsInOrderBottomSheet(
    order: Order,
    orderItemsReviewStateFlow: StateFlow<Map<String, UserReviewStateCheck?>>,
    onDismissRequest: () -> Unit,
    openReviewBottomSheet: (OrderItem) -> Unit
) {
    val orderItemsReviewState by orderItemsReviewStateFlow.collectAsState()
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(LightBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Which item you want to review?",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                items(order.items) { item ->
                    OrderItemForReviewComponent(
                        item,
                        order.orderDate,
                        orderItemsReviewState[
                            item.productId
                        ]?.doesReviewExist == true
                    ) {
                        openReviewBottomSheet(item)
                    }

                }
            }
        }
    }
}