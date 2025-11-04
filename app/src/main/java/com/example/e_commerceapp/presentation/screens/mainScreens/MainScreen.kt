package com.example.e_commerceapp.presentation.screens.mainScreens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_commerceapp.presentation.components.BottomNavigationBar
import com.example.e_commerceapp.presentation.screens.AboutHaroShopScreen
import com.example.e_commerceapp.presentation.screens.ActivityScreen
import com.example.e_commerceapp.presentation.screens.HistoryScreen
import com.example.e_commerceapp.presentation.screens.PaymentScreen
import com.example.e_commerceapp.presentation.screens.SettingsScreen
import com.example.e_commerceapp.presentation.screens.ToReceiveScreen
import com.example.e_commerceapp.presentation.screens.VouchersScreen

@Composable
fun MainScreen(rootNavController: NavController){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomNavigationBar(navController)}) { padding ->
        NavHost(
            navController = navController,
            startDestination = "profile",
            modifier = Modifier.padding(padding)
        ){
            composable("profile"){ ProfileScreen(rootNavController,navController)}
            composable("cart"){ CartScreen(rootNavController,navController)}
            composable("wishList"){ WishListScreen(rootNavController)}
            composable("shop"){ ShopScreen(rootNavController,navController)}
            composable("flashSaleScreen"){ FlashSaleScreen(rootNavController) }
            composable("searchScreen"){ SearchScreen(rootNavController) }
            composable("paymentScreen"){ PaymentScreen(rootNavController,navController) }
            composable("toReceiveScreen"){ ToReceiveScreen(navController) }
            composable("activityScreen"){ ActivityScreen(navController) }
            composable("historyScreen"){ HistoryScreen(navController) }
            composable("voucherScreen"){ VouchersScreen() }
            composable("aboutScreen"){ AboutHaroShopScreen() }
            composable("settingsScreen"){ SettingsScreen(navController) }
        }
    }
}