package com.example.e_commerceapp.data.local.dataSources

import com.example.e_commerceapp.data.local.dao.UserDao
import com.example.e_commerceapp.data.local.data.UserDbEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalUserDataSource @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun addCurrentUser(userDbEntity: UserDbEntity) = userDao.saveCurrentUser(userDbEntity)
    suspend fun deleteCurrentUser() = userDao.clearCurrentUserData()
    fun getCurrentUser() = userDao.getCurrentUser()
}