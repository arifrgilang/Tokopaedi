package com.arifrgilang.data.database.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


/**
 * Created by arifrgilang on 4/26/2021
 */
@Dao
interface CartDao {
    @Insert
    suspend fun insert(item: CartDataModel)

    @Query("DELETE FROM CART WHERE id = :itemId")
    suspend fun deleteById(itemId: Int)

    @Query("DELETE FROM CART WHERE USER_EMAIL = :userEmail")
    suspend fun deleteByEmail(userEmail: String)

    @Query("SELECT * FROM CART WHERE USER_EMAIL = :userEmail")
    suspend fun getCartWithEmail(userEmail: String): List<CartDataModel>
}