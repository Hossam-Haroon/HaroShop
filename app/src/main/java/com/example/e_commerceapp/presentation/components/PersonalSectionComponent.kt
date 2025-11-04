package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PersonalSectionComponent(innerNavController: NavController){
    val personalFields = listOf(
        "Profile" to "profileSettingsScreen",
        "Shipping Address" to "shippingAddressSettingsScreen",
        "Payment methods" to "paymentMethodsSettingsScreen"
    )
    Column {
        for (field in personalFields){
            SettingsSingleFieldComponent(fieldName = field.first) {
                innerNavController.navigate(field.second)
            }
            Spacer(modifier = Modifier.height(14.dp))
        }
    }
}