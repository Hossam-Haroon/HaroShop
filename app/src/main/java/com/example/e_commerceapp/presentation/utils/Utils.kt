package com.example.e_commerceapp.presentation.utils

import androidx.compose.ui.graphics.Color

object Utils {
    const val STANDARD_PAYMENT = "Standard"
    const val EXPRESS_PAYMENT = "Express"
    const val STANDARD_SHIPPING_DURATION = "5-7 days"
    const val EXPRESS_SHIPPING_DURATION = "1-2 days"
    const val STANDARD_PRICE = "12,00"
    const val EXPRESS_PRICE = "3,00"
    fun generateDistinctColors(count: Int): List<Color> {
        return List(count) { index ->
            val hue = (index * 360f / count) % 360f
            Color.hsv(hue, 0.8f, 0.9f)
        }
    }
}