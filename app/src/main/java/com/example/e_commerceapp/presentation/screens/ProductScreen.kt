package com.example.e_commerceapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.e_commerceapp.domain.model.Review
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.ColorfulButton
import com.example.e_commerceapp.presentation.components.DeleteItemDialogComponent
import com.example.e_commerceapp.presentation.components.DeliverySectionComponent
import com.example.e_commerceapp.presentation.components.DiscountAndSharingSection
import com.example.e_commerceapp.presentation.components.PopularProductsSectionComponent
import com.example.e_commerceapp.presentation.components.ProductModalBottomSheet
import com.example.e_commerceapp.presentation.components.ProductScreenBottomBar
import com.example.e_commerceapp.presentation.components.ReviewComponent
import com.example.e_commerceapp.presentation.components.SettingReviewBottomShowComponent
import com.example.e_commerceapp.presentation.components.SettingUserReviewComponent
import com.example.e_commerceapp.presentation.components.VariationsAndSelectionSection
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.LightRed
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.ProductScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductScreen(
    productId: String,
    productColorOption: Long,
    productSizeOption: String,
    productQuantity: Int,
    navController: NavController
) {
    val productViewModel: ProductScreenViewModel = hiltViewModel()
    val product by productViewModel.product.collectAsState()
    val imageUrl by productViewModel.imageUrl.collectAsState()
    val userReviewState by productViewModel.userReviewState.collectAsState()
    val reviewsState by productViewModel.reviewsOnCurrentProductState.collectAsState()
    val highestRateReview by productViewModel.highestRateReview.collectAsState()
    val popularProducts by productViewModel.popularProducts.collectAsState()
    val isProductLiked by productViewModel.isProductLiked.collectAsState()
    val currentUser by productViewModel.currentUser.collectAsState()
    val priceAfterDiscount = product?.productPrice?.times(((100 - product!!.discount) / 100))
    val context = LocalContext.current
    var colorOption by remember { mutableLongStateOf(productColorOption) }
    var sizeOption by remember { mutableStateOf(productSizeOption) }
    var quantity by remember { mutableIntStateOf(productQuantity) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showReviewBottomSheet by remember { mutableStateOf(false) }
    var showUpdateReviewBottomSheet by remember { mutableStateOf(false) }
    var showDeleteReviewDialog by remember { mutableStateOf(false) }
    var reviewText by remember { mutableStateOf("") }
    var reviewRate by remember { mutableIntStateOf(0) }
    LaunchedEffect(productId) {
        launch { productViewModel.getProductById(productId) }
        launch { productViewModel.checkUserReviewOnTheProductState(productId) }
        launch { productViewModel.checkIfReviewsAreEmptyOrNot(productId) }
        launch { productViewModel.getHighestRateReviewAsSample(productId) }
        launch { productViewModel.getMostPopularProducts() }
        launch { productViewModel.getIsProductLikedState(productId) }
        launch { productViewModel.addLastViewedProductToRecentlyViewed(productId) }
        launch { productViewModel.getCurrentUserDataForReview() }
    }
    LaunchedEffect(Unit) {
        productViewModel.uiEvent.collect { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Scaffold(
        bottomBar = {
            ProductScreenBottomBar(
                isProductLiked, onClick = {
                    /* if (isProductLiked){
                         productViewModel.removeProductIdFromFavouriteProducts(productId)
                     }else{
                         productViewModel.addProductIdToFavouriteProducts(productId)
                     }*/
                    productViewModel.toggleLikedProductsCounts(productId)
                }, onAddToCart = {
                    product?.let {
                        productViewModel.insertProductToCart(
                            it,
                            quantity,
                            sizeOption,
                            colorOption,
                            it.productType
                        )
                    }
                    Toast.makeText(context, "product added to cart", Toast.LENGTH_LONG).show()
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(439.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            product?.let {
                if (priceAfterDiscount != null) {
                    DiscountAndSharingSection(product = it, priceAfterDiscount = priceAfterDiscount)
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            product?.let {
                Text(
                    text = it.description,
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            VariationsAndSelectionSection(colorOption = colorOption, sizeOption = sizeOption) {
                showBottomSheet = true
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Specifications",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Material",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(LightRed),
                contentAlignment = Alignment.Center
            ) {
                product?.material?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Medium,
                        fontFamily = raleWay,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(3.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Origin",
                fontSize = 17.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(25.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(LightBlue),
                contentAlignment = Alignment.Center
            ) {
                product?.origin?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Medium,
                        fontFamily = raleWay,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(1.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Delivery",
                fontSize = 20.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            DeliverySectionComponent("Standard", "5-7", "3,00")
            Spacer(modifier = Modifier.height(10.dp))
            DeliverySectionComponent("Express", "1-2", "12,00")
            Spacer(modifier = Modifier.height(25.dp))
            if (userReviewState?.review == null) {
                SettingUserReviewComponent(reviewRate,
                    onRateCLick = {
                        reviewRate = it
                        showReviewBottomSheet = true
                    },
                    onWriteReviewClick = { showReviewBottomSheet = true }
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Rating & Reviews",
                fontSize = 20.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            when {
                userReviewState?.doesReviewExist == true -> {
                    ReviewComponent(
                        userReviewState!!.review,
                        true,
                        onDeleteClick = {
                            showDeleteReviewDialog = true
                        },
                        onUpdateClick = {
                            reviewRate = userReviewState!!.review.userRate
                            reviewText = userReviewState!!.review.reviewText
                            showUpdateReviewBottomSheet = true
                        }
                    )
                }
                reviewsState -> {
                    highestRateReview?.let {
                        ReviewComponent(
                            review = it,
                            isCurrentUser = false,
                            onUpdateClick = {},
                            onDeleteClick = {}
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            ColorfulButton(
                text = "View All Reviews",
                width = 335.dp,
                height = 40.dp,
                backgroundColor = DarkBlue,
                textSize = 16.sp,
                textColor = BlueishWhite
            ) {
                navController.navigate(Screen.ReviewsScreen.createRoute(productId))
            }
            Spacer(modifier = Modifier.height(20.dp))
            PopularProductsSectionComponent(popularProducts, navController)
            Spacer(modifier = Modifier.height(20.dp))
        }
        if (showBottomSheet) {
            if (priceAfterDiscount != null) {
                imageUrl?.let { imageUrl ->
                    product?.let { product ->
                        ProductModalBottomSheet(
                            imageUrl, priceAfterDiscount,
                            product, colorOption, sizeOption, quantity,
                            setColorOption = { color -> colorOption = color },
                            setSizeOption = { size -> sizeOption = size },
                            onDismissRequest = { showBottomSheet = false },
                            decreaseQuantityByOne = { quantity -= 1 },
                            increaseQuantityByOne = { quantity += 1 })
                    }
                }
            }
        }
        if (showReviewBottomSheet) {
            currentUser?.let {
                SettingReviewBottomShowComponent(
                    textValue = reviewText,
                    rate = reviewRate,
                    reviewer = it,
                    onRateCLick = { reviewRate = it },
                    onDismissRequest = { showReviewBottomSheet = false },
                    onValueChange = { reviewText = it },
                    onShareButtonClick = {
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
                            productViewModel.createReviewAndUpdateUserReviewsIds(review, productId)
                        }
                        Toast.makeText(
                            context,
                            "review is created successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        showReviewBottomSheet = false
                    }
                )
            }
        }
        if (showUpdateReviewBottomSheet) {
            currentUser?.let {
                SettingReviewBottomShowComponent(
                    textValue = reviewText,
                    rate = reviewRate,
                    reviewer = it,
                    onRateCLick = { rate -> reviewRate = rate },
                    onDismissRequest = { showUpdateReviewBottomSheet = false },
                    onValueChange = { text -> reviewText = text }
                ) {
                    userReviewState?.review?.let { review ->
                        productViewModel.updateUserReview(
                            review.reviewId,
                            productId,
                            reviewRate,
                            reviewText
                        )
                        Toast.makeText(
                            context,
                            "review is updated successfully",
                            Toast.LENGTH_LONG
                        ).show()
                        reviewRate = 0
                        reviewText = ""
                        showReviewBottomSheet = false
                    }
                }
            }
        }
        if (showDeleteReviewDialog) {
            Dialog(onDismissRequest = { showDeleteReviewDialog = false }) {
                DeleteItemDialogComponent(
                    itemName = "Review",
                    onConfirm = {
                        productViewModel.deleteReviewAndUpdateUserReviewsIds(
                            userReviewState!!.review, productId
                        )
                        showDeleteReviewDialog = false
                        Toast.makeText(
                            context,
                            "review has been deleted!",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    onCancel = { showDeleteReviewDialog = false }
                )
            }
        }
    }
}


