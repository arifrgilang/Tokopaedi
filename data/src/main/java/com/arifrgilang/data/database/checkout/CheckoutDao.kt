package com.arifrgilang.data.database.checkout

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


/**
 * Created by arifrgilang on 4/26/2021
 */
@Dao
interface CheckoutDao {
    @Insert
    suspend fun insert(item: CheckoutDataModel)

    @Query("DELETE FROM CHECKOUT WHERE id = :checkoutId")
    suspend fun deleteWithId(checkoutId: Int)

    @Query("SELECT * FROM CHECKOUT WHERE USER_EMAIL = :userEmail")
    suspend fun getCheckoutWithEmail(userEmail: String): List<CheckoutDataModel>
}