package com.example.e_commerceapp.presentation.screens.mainScreens

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.AutoImageCarouselComponent
import com.example.e_commerceapp.presentation.components.CategoryCardItem
import com.example.e_commerceapp.presentation.components.FlashSaleComponent
import com.example.e_commerceapp.presentation.components.ImageWithContentAndPriceItem
import com.example.e_commerceapp.presentation.components.MostPopularICardItem
import com.example.e_commerceapp.presentation.components.MostPopularSectionComponent
import com.example.e_commerceapp.presentation.components.SeeAllObjectsRow
import com.example.e_commerceapp.presentation.components.ShopSearchBarComponent
import com.example.e_commerceapp.presentation.components.ShopSearchButtonToSearchScreen
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.uiModels.FlashSaleSlideItem
import com.example.e_commerceapp.presentation.viewmodels.ShopScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun ShopScreen(rootNavController:NavController,innerNavController: NavController){
    val shopViewModel : ShopScreenViewModel = hiltViewModel()
    val categories by shopViewModel.categories.collectAsState()
    val newProducts by shopViewModel.newProducts.collectAsState()
    val flashSaleProducts by shopViewModel.flashSaleProducts.collectAsState()
    val popularProducts by shopViewModel.popularProducts.collectAsState()
    val justForYouProducts by shopViewModel.justForYouProducts.collectAsState()
    val flashSaleBanners = listOf(
        FlashSaleSlideItem(R.drawable.flash_sale_hupe_second,R.drawable.shirt14_no_background),
        FlashSaleSlideItem(R.drawable.flash_sale_hype_rectangle,R.drawable.flash_sale_hype_watch)
    )

    Column (
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Shop",
                fontSize = 28.sp,
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            ShopSearchButtonToSearchScreen{
                innerNavController.navigate(Screen.SearchScreen.route)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        AutoImageCarouselComponent(images = flashSaleBanners){
            innerNavController.navigate(Screen.FlashSaleScreen.route)
        }
        Spacer(modifier = Modifier.height(20.dp))
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
                CategoryCardItem(
                    category.thumbnailSampleImagesId,
                    category.categoryName,
                    category.productCount
                ) { categoryName ->
                    rootNavController.navigate(
                        Screen.CategoryProductsScreen.createRoute(categoryName)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        // top products
        Spacer(modifier = Modifier.height(20.dp))
        SeeAllObjectsRow("New Items") {}
        Spacer(modifier = Modifier.height(13.dp))
        LazyRow(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(newProducts.takeLast(5)) { product ->
                product.let {
                    ImageWithContentAndPriceItem(
                        it.productImage, it.description,
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
        Spacer(modifier = Modifier.height(20.dp))
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
        Spacer(modifier = Modifier.height(20.dp))
        if (popularProducts.isNotEmpty()) {
            MostPopularSectionComponent(
                popularProducts = popularProducts,
                rootNavController = rootNavController
            )
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
            items(justForYouProducts){product ->
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
        Spacer(modifier = Modifier.height(10.dp))
    }
}