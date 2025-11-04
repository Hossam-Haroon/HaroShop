package com.example.e_commerceapp.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.presentation.appNavigation.Screen

@Composable
fun PopularProductsSection(popularProducts:List<Product>,rootNavController:NavController){
    if (popularProducts.isNotEmpty()) {
        SeeAllObjectsRow("Most Popular") {}
        Spacer(modifier = Modifier.height(13.dp))
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(popularProducts.takeLast(5)) { product ->
                Log.d("ProfileScreen", "$product")
                MostPopularICardItem(product.productImage, product.favouriteCount) {
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