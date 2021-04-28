package com.arifrgilang.data.database.history

import androidx.room.*
import com.arifrgilang.data.database.cart.CartDataModel
import com.arifrgilang.data.database.checkout.CheckoutHistoryTypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


/**
 * Created by arifrgilang on 4/26/2021
 */
@Entity(tableName = "HISTORY")
@TypeConverters(CheckoutHistoryTypeConverter::class)
data class HistoryDataModel (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "USER_EMAIL") val userEmail: String?,
    @ColumnInfo(name = "ITEMS") val items: List<CartDataModel> = listOf(),
)
