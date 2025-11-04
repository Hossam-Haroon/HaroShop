package com.example.e_commerceapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.presentation.theme.BlueishWhite
import com.example.e_commerceapp.presentation.theme.LightBlue
import com.example.e_commerceapp.presentation.theme.raleWay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VouchersBottomSheet(
    vouchers:List<Voucher>,
    onDismissRequest:()->Unit,
    onApplyVoucher:(Voucher)->Unit
){
    ModalBottomSheet(onDismissRequest = { onDismissRequest() }) {
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box (
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(LightBlue),
                contentAlignment = Alignment.CenterStart
            ){
                Text(
                    text = "Active Vouchers",
                    fontFamily = raleWay,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn {
                items(vouchers){voucher ->
                    VoucherCardComponent(voucher = voucher) {
                        onApplyVoucher(voucher)
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}