package com.example.e_commerceapp.presentation.screens.mainScreens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.e_commerceapp.presentation.components.AnnouncementBox
import com.example.e_commerceapp.presentation.components.CategoryCardItem
import com.example.e_commerceapp.presentation.components.ColorfulButton
import com.example.e_commerceapp.presentation.components.FlashSaleComponent
import com.example.e_commerceapp.presentation.components.ImageWithContentAndPriceItem
import com.example.e_commerceapp.presentation.components.ImageWithPortraitComponent
import com.example.e_commerceapp.presentation.components.MostPopularICardItem
import com.example.e_commerceapp.presentation.components.SeeAllObjectsRow
import com.example.e_commerceapp.presentation.components.StoryCardItem
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.GrayishWhite
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.ProfileAndStorySharedViewModel
import com.example.e_commerceapp.presentation.viewmodels.ProfileScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(rootNavController: NavController,innerNavController: NavController) {
    val profileViewModel: ProfileScreenViewModel = hiltViewModel()
    val sharedStoriesViewModel: ProfileAndStorySharedViewModel = hiltViewModel(
        rootNavController.getViewModelStoreOwner(rootNavController.graph.id)
    )
    val userData = profileViewModel.userData.collectAsState()
    val userImageUrl by profileViewModel.imageUrl.collectAsState()
    val recentlyViewedProductsUrl by profileViewModel.recentlyViewedProducts.collectAsState()
    val newProducts by profileViewModel.newProducts.collectAsState()
    val popularProducts by profileViewModel.popularProducts.collectAsState()
    val categories by profileViewModel.categories.collectAsState()
    val sampleImagesUrl by profileViewModel.sampleImagesUrl.collectAsState()
    val flashSaleProducts by profileViewModel.flashSaleProducts.collectAsState()
    val storyProducts by sharedStoriesViewModel.storyProducts.collectAsState()
    val justForYouProducts by profileViewModel.justForYouProducts.collectAsState()
    val storyProductsImagesUrl = emptyList<String>().toMutableList()
    val storyProductsId = emptyList<String>().toMutableList()
    for (product in storyProducts) {
        storyProductsImagesUrl += product.productImage
        storyProductsId += product.productId
    }
    LaunchedEffect(Unit) {
        launch { profileViewModel.getUserProfileById() }
        launch { profileViewModel.getRecentlyViewedProductsFromUserCollection() }
        launch { profileViewModel.getNewestTenProducts() }
        launch { profileViewModel.getMostPopularProducts() }
        launch { profileViewModel.getSampleCategories() }
        launch { profileViewModel.fetchFlashSaleProducts() }
        launch { sharedStoriesViewModel.getStoryProducts() }
        launch { profileViewModel.getJustForYouProducts() }
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1F)
            ) {
                ImageWithPortraitComponent(
                    portraitSize = 43.dp, imageSize = 40.dp,
                    userImageUrl
                ) {
                }
                ColorfulButton(
                    text = "My Activity",
                    width = 115.dp,
                    height = 35.dp,
                    backgroundColor = DarkBlue,
                    textSize = 16.sp,
                    textColor = GrayishWhite
                ) {
                    innerNavController.navigate(Screen.ActivityScreen.route)
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1F)
            ) {
                Image(painter = painterResource(
                    id = R.drawable.vouchers
                ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {
                            innerNavController.navigate(Screen.VoucherScreen.route)
                        }
                )
                Image(painter = painterResource(
                    id = R.drawable.top_menu
                ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {

                        }
                )
                Image(painter = painterResource(
                    id = R.drawable.settings
                ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {

                        }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Hello, ${userData.value?.userName}!",
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        AnnouncementBox()
        Spacer(modifier = Modifier.height(20.dp))
        if (recentlyViewedProductsUrl.isNotEmpty()) {
            Text(
                text = "Recently viewed",
                fontSize = 21.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
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
        Text(
            text = "My Orders",
            fontSize = 21.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ColorfulButton(
                text = "To Pay",
                width = 86.dp,
                height = 35.dp,
                backgroundColor = LightBlue,
                textSize = 16.sp,
                textColor = DarkBlue
            ) {

            }
            ColorfulButton(
                text = "To Receive",
                width = 118.dp,
                height = 35.dp,
                backgroundColor = LightBlue,
                textSize = 16.sp,
                textColor = DarkBlue
            ) {
                innerNavController.navigate(Screen.ToReceiveScreen.route)
            }
            ColorfulButton(
                text = "To Review",
                width = 118.dp,
                height = 35.dp,
                backgroundColor = LightBlue,
                textSize = 16.sp,
                textColor = DarkBlue
            ) {

            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = "Stories",
            fontSize = 21.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(storyProducts) { index, product ->
                StoryCardItem(product.productImage) {
                    rootNavController.navigate(
                        Screen.StoryScreen.createRoute(index)
                    )
                }
                Spacer(modifier = Modifier.width(9.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        SeeAllObjectsRow("New Items") {}
        Spacer(modifier = Modifier.height(13.dp))
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(newProducts.takeLast(5)) { pair ->
                pair.first?.let {
                    ImageWithContentAndPriceItem(
                        pair.second, it.description,
                        it.productPrice.toString(),
                        135.dp, 135.dp,
                        130.dp, 130.dp
                    ) {
                        rootNavController.navigate(
                            Screen.ProductScreen.createRouteForKnownProduct(
                                it.productId,
                                0,
                                "none",
                                1
                            )
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
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
        Spacer(modifier = Modifier.height(15.dp))
        SeeAllObjectsRow("Categories") {
            rootNavController.navigate(Screen.AllCategories.route)
        }
        Spacer(modifier = Modifier.height(13.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        ) {
            items(categories) { category ->
                sampleImagesUrl[category.categoryId]?.let {
                    CategoryCardItem(
                        it,
                        category.categoryName,
                        category.productCount
                    ) { categoryName ->
                        rootNavController.navigate(
                            Screen.CategoryProductsScreen.createRoute(categoryName)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Flash Sale",
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(13.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height(252.dp)
        ) {
            items(flashSaleProducts) { product ->
                FlashSaleComponent(
                    discount = product.discount.toInt(),
                    imageUrl = product.productImage
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
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Top Products",
            fontSize = 21.sp,
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(13.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .height(30.dp)
                .wrapContentWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Just For you",
                fontSize = 21.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.star_mark),
                contentDescription = null,
                modifier = Modifier.size(14.dp, 13.31.dp)
            )
        }
        Spacer(modifier = Modifier.height(13.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .height(790.dp)
        ) {
            items(justForYouProducts) { product ->
                ImageWithContentAndPriceItem(
                    imageUrl = product.productImage,
                    description = product.description,
                    price = product.productPrice.toFloat().toString(),
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
