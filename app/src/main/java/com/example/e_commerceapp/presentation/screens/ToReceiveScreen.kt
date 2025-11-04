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
import com.example.e_commerceapp.domain.model.Order
import com.example.e_commerceapp.domain.model.OrderItem
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.OrderCardComponent
import com.example.e_commerceapp.presentation.components.ReviewProductInOrderBottomSheet
import com.example.e_commerceapp.presentation.components.ShowAllItemsInOrderBottomSheet
import com.example.e_commerceapp.presentation.components.ToReceiveHeadLineComponent
import com.example.e_commerceapp.presentation.viewmodels.ToReceiveScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun ToReceiveScreen(innerNavController: NavController){
    val toReceiveViewModel : ToReceiveScreenViewModel = hiltViewModel()
    val userImageUrl by toReceiveViewModel.imageUrl.collectAsState()
    val userOrders by toReceiveViewModel.userOrders.collectAsState()
    val userReviewState by toReceiveViewModel.userReviewState.collectAsState()
    val orderItemsReviewState by toReceiveViewModel.orderProductsReviewState.collectAsState()
    val currentUser by toReceiveViewModel.currentUser.collectAsState()
    var deliveredProductBottomSheet by remember { mutableStateOf(false) }
    var openReviewBottomSheet by remember { mutableStateOf(false) }
    var clickedOrder by remember { mutableStateOf<Order?>(null) }
    var orderNumber by remember { mutableStateOf("") }
    var clickedProductToReview by remember { mutableStateOf<OrderItem?>(null) }
    var reviewRate by remember { mutableIntStateOf(0) }
    var reviewText by remember { mutableStateOf("") }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        launch { toReceiveViewModel.getAllOrdersForUser() }
        launch { toReceiveViewModel.getCurrentUserDataForReview() }
        launch { toReceiveViewModel.getUserProfileById() }
    }
    LaunchedEffect(orderItemsReviewState) {
        println("orderItemsReviewState: ${orderItemsReviewState.keys}")
    }

    Column (
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState())
    ){
        Spacer(modifier = Modifier.height(30.dp))
        ToReceiveHeadLineComponent(userImageUrl){
            innerNavController.navigate(Screen.VoucherScreen.route)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            for (order in userOrders){
                OrderCardComponent(
                    order = order,
                    onTrackClicked = {
                        // go to track screen
                    },
                    onReviewClicked = {
                        clickedOrder = order
                        clickedOrder?.items?.let {
                            toReceiveViewModel.checkOrderProductsReviewExistence(it)
                        }
                        deliveredProductBottomSheet = true
                    }
                )
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
    if (deliveredProductBottomSheet){
        clickedOrder?.let {
            ShowAllItemsInOrderBottomSheet(
                it,
                toReceiveViewModel.orderProductsReviewState,
                onDismissRequest = {
                    deliveredProductBottomSheet = false
                },
                openReviewBottomSheet = {item ->
                    clickedProductToReview = item
                    orderNumber = it.orderNumber
                    openReviewBottomSheet = true
                }
            )
        }
    }
    if (openReviewBottomSheet){
        clickedProductToReview?.let {
            ReviewProductInOrderBottomSheet(it,orderNumber,
                reviewRate,
                reviewText,
                onRateClick = { rate ->
                    reviewRate = rate
                },
                onTextChange = {text ->
                    reviewText = text
                },
                onSetReview = { productId ->
                    val review = currentUser?.let { user ->
                        Review(
                            reviewId = "",
                            userId = currentUser!!.userId,
                            userRate = reviewRate,
                            reviewText = reviewText,
                            userImage = user.imageUrl ?: "",
                            userName = user.userName
                        )
                    }
                    if (review != null) {
                        clickedOrder?.let { clickedOrder ->
                            toReceiveViewModel.createReviewAndUpdateUserReviewsIds(
                                review,
                                productId,
                                clickedOrder.items
                            )
                        }
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