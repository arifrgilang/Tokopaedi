package com.arifrgilang.data.database.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass


/**
 * Created by arifrgilang on 4/26/2021
 */
@Entity(tableName = "CART")
@JsonClass(generateAdapter = true)
data class CartDataModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "USER_EMAIL") val userEmail: String?,
    @ColumnInfo(name = "ITEM_ID") val itemId: Int?,
    @ColumnInfo(name = "ITEM_NAME") val itemName: String?,
    @ColumnInfo(name = "ITEM_CATEGORY") val itemCategory: String?,
    @ColumnInfo(name = "ITEM_VARIANT") val itemVariant: String?,
    @ColumnInfo(name = "ITEM_BRAND") val itemBrand: String?,
    @ColumnInfo(name = "ITEM_PRICE") val itemPrice: Long?,
    @ColumnInfo(name = "CURRENCY") val currency: String?,
    @ColumnInfo(name = "QUANTITY") val quantity: Long?,
    @ColumnInfo(name = "ITEM_DESC") val itemDesc: String,
    @ColumnInfo(name = "ITEM_PIC_URL") val itemPicUrl: String
)