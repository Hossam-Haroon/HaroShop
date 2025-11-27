package com.example.e_commerceapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.domain.mappers.toOrderItem
import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.OrderItemForReviewComponent
import com.example.e_commerceapp.presentation.components.ReviewProductInOrderBottomSheet
import com.example.e_commerceapp.presentation.components.ScreenHeadSectionComponent
import com.example.e_commerceapp.presentation.viewmodels.HistoryViewModel
import kotlinx.coroutines.launch

@Composable
fun HistoryScreen(innerNavController: NavController) {
    val historyViewModel: HistoryViewModel = hiltViewModel()
    val userData by historyViewModel.userData.collectAsState()
    val orderItems by historyViewModel.orderItems.collectAsState()
    val orderItemsReviewState by historyViewModel.orderProductsReviewState.collectAsState()
    var openReviewBottomSheet by remember { mutableStateOf(false) }
    var clickedProductToReview by remember { mutableStateOf<OrderItem?>(null) }
    var reviewRate by remember { mutableIntStateOf(0) }
    var reviewText by remember { mutableStateOf("") }
    var itemOrderNumber by remember { mutableStateOf("") }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        ScreenHeadSectionComponent(userData?.imageUrl, "History"){
            innerNavController.navigate(Screen.VoucherScreen.route)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column {
            for (item in orderItems) {
                OrderItemForReviewComponent(
                    orderItem = item.toOrderItem(),
                    orderDate = item.orderDate,
                    orderItemsReviewState[
                        item.productId
                    ]?.doesReviewExist == true,
                ) {
                    clickedProductToReview = item.toOrderItem()
                    itemOrderNumber = item.orderNumber
                    openReviewBottomSheet = true
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
    if (openReviewBottomSheet){
        clickedProductToReview?.let {
            ReviewProductInOrderBottomSheet(
                it,
                itemOrderNumber,
                reviewRate,
                reviewText,
                onRateClick = { rate ->
                    reviewRate = rate
                },
                onTextChange = {text ->
                    reviewText = text
                },
                onSetReview = { productId ->
                    val review = userData?.let { user ->
                        Review(
                            reviewId = "",
                            userId = user.userId,
                            userRate = reviewRate,
                            reviewText = reviewText,
                            userImage = user.imageUrl ?: "",
                            userName = user.userName
                        )
                    }
                    if (review != null) {
                        historyViewModel.createReviewAndUpdateUserReviewsIds(
                                review,
                                productId,
                                orderItems.toOrderItem()
                            )
                    }
                    Toast.makeText(
                        context,
                        "review is created successfully",
                        Toast.LENGTH_LONG
                    ).show()
                    openReviewBottomSheet = false
                },
                onDismissRequest = {openReviewBottomSheet = false}
            )
        }
    }
}