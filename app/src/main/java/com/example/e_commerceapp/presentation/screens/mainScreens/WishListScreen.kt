package com.example.e_commerceapp.presentation.screens.mainScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.ImageWithPortraitComponent
import com.example.e_commerceapp.presentation.components.MostPopularSectionComponent
import com.example.e_commerceapp.presentation.components.WishListComponent
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.WishListScreenViewModel

@Composable
fun WishListScreen(rootNavController: NavController) {
    val viewModel: WishListScreenViewModel = hiltViewModel()
    val recentlyViewedProductsUrl by viewModel.recentlyViewedProducts.collectAsState()
    val popularProducts by viewModel.popularProducts.collectAsState()
    val likedProducts by viewModel.likedProducts.collectAsState()
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "WishList",
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        if (recentlyViewedProductsUrl.isNotEmpty()) {
            Text(
                text = "Recently viewed",
                fontSize = 21.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            LazyRow(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()

            ) {
                items(recentlyViewedProductsUrl) {
                    ImageWithPortraitComponent(60.dp, 50.dp, it.second) {
                        rootNavController.navigate(
                            Screen.ProductScreen.createRouteForKnownProduct(
                                it.first,
                                0,
                                "none",
                                1
                            )
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (likedProducts.isNotEmpty()) {
            LazyColumn {
                items(likedProducts) { product ->
                    WishListComponent(
                        imageUrl = product.productImage,
                        product = product,
                        onNavigateClick = {
                            rootNavController.navigate(
                                Screen.ProductScreen.createRouteForKnownProduct(
                                    product.productId,
                                    0,
                                    "none",
                                    1
                                )
                            )
                        },
                        onCLick = {},
                        onDelete = {viewModel.unlikeProduct(product.productId)}
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_wishlist_screen),
                    contentDescription = null,
                    modifier = Modifier.size(134.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (popularProducts.isNotEmpty()) {
            MostPopularSectionComponent(popularProducts, rootNavController)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}