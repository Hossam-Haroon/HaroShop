package com.example.e_commerceapp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.e_commerceapp.presentation.components.ScreenHeadSectionComponent
import com.example.e_commerceapp.presentation.components.VoucherCardComponent
import com.example.e_commerceapp.presentation.viewmodels.VouchersViewModel
import kotlinx.coroutines.launch

@Composable
fun VouchersScreen(){
    val vouchersViewModel : VouchersViewModel = hiltViewModel()
    val userImageUrl by vouchersViewModel.imageUrl.collectAsState()
    val currentVouchers by vouchersViewModel.currentVouchers.collectAsState()

    LaunchedEffect(Unit) {
        launch { vouchersViewModel.getUserProfileById() }
        launch { vouchersViewModel.getAllUserVouchers() }
    }
    Column (
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(30.dp))
        ScreenHeadSectionComponent(userImageUrl,"Vouchers"){}
        Column (
            modifier = Modifier.fillMaxWidth(),

        ){
            for (voucher in currentVouchers){
                VoucherCardComponent(voucher = voucher) {}
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}