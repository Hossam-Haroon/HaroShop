package com.example.e_commerceapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e_commerceapp.data.local.data.UserDbEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrentUser(user:UserDbEntity)

    @Query("SELECT * FROM current_user LIMIT 1")
    fun getCurrentUser(): Flow<UserDbEntity?>

    @Query("DELETE FROM current_user")
    suspend fun clearCurrentUserData()

}