package com.example.e_commerceapp.presentation.utils

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.e_commerceapp.R

sealed class SubScreens(val route : String, val icon: Int, val selectedIcon : Int) {
    data object Cart : SubScreens("cart", R.drawable.cart, R.drawable.cart_selected )
    data object Shop : SubScreens("shop", R.drawable.shop, R.drawable.shop_selected)
    data object Profile : SubScreens(
        "profile",
        R.drawable.profile,
        R.drawable.profile_selected
    )
    data object WishList : SubScreens(
        "wishList",
        R.drawable.wishlist,
        R.drawable.wishlist_selected
    )
}