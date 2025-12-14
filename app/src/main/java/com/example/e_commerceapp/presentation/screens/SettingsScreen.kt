package com.example.e_commerceapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.PersonalSectionComponent
import com.example.e_commerceapp.presentation.components.SettingsSingleFieldComponent
import com.example.e_commerceapp.presentation.theme.LightRed2
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.AuthViewModel

@Composable
fun SettingsScreen(
    innerNavController: NavController,
    rootNavController: NavController
) {
    val authViewModel : AuthViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Settings",
            fontFamily = raleWay,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Personal",
            fontFamily = raleWay,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        PersonalSectionComponent(innerNavController)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Shop",
            fontFamily = raleWay,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        //
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Account",
            fontFamily = raleWay,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        SettingsSingleFieldComponent(fieldName = "About HaroShop") {
            innerNavController.navigate(Screen.AboutHaroShopScreen.route)
        }
        SettingsSingleFieldComponent(fieldName = "Log Out") {
            authViewModel.logOutUser()
            rootNavController.popBackStack()
            rootNavController.navigate("introduction")
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Delete My Account",
            fontFamily = raleWay,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = LightRed2,
            modifier = Modifier.clickable {
                // open dialog to delete the account
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "HaroShop",
            fontFamily = raleWay,
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = "Version 1.0 November, 2025",
            fontFamily = raleWay,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}