package com.example.e_commerceapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceapp.core.Utils.BACK4APP_USER_CLASS
import com.example.e_commerceapp.core.Utils.USER_IMAGE_URL
import com.example.e_commerceapp.data.mappers.USER
import com.example.e_commerceapp.domain.model.User
import com.example.e_commerceapp.domain.model.Voucher
import com.example.e_commerceapp.domain.usecases.imageHandlerUseCases.GetImageIdForFetching
import com.example.e_commerceapp.domain.usecases.userUseCases.GetUserByIdUseCase
import com.example.e_commerceapp.domain.usecases.vouchersUseCases.GetAllUserVouchersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class VouchersViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getImageIdForFetching: GetImageIdForFetching,
    private val getAllUserVouchersUseCase: GetAllUserVouchersUseCase
):ViewModel() {
    private var _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl = _imageUrl.asStateFlow()
    private var _userData = MutableStateFlow<User?>(
        User(
            "", "", "",
            "", emptyList(), "", emptyList(), emptyList(), null,
            ""
        )
    )
    val userData = _userData.asStateFlow()
    private var _currentVouchers = MutableStateFlow<List<Voucher>>(emptyList())
    val currentVouchers = _currentVouchers.asStateFlow()
    fun getUserProfileById() {
        viewModelScope.launch {
            _userData.value = getUserByIdUseCase()
            val imageUrl = userData.value?.let {
                getImageIdForFetching(
                    it.userId,
                    USER,
                    USER_IMAGE_URL,
                    BACK4APP_USER_CLASS
                )
            }
            _imageUrl.value = imageUrl
        }
    }

    fun getAllUserVouchers(){
        viewModelScope.launch {
            getAllUserVouchersUseCase().catch {
                _currentVouchers.value = emptyList()
            }.collect{ vouchers->
                _currentVouchers.value = vouchers
            }
        }
    }
}