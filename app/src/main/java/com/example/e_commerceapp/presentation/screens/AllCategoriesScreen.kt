package com.example.e_commerceapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.e_commerceapp.presentation.components.CategoryCardItem
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.AllCategoriesScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun AllCategoriesScreen(navController:NavController){
    val allCategoriesScreenViewModel : AllCategoriesScreenViewModel = hiltViewModel()
    val imagesUrl by allCategoriesScreenViewModel.imagesUrl.collectAsState()
    val categories by allCategoriesScreenViewModel.categories.collectAsState()
    LaunchedEffect(Unit) {
        launch {
            allCategoriesScreenViewModel.getAllCategories()
        Log.d("AllCategoriesScreen","${categories}/n ${imagesUrl}")}
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .padding(bottom = 5.dp)
            .fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = "All Categories",
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(categories){category ->
                imagesUrl[category.categoryId]?.let {
                    CategoryCardItem(
                        sampleImagesUrl = it,
                        categoryName = category.categoryName,
                        productNumbers = category.productCount
                    ) {categoryName ->
                        navController.navigate(
                            Screen.CategoryProductsScreen.createRoute(categoryName)
                        )
                    }
                }
            }
        }
    }
}