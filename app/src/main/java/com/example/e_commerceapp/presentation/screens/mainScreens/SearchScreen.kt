package com.example.e_commerceapp.presentation.screens.mainScreens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.ImageWithContentAndPriceItem
import com.example.e_commerceapp.presentation.components.LazyVerticalGridProductCart
import com.example.e_commerceapp.presentation.components.ShopSearchBarComponent
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightRed2
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.SearchScreenViewModel

@Composable
fun SearchScreen(rootNavController: NavController) {
    val searchViewmodel: SearchScreenViewModel = hiltViewModel()
    val searchResults by searchViewmodel.searchResults.collectAsState()
    val query by searchViewmodel.query.collectAsState()
    val recentSearches by searchViewmodel.recentSearches.collectAsState()
    val discoverSectionProducts by searchViewmodel.discoverSectionProducts.collectAsState()
    val recommendations = listOf("trouser", "shirt", "bag", "shoes", "watch")
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Search",
                fontSize = 28.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            ShopSearchBarComponent(
                query,
                onValueChange = {
                    searchViewmodel.onChangeQuery(it)
                },
                onCameraSearch = {
                    rootNavController.navigate("imageSearchScreen")
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        if (query.isEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Search history",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
                Box(
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(GrayishWhite)
                        .clickable {
                            searchViewmodel.clearSearchHistory()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = null,
                        tint = LightRed2,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ){
                items(recentSearches){keyword->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .background(GrayishWhite)
                            .clickable { searchViewmodel.onChangeQuery(keyword) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = keyword,
                            fontFamily = raleWay,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Recommendations",
                fontFamily = raleWay,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ){
                items(recommendations){keyword->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9.dp))
                            .background(GrayishWhite)
                            .clickable { searchViewmodel.onChangeQuery(keyword) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = keyword,
                            fontFamily = raleWay,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(5.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Discover",
                fontFamily = raleWay,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyRow {
                items(discoverSectionProducts) { product ->
                    ImageWithContentAndPriceItem(
                        imageUrl = product.productImage,
                        description = product.description,
                        price = product.productPrice.toString(),
                        cardWidth = 140.dp,
                        cardHeight = 140.dp,
                        imageWidth = 130.dp,
                        imageHeight = 130.dp
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
        } else {
            LazyVerticalGridProductCart(searchResults, rootNavController)
        }
    }
}