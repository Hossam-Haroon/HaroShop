package com.example.e_commerceapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.e_commerceapp.presentation.components.ReviewComponent
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.ReviewsScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun ReviewsScreen(productId: String, navController: NavController) {
    val reviewsViewModel: ReviewsScreenViewModel = hiltViewModel()
    val allReviews by reviewsViewModel.productReviews.collectAsState()
    val currentUser by reviewsViewModel.currentUser.collectAsState()
    LaunchedEffect(Unit) {
        launch { reviewsViewModel.getAllProductReviews(productId) }
        launch { reviewsViewModel.getCurrentUser() }
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .padding(top = 10.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Reviews",
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
        Spacer(modifier = Modifier.height(30.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(allReviews) { review ->
                if (currentUser?.userId == review.userId){
                    ReviewComponent(
                        review = review,
                        true,
                        onDeleteClick = {},
                        onUpdateClick = {}
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                            .background(Color.Black)
                    )
                }else{
                    ReviewComponent(
                        review = review,
                        false,
                        onDeleteClick = {},
                        onUpdateClick = {}
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                            .background(Color.Black)
                    )
                }
            }
        }
    }
}