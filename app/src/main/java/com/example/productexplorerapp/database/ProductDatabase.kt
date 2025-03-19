package com.example.productexplorerapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.productexplorerapp.database.ProductDao
import com.example.productexplorerapp.model.Converters
import com.example.productexplorerapp.model.ProductDto

@Database(entities = [ProductDto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class) // Apply the TypeConverter
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}