package com.arifrgilang.data.database.promo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/25/2021
 */
@Dao
interface PromoDao {
    @Insert
    suspend fun insertAll(promos: List<PromoDataModel>)

    @Query("SELECT * FROM PROMO")
    suspend fun getAll(): List<PromoDataModel>

    @Query("SELECT * FROM PROMO WHERE id = :promoId")
    suspend fun getPromo(promoId: Int): PromoDataModel
}