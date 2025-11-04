package com.example.e_commerceapp.presentation.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerceapp.presentation.viewmodels.ImageSearchViewModel

@Composable
fun ImageSearchScreenWrapper(viewModel: ImageSearchViewModel = hiltViewModel()) {
    val products by viewModel.products.collectAsState()

    ImageSearchScreen(
        onSearchByLabel = { label ->
            viewModel.searchByLabel(label)
        }
    )

    LazyColumn {
        items(products) { product ->
            Text(text = product.productName)
        }
    }
}
