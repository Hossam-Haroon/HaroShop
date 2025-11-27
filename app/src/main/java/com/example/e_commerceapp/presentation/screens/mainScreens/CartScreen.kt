package com.example.e_commerceapp.presentation.screens.mainScreens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.Cart
import com.example.e_commerceapp.domain.model.Product
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.AddressBoxComponent
import com.example.e_commerceapp.presentation.components.AddressEditDialogComponent
import com.example.e_commerceapp.presentation.components.CartCheckOutBottomBar
import com.example.e_commerceapp.presentation.components.CartItemComponent
import com.example.e_commerceapp.presentation.components.DeleteItemDialogComponent
import com.example.e_commerceapp.presentation.components.MostPopularSectionComponent
import com.example.e_commerceapp.presentation.components.WishListComponent
import com.example.e_commerceapp.presentation.theme.DarkBlue
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.viewmodels.CartScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun CartScreen(rootNavController: NavController,innerNavController:NavController) {
    val cartViewModel: CartScreenViewModel = hiltViewModel()
    val popularProducts by cartViewModel.popularProducts.collectAsState()
    val likedProducts by cartViewModel.likedProducts.collectAsState()
    val cartProducts by cartViewModel.cartProducts.collectAsState()
    val userAddress by cartViewModel.userAddress.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteCartDialog by remember { mutableStateOf(false) }
    var editableAddress by remember { mutableStateOf(userAddress) }
    var showDeleteFavouriteProductDialog by remember {
        mutableStateOf(false)
    }
    var cartRequiredToBeDeleted by remember {
        mutableStateOf<Cart?>(null)
    }
    var favouriteProductRequiredToBeDeleted by remember {
        mutableStateOf<Product?>(null)
    }
    val context = LocalContext.current
    Scaffold(bottomBar = {
        CartCheckOutBottomBar(
            "Checkout",
            totalPrice.toString(),
            DarkBlue,
            cartProducts.isEmpty()
        ) {
            innerNavController.navigate(Screen.PaymentScreen.route)
        }
    }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cart",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(LightBlue),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = cartProducts.size.toString(),
                        fontFamily = raleWay,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            AddressBoxComponent(userAddress) {
                showDialog = true
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (cartProducts.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .height(350.dp)
                        .fillMaxWidth()
                ) {
                    items(cartProducts) { cartProduct ->
                        CartItemComponent(
                            viewModel = cartViewModel,
                            cartItem = cartProduct,
                            onNavigate = {
                                rootNavController.navigate(
                                    Screen.ProductScreen.createRouteForKnownProduct(
                                        cartProduct.productId,
                                        cartProduct.color,
                                        cartProduct.size,
                                        cartProduct.productAmount
                                    )
                                )
                            },
                            onDelete = {
                                cartRequiredToBeDeleted = cartProduct
                                showDeleteCartDialog = true
                            }
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.empty_cart),
                        contentDescription = null,
                        modifier = Modifier.size(134.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "From You Wishlist",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            if (likedProducts.isNotEmpty()) {
                LazyColumn(modifier = Modifier.height(280.dp)) {
                    items(likedProducts) { product ->
                        product?.let {
                            WishListComponent(
                                imageUrl = it.productImage,
                                product = product,
                                onNavigateClick = {
                                    rootNavController.navigate(
                                        Screen.ProductScreen.createRouteForKnownProduct(
                                            it.productId,
                                            0,
                                            "none",
                                            1
                                        )
                                    )
                                },
                                onCLick = {},
                                onDelete = {
                                    favouriteProductRequiredToBeDeleted = product
                                    showDeleteFavouriteProductDialog = true
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (popularProducts.isNotEmpty()) {
                MostPopularSectionComponent(popularProducts, rootNavController)
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (showDialog) {
                Dialog(onDismissRequest = { showDialog = false }) {
                    AddressEditDialogComponent(
                        editableAddress,
                        { editableAddress = it },
                        onConfirm = {
                            cartViewModel.updateUserAddress(editableAddress)
                            Toast.makeText(
                                context,
                                "address has been upgraded",
                                Toast.LENGTH_LONG
                            ).show()
                            showDialog = false
                        }
                    )
                }
            }
            if (showDeleteCartDialog) {
                Dialog(onDismissRequest = { showDeleteCartDialog = false }) {
                    DeleteItemDialogComponent(
                        itemName = "Cart",
                        onConfirm = {
                            cartRequiredToBeDeleted?.let { cartViewModel.deleteItemFromCart(it) }
                            Toast.makeText(
                                context,
                                "cart has been deleted",
                                Toast.LENGTH_LONG
                            ).show()
                            showDeleteCartDialog = false
                        },
                        onCancel = { showDeleteCartDialog = false }
                    )
                }
            }
            if (showDeleteFavouriteProductDialog) {
                Dialog(onDismissRequest = { showDeleteFavouriteProductDialog = false }) {
                    DeleteItemDialogComponent(
                        itemName = "Product",
                        onConfirm = {
                            favouriteProductRequiredToBeDeleted?.let {
                                cartViewModel.deleteFavouriteProduct(
                                    it.productId
                                )
                            }
                            Toast.makeText(
                                context,
                                "product has been deleted from favourite list",
                                Toast.LENGTH_LONG
                            ).show()
                            showDeleteFavouriteProductDialog = false
                        },
                        onCancel = { showDeleteFavouriteProductDialog = false }
                    )
                }
            }
        }
    }
}