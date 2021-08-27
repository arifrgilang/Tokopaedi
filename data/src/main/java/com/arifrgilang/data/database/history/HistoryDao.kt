package com.arifrgilang.data.database.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


/**
 * Created by arifrgilang on 4/26/2021
 */
@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(item: HistoryDataModel)

    @Query("SELECT * FROM HISTORY WHERE id = :historyId")
    fun getHistoryWithId(historyId: Int): Flow<HistoryDataModel>

    @Query("SELECT * FROM HISTORY WHERE USER_EMAIL = :userEmail")
    fun getHistoryWithEmail(userEmail: String): Flow<List<HistoryDataModel>>
}