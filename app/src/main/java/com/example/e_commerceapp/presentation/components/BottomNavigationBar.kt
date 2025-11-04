package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.utils.SubScreens

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        SubScreens.Shop,
        SubScreens.WishList,
        SubScreens.Cart,
        SubScreens.Profile
    )
    NavigationBar() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            val selected = currentDestination?.route == screen.route
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = if (selected) {
                            painterResource(id = screen.selectedIcon)
                        } else {
                            painterResource(id = screen.icon)
                        },
                        contentDescription = null,
                        tint = if (selected) Color.Black else DarkBlue
                    )
                },
                selected = selected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}