package com.example.e_commerceapp.presentation.appNavigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.e_commerceapp.presentation.screens.AboutHaroShopScreen
import com.example.e_commerceapp.presentation.screens.AllCategoriesScreen
import com.example.e_commerceapp.presentation.screens.CategoryProductsScreen
import com.example.e_commerceapp.presentation.screens.ImageSearchScreenWrapper
import com.example.e_commerceapp.presentation.screens.ProductScreen
import com.example.e_commerceapp.presentation.screens.ReviewsScreen
import com.example.e_commerceapp.presentation.screens.StoriesScreen
import com.example.e_commerceapp.presentation.screens.signUpScreen.HelloScreen
import com.example.e_commerceapp.presentation.screens.introductionScreen.IntroductionScreen
import com.example.e_commerceapp.presentation.screens.logInScreen.LogInScreen
import com.example.e_commerceapp.presentation.screens.mainScreens.MainScreen
import com.example.e_commerceapp.presentation.screens.logInScreen.PasswordScreen
import com.example.e_commerceapp.presentation.screens.signUpScreen.ReadyScreen
import com.example.e_commerceapp.presentation.screens.signUpScreen.SignUpScreen
import com.example.e_commerceapp.presentation.viewmodels.AuthViewModel

@Composable
fun AppNavigation(navController: NavHostController, viewModel: AuthViewModel = hiltViewModel()){
    val userState = viewModel.getCurrentUser()
    val checkStartDestination = 
        if (userState == null) Screen.Introduction.route else Screen.MainScreen.route
    NavHost(navController = navController, startDestination = checkStartDestination) {
        composable(Screen.Introduction.route){
            IntroductionScreen(
                onNavigateToSignUp = { navController.navigate(Screen.SignUp.route) },
                onNavigateToLogIn = { navController.navigate(Screen.LogIn.route) }
            )
        }
        composable(Screen.SignUp.route){
            SignUpScreen{
                navController.navigate(Screen.Hello.route){
                    popUpTo(Screen.SignUp.route){inclusive = true}
                }
            }
        }
        composable(Screen.Hello.route){
            HelloScreen{
                navController.navigate(Screen.Ready.route){
                    popUpTo(Screen.Hello.route){inclusive = true}
                }
            }
        }
        composable(Screen.Ready.route){
            ReadyScreen{
                navController.navigate(Screen.MainScreen.route){
                    popUpTo(Screen.Ready.route){inclusive = true}
                }
            }
        }
        composable(route = Screen.LogIn.route){
            LogInScreen { name ->
                navController.navigate(Screen.Password.createRoute(name))
            }
        }
        composable(
            route = Screen.Password.route,
            arguments = listOf(navArgument("email"){type = NavType.StringType})
        ){backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            PasswordScreen(email ?: ""){
                navController.navigate(Screen.MainScreen.route){
                    popUpTo(Screen.Password.route){inclusive = true}
                }
            }
        }
        composable(Screen.MainScreen.route){
            MainScreen(navController)
        }
        composable(
            route = Screen.AllCategories.route
            ){
            AllCategoriesScreen(navController)
        }
        composable(
            route = "categoryProducts/{categoryName}",
            arguments = listOf(navArgument("categoryName"){type = NavType.StringType})
        ){backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryProductsScreen(categoryName,navController)
        }
        composable(
            route = "productScreen/{productId}/{colorOption}/{sizeOption}/{quantity}",
            arguments = listOf(
                navArgument("productId"){type = NavType.StringType},
                navArgument("colorOption"){type = NavType.LongType},
                navArgument("sizeOption"){
                    type = NavType.StringType
                    defaultValue = ""
                    nullable = true
                                         },
                navArgument("quantity"){type = NavType.IntType},
            )
        ){backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            val colorOption = backStackEntry.arguments?.getLong("colorOption") ?: 0
            val sizeOption = backStackEntry.arguments?.getString("sizeOption") ?: ""
            val quantity = backStackEntry.arguments?.getInt("quantity") ?: 1
            ProductScreen(productId,colorOption,sizeOption,quantity,navController)
        }
        composable(
            route = "reviewsScreen/{productId}",
            arguments = listOf(navArgument("productId"){type = NavType.StringType})
        ){backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ReviewsScreen(productId,navController)
        }
        composable(
            route  = "storyScreen/{index}",
            arguments = listOf(
                navArgument("index"){type = NavType.IntType}
            )
        ){backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            StoriesScreen(
                chosenIndex = index,
                rootNavController = navController
            ) {
                navController.popBackStack()
            }
        }
        composable(
            route = "imageSearchScreen"
        ){
            ImageSearchScreenWrapper()
        }
        composable("aboutScreen"){ AboutHaroShopScreen() }
    }
}