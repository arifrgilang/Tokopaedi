package com.arifrgilang.data.database.item

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by arifrgilang on 4/25/2021
 */
@Entity(tableName = "ITEM")
data class ItemDataModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "ITEM_NAME") val itemName: String?,
    @ColumnInfo(name = "ITEM_CATEGORY") val itemCategory: String?,
    @ColumnInfo(name = "ITEM_VARIANT") val itemVariant: String?,
    @ColumnInfo(name = "ITEM_BRAND") val itemBrand: String?,
    @ColumnInfo(name = "ITEM_PRICE") val itemPrice: Long?,
    @ColumnInfo(name = "ITEM_DESC") val itemDesc: String,
    @ColumnInfo(name = "ITEM_PIC_URL") val itemPicUrl: String
)