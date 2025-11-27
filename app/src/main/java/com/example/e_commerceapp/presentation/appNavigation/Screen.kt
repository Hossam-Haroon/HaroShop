package com.example.e_commerceapp.presentation.appNavigation

sealed class Screen(val route: String) {
    data object Introduction : Screen("introduction")
    data object SignUp : Screen("signUp")
    data object LogIn : Screen("logIn")
    data object Hello : Screen("hello")
    data object Ready : Screen("ready")
    data object Password : Screen("password") {
        fun createRoute(email: String) = "${Password.route}/$email"
    }
    data object MainScreen : Screen("main")
    data object AllCategories : Screen("allCategories")
    data object CategoryProductsScreen : Screen("categoryProducts") {
        fun createRoute(categoryName: String) = "${CategoryProductsScreen.route}/$categoryName"
    }
    data object ProductScreen : Screen("productScreen") {
        fun createRouteForKnownProduct(
            productId: String, colorOption: Long, sizeOption: String, quantity: Int
        ) = "${ProductScreen.route}/$productId/$colorOption/$sizeOption/$quantity"
    }
    data object ReviewsScreen : Screen("reviewsScreen") {
        fun createRoute(productId: String) = "${ReviewsScreen.route}/$productId"
    }
    data object FlashSaleScreen : Screen("flashSaleScreen")
    data object HistoryScreen : Screen("historyScreen")
    data object ActivityScreen : Screen("activityScreen")
    data object SearchScreen : Screen("searchScreen")
    data object ToReceiveScreen : Screen("toReceiveScreen")
    data object PaymentScreen : Screen("paymentScreen")
    data object VoucherScreen : Screen("voucherScreen")
    data object SettingsScreen : Screen("settingsScreen")
    data object ProfileSettingsScreen : Screen("profileSettingsScreen")
    data object PaymentMethodsSettingsScreen : Screen("shippingAddressSettingsScreen")
    data object ShippingAddressSettingsScreen : Screen("paymentMethodsSettingsScreen")
    data object AboutHaroShopScreen : Screen("aboutScreen")
    data object StoryScreen : Screen("storyScreen") {
        fun createRoute(
            index: Int
        ) = "${StoryScreen.route}/$index"
    }
}