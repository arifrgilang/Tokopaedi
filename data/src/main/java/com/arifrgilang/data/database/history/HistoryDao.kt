package com.arifrgilang.data.database.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.arifrgilang.data.database.checkout.CheckoutDataModel


/**
 * Created by arifrgilang on 4/26/2021
 */
@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(item: HistoryDataModel)

    @Query("SELECT * FROM HISTORY WHERE id = :historyId")
    suspend fun getHistoryWithId(historyId: Int): HistoryDataModel

    @Query("SELECT * FROM HISTORY WHERE USER_EMAIL = :userEmail")
    suspend fun getHistoryWithEmail(userEmail: String): List<HistoryDataModel>
}