package com.example.productexplorerapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.productexplorerapp.model.ProductDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<ProductDto>)

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductDto>>
}