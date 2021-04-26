package com.arifrgilang.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arifrgilang.data.database.item.ItemDao
import com.arifrgilang.data.database.item.ItemDataModel
import com.arifrgilang.data.database.promo.ItemTypeConverter
import com.arifrgilang.data.database.promo.PromoDao
import com.arifrgilang.data.database.promo.PromoDataModel


/**
 * Created by arifrgilang on 4/25/2021
 */
@Database(
    entities = [ItemDataModel::class, PromoDataModel::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(ItemTypeConverter::class)
internal abstract class OpakuDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun promoDao(): PromoDao

    companion object{
        private var INSTANCE: OpakuDatabase? = null
        fun getDatabase(context: Context): OpakuDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OpakuDatabase::class.java,
                    "opaku_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                return instance
            }
        }
    }
}