package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.WishListProductsDao
import com.example.e_commerceapp.data.local.data.WishListProductDbEntity
import javax.inject.Inject

class LocalWishListProductsDataSource @Inject constructor(
    private val wishListProductsDao: WishListProductsDao
) {
    suspend fun insertOrUpdateFavouriteProduct(wishListProduct: WishListProductDbEntity) =
        wishListProductsDao.insertOrUpdate(wishListProduct)

    fun isProductLiked(productId:String) = wishListProductsDao.observeLikeState(productId)

    fun getAllFavouriteProductsIds() = wishListProductsDao.getAllFavouriteProductsIds()

}