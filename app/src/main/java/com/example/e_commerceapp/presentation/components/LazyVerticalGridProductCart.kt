package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.presentation.appNavigation.Screen

@Composable
fun LazyVerticalGridProductCart(searchResults:List<Product>,rootNavController:NavController){
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(searchResults) { product ->
            ImageWithContentAndPriceItem(
                imageUrl = product.productImage,
                description = product.description,
                price = product.productPrice.toString(),
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