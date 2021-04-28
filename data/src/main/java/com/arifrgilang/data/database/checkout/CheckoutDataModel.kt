package com.arifrgilang.data.database.checkout

import androidx.room.*
import com.arifrgilang.data.database.cart.CartDataModel
import com.arifrgilang.data.database.item.ItemDataModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


/**
 * Created by arifrgilang on 4/26/2021
 */
@Entity(tableName = "CHECKOUT")
@TypeConverters(CheckoutHistoryTypeConverter::class)
data class CheckoutDataModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "USER_EMAIL") val userEmail: String?,
    @ColumnInfo(name = "ITEMS") val items: List<CartDataModel> = listOf(),
)

class CheckoutHistoryTypeConverter {
    private val type = Types.newParameterizedType(List::class.java, CartDataModel::class.java)
    private val adapter: JsonAdapter<List<CartDataModel>> = Moshi.Builder().build().adapter(type)

    @TypeConverter
    fun listToJson(value: List<CartDataModel>?): String =
        adapter.toJson(value)

    @TypeConverter
    fun jsonToList(value: String?): List<CartDataModel> =
        value?.let { adapter.fromJson(it) } ?: listOf()
}