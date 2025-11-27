package com.example.e_commerceapp.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.e_commerceapp.R
import com.example.e_commerceapp.domain.model.OrderAddress
import com.example.e_commerceapp.presentation.appNavigation.Screen
import com.example.e_commerceapp.presentation.components.AddressBoxComponent
import com.example.e_commerceapp.presentation.components.AddressEditDialogComponent
import com.example.e_commerceapp.presentation.components.CartCheckOutBottomBar
import com.example.e_commerceapp.presentation.components.ContactInformationSectionComponent
import com.example.e_commerceapp.presentation.components.EmailAndPhoneNumberUpdateDialogComponent
import com.example.e_commerceapp.presentation.components.FailedPaymentDialogComponent
import com.example.e_commerceapp.presentation.components.ItemsCountAndVoucherComponent
import com.example.e_commerceapp.presentation.components.PaymentCardsBottomSheet
import com.example.e_commerceapp.presentation.components.PaymentItemComponent
import com.example.e_commerceapp.presentation.components.PaymentProgressDialogComponent
import com.example.e_commerceapp.presentation.components.ShippingOptionsComponent
import com.example.e_commerceapp.presentation.components.SucceededPaymentDialogComponent
import com.example.e_commerceapp.presentation.components.VouchersBottomSheet
import com.example.e_commerceapp.presentation.theme.raleWay
import com.example.e_commerceapp.presentation.utils.Utils.EXPRESS_PAYMENT
import com.example.e_commerceapp.presentation.utils.Utils.EXPRESS_SHIPPING_DURATION
import com.example.e_commerceapp.presentation.utils.Utils.STANDARD_PAYMENT
import com.example.e_commerceapp.presentation.utils.Utils.STANDARD_SHIPPING_DURATION
import com.example.e_commerceapp.presentation.viewmodels.ContactInformationViewModel
import com.example.e_commerceapp.presentation.viewmodels.PaymentProcessViewModel
import com.example.e_commerceapp.presentation.viewmodels.PaymentScreenViewModel
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import kotlinx.coroutines.launch

