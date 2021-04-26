package com.arifrgilang.data.database.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by arifrgilang on 4/25/2021
 */
@Entity(tableName = "USER")
data class UserDataModel(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "NAME") val name: String?,
    @ColumnInfo(name = "EMAIL") val email: String?
)