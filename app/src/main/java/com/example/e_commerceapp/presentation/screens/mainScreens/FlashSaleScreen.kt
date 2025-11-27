package com.example.e_commerceapp.presentation.screens.mainScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.DiscountValuesRowSectionComponent
import com.example.e_commerceapp.presentation.components.FlashSaleScreenProductComponent
import com.example.e_commerceapp.presentation.components.PopularProductsSection
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.FlashSaleScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun FlashSaleScreen(rootNavController: NavController) {
    val flashSaleViewModel: FlashSaleScreenViewModel = hiltViewModel()
    val discountValues by flashSaleViewModel.discountValues.collectAsState()
    val selectedDiscount by flashSaleViewModel.selectedDiscount.collectAsState()
    val popularProducts by flashSaleViewModel.popularProducts.collectAsState()
    val isLoading by flashSaleViewModel.isLoading.collectAsState()
    val productsWithSpecificDiscount by flashSaleViewModel.productsWithDiscount.collectAsState()
    Image(
        painter = painterResource(
            id = R.drawable.bubbles_flash_sale
        ),
        alignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize(),
        contentDescription = null
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Flash Sale",
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Choose Your Discount",
            fontFamily = raleWay,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        DiscountValuesRowSectionComponent(
            discountValue = selectedDiscount,
            discountValues = discountValues,
            onAllSelect = {
                flashSaleViewModel.selectDiscount("All")
            },
            onDiscountValueSelect = { discount ->
                flashSaleViewModel.selectDiscount(discount.discountValue)
            }
        )
        Spacer(modifier = Modifier.height(25.dp))
        if (selectedDiscount == "All") {
            Text(
                text = "All Discounts",
                fontFamily = raleWay,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "$selectedDiscount% Discount",
                fontFamily = raleWay,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .height(800.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        color = DarkBlue,
                        strokeWidth = 4.dp
                    )
                }

                productsWithSpecificDiscount.isEmpty() -> {
                    Text(
                        text = "No products available",
                        color = Color.Gray,
                        fontSize = 20.sp
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.height(800.dp)
                    ) {
                        items(productsWithSpecificDiscount) { product ->
                            FlashSaleScreenProductComponent(
                                product = product,
                                cardWidth = 165.dp,
                                cardHeight = 181.dp,
                                imageWidth = 155.dp,
                                imageHeight = 171.dp
                            ) {
                                rootNavController.navigate(
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
        Spacer(modifier = Modifier.height(20.dp))
        if (popularProducts.isNotEmpty()){
            PopularProductsSection(popularProducts, rootNavController)
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}