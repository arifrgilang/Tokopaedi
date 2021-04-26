package com.arifrgilang.data.database.promo

import android.util.JsonReader
import androidx.room.*
import com.arifrgilang.data.database.item.ItemDataModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.StringReader


/**
 * Created by arifrgilang on 4/25/2021
 */
@Entity(tableName = "PROMO")
@TypeConverters(ItemTypeConverter::class)
data class PromoDataModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "PROMOTION_NAME") val promotionName: String?,
    @ColumnInfo(name = "CREATIVE_NAME") val creativeName: String?,
    @ColumnInfo(name = "CREATIVE_SLOT") val creativeSlot: String?,
    @ColumnInfo(name = "LOCATION_ID") val locationId: String?,
    @ColumnInfo(name = "ITEMS") val items: List<ItemDataModel> = listOf()
)

class ItemTypeConverter {
    private val type = Types.newParameterizedType(List::class.java, ItemDataModel::class.java)
    private val adapter: JsonAdapter<List<ItemDataModel>> = Moshi.Builder().build().adapter(type)

    @TypeConverter
    fun listToJson(value: List<ItemDataModel>?): String =
        adapter.toJson(value)

    @TypeConverter
    fun jsonToList(value: String?): List<ItemDataModel> =
        value?.let { adapter.fromJson(it) } ?: listOf()

}