@Composable
fun PaymentScreen(rootNavController: NavController,innerNavController: NavController) {
    val paymentScreenViewModel: PaymentScreenViewModel = hiltViewModel()
    val paymentProcessViewModel: PaymentProcessViewModel = hiltViewModel()
    val contactInformationViewModel: ContactInformationViewModel = hiltViewModel()
    val userAddress by contactInformationViewModel.userAddress.collectAsState()
    val userPhoneNumber by contactInformationViewModel.userPhoneNumber.collectAsState()
    val userEmail by contactInformationViewModel.userEmail.collectAsState()
    val cartProducts by paymentScreenViewModel.cartProducts.collectAsState()
    val currentVouchers by paymentScreenViewModel.currentVouchers.collectAsState()
    val totalFinalPrice by paymentScreenViewModel.totalFinalPrice.collectAsState()
    val selectedShippingOption by paymentScreenViewModel.selectedShippingOption.collectAsState()
    val standardPrice by paymentScreenViewModel.standardPrice.collectAsState()
    val expressPrice by paymentScreenViewModel.expressPrice.collectAsState()
    val selectedVoucher by paymentScreenViewModel.selectedVoucher.collectAsState()
    val savedCards by paymentProcessViewModel.savedCards.collectAsState()
    val selectedCardId by paymentProcessViewModel.selectedCardId.collectAsState()
    val setupSecret by paymentProcessViewModel.setupIntentClientSecret.collectAsState()
    val confirmationSecret by paymentProcessViewModel.paymentConfirmationSecret.collectAsState()
    val paymentResult by paymentProcessViewModel.paymentResult.collectAsState()
    val isLoading by paymentProcessViewModel.isLoading.collectAsState()
    val onPaymentFailed by paymentProcessViewModel.onPaymentFailed.collectAsState()
    val onPaymentSucceeded by paymentProcessViewModel.onPaymentSucceeded.collectAsState()
    val context = LocalContext.current
    var editableAddress by remember { mutableStateOf(userAddress) }
    var showAddressDialog by remember { mutableStateOf(false) }
    var showPhoneNumberAndEmailDialog by remember { mutableStateOf(false) }
    var showVouchersBottomSheet by remember { mutableStateOf(false) }
    var showPaymentCardsBottomSheet by remember { mutableStateOf(false) }
    var editablePhoneNumber by remember { mutableStateOf(userPhoneNumber) }
    var editableEmail by remember { mutableStateOf(userEmail) }
    val paymentSheet = rememberPaymentSheet{result ->
        when(result){
            PaymentSheetResult.Canceled -> {
                Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
            }
            PaymentSheetResult.Completed -> {
                Toast.makeText(context, "Payment Successful!", Toast.LENGTH_SHORT).show()
                paymentProcessViewModel.loadSavedCards()
            }
            is PaymentSheetResult.Failed -> {
                Toast.makeText(
                    context,
                    "Failed: ${result.error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        paymentProcessViewModel.onPaymentFlowFinished()
    }
    LaunchedEffect (userAddress,userPhoneNumber,userEmail){
        editableAddress = userAddress
        editableEmail = userEmail
        editablePhoneNumber = userPhoneNumber
    }
    LaunchedEffect(setupSecret) {
        setupSecret?.let {
            paymentSheet.presentWithSetupIntent(it)
        }
    }
    LaunchedEffect(confirmationSecret) {
        confirmationSecret?.let {
            paymentSheet.presentWithPaymentIntent(it)
        }
    }
    LaunchedEffect(paymentResult) {
        paymentResult?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    Scaffold(
        bottomBar = {
            CartCheckOutBottomBar(
                productState = "Pay",
                totalPrice = totalFinalPrice.toString(),
                suitableColor = Color.Black,
                cartIsEmpty = cartProducts.isEmpty()
            ) {
                val orderAddress = OrderAddress(
                    name = "hosam",
                    address = userAddress
                )
                paymentProcessViewModel.onPayClicked(
                    (totalFinalPrice*100).toInt(),
                    totalFinalPrice,
                    selectedShippingOption,
                    orderAddress,
                    cartProducts
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 10.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Payment",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            AddressBoxComponent(userAddress) {
                showAddressDialog = true
            }
            Spacer(modifier = Modifier.height(10.dp))
            ContactInformationSectionComponent(userPhoneNumber, userEmail) {
                showPhoneNumberAndEmailDialog = true
            }
            Spacer(modifier = Modifier.height(20.dp))
            ItemsCountAndVoucherComponent(
                selectedVoucher,
                cartProducts.size.toString(),
                onVoucherClick = {
                    showVouchersBottomSheet = true
                },
                onVoucherCancel = {
                    paymentScreenViewModel.resetPriceAfterCancelingVoucher()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                for (product in cartProducts) {
                    PaymentItemComponent(
                        product.productImage,
                        product.productAmount.toString(),
                        product.description,
                        product.productPrice.toString()
                    ) {
                        rootNavController.navigate(
                            Screen.ProductScreen.createRouteForKnownProduct(
                                product.productId,
                                product.color,
                                product.size,
                                product.productAmount
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Shipping Options",
                fontFamily = raleWay,
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            ShippingOptionsComponent(
                selectedShippingOption == STANDARD_PAYMENT,
                STANDARD_PAYMENT,
                STANDARD_SHIPPING_DURATION,
                standardPrice.toString()
            ) {
                paymentScreenViewModel.applyShippingOption(STANDARD_PAYMENT)
            }
            Spacer(modifier = Modifier.height(10.dp))
            ShippingOptionsComponent(
                selectedShippingOption == EXPRESS_PAYMENT,
                EXPRESS_PAYMENT,
                EXPRESS_SHIPPING_DURATION,
                expressPrice.toString()
            ) {
                paymentScreenViewModel.applyShippingOption(EXPRESS_PAYMENT)
            }
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Payment Method",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp
                )
                Image(
                    painter = painterResource(id = R.drawable.pen_button),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            showPaymentCardsBottomSheet = true
                        }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    if (showAddressDialog) {
        Dialog(onDismissRequest = { showAddressDialog = false }) {
            AddressEditDialogComponent(
                editableAddress,
                { editableAddress = it },
                onConfirm = {
                    contactInformationViewModel.updateUserAddress(editableAddress)
                    Toast.makeText(
                        context,
                        "address has been upgraded",
                        Toast.LENGTH_LONG
                    ).show()
                    showAddressDialog = false
                }
            )
        }
    }
    if (showPhoneNumberAndEmailDialog) {
        Dialog(onDismissRequest = { showPhoneNumberAndEmailDialog = false }) {
            EmailAndPhoneNumberUpdateDialogComponent(
                email = editableEmail,
                phoneNumber = editablePhoneNumber,
                onEmailChange = { editableEmail = it },
                onPhoneNumberChange = { editablePhoneNumber = it }
            ) {
                contactInformationViewModel.updateUserContactInformation(
                    editableEmail,
                    editablePhoneNumber
                )
                Toast.makeText(
                    context,
                    "contact information has been upgraded",
                    Toast.LENGTH_LONG
                ).show()
                showPhoneNumberAndEmailDialog = false
            }
        }
    }
    if (showVouchersBottomSheet) {
        VouchersBottomSheet(
            currentVouchers,
            onDismissRequest = {
                showVouchersBottomSheet = false
            },
            onApplyVoucher = { voucher ->
                paymentScreenViewModel.applyVoucher(voucher)
                showVouchersBottomSheet = false
            }
        )
    }
    if (showPaymentCardsBottomSheet){
        PaymentCardsBottomSheet(
            cardList = savedCards,
            isCardSelected = { cardId -> selectedCardId == cardId },
            onDismissRequest = { showPaymentCardsBottomSheet = false },
            onCardClick = {card ->
                paymentProcessViewModel.onCardSelected(card.id)
                showPaymentCardsBottomSheet = false
            },
            onAddCardClicked = {
                paymentProcessViewModel.onAddNewCardClicked()
            }
        )
    }
    if (isLoading){
        Dialog(onDismissRequest = {  }) {
            PaymentProgressDialogComponent()
        }
    }
    if (onPaymentFailed){
        Dialog(onDismissRequest = { paymentProcessViewModel.dismissOnPaymentFailedDialog() }) {
            FailedPaymentDialogComponent{
                val orderAddress = OrderAddress(
                    name = "hosam",
                    address = userAddress
                )
                paymentProcessViewModel.onPayClicked(
                    (totalFinalPrice*100).toInt(),
                    totalFinalPrice,
                    selectedShippingOption,
                    orderAddress,
                    cartProducts
                )
            }
        }
    }
    if (onPaymentSucceeded){
        Dialog(onDismissRequest = {  }) {
            SucceededPaymentDialogComponent{
                paymentProcessViewModel.dismissOnPaymentSucceededDialog()
                innerNavController.popBackStack()
                innerNavController.navigate(Screen.ToReceiveScreen.route)
            }
        }
    }
}