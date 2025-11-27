package com.example.e_commerceapp.presentation.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.ImageWithContentAndPriceItem
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.CategoryProductsScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun CategoryProductsScreen(categoryName: String, navController: NavController) {
    val categoryProductsScreenViewModel: CategoryProductsScreenViewModel = hiltViewModel()
    val products by categoryProductsScreenViewModel.products.collectAsState()
    val isLoading by categoryProductsScreenViewModel.isLoading.collectAsState()
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(60.dp),
                color = Color.Black,
                strokeWidth = 4.dp
            )
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "All Products",
                        fontSize = 28.sp,
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Bold
                    )
                    Image(
                        painter = painterResource(id = R.drawable.close),
                        contentDescription = null,
                        modifier = Modifier
                            .size(13.43.dp, 13.42.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                    items(products) { product ->
                        ImageWithContentAndPriceItem(
                            imageUrl = product.productImage,
                            description = product.description,
                            price = "${product.productPrice * ((100 - product.discount) / 100)}",
                            cardWidth = 165.dp,
                            cardHeight = 181.dp,
                            imageWidth = 155.dp,
                            imageHeight = 171.dp
                        ) {
                            navController.navigate(
                                Screen.ProductScreen.createRouteForKnownProduct(
                                    product.productId,
                                    0,
                                    "none",
                                    1
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}