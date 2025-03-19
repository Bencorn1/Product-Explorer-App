package com.example.productexplorerapp.repository

import android.content.SharedPreferences
import com.example.productexplorerapp.database.ProductDao
import com.example.productexplorerapp.model.Product
import com.example.productexplorerapp.model.ProductDto
import com.example.productexplorerapp.model.toProduct
import com.example.productexplorerapp.model.toRatingDto
import com.example.productexplorerapp.network.ProductService
import com.example.productexplorerapp.utils.Resource
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val service: ProductService,
    private val productDao: ProductDao,
    private val sharedPreferences: SharedPreferences
) {

    suspend fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading())

        val lastFetchedTime = sharedPreferences.getLong("last_fetch_time", 0)
        val currentTime = System.currentTimeMillis()
        val isDataStale = (currentTime - lastFetchedTime) > 24 * 60 * 60 * 1000 // 24 hours

        if (isDataStale || isDatabaseEmpty()) {
            try {
                val response = service.getProducts()

                // Convert API response (Product) to Database model (ProductDto)
                val productDtos = response.map { product ->
                    ProductDto(
                        product.id,
                        product.title,
                        product.price,
                        product.description,
                        product.category,
                        product.image,
                        product.rating.toRatingDto()
                    )
                }

                productDao.insertProducts(productDtos) // Cache in Room
                sharedPreferences.edit().putLong("last_fetch_time", currentTime).apply()

                emit(Resource.Success(response)) // Return API response
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "An error occurred"))

                // Fallback to local database if API fails
                emitAll(productDao.getAllProducts().map { productDtos ->
                    Resource.Success(productDtos.map { it.toProduct() }) // Convert DB to API model
                })
            }
        } else {
            // Return cached data from database
            emitAll(productDao.getAllProducts().map { productDtos ->
                Resource.Success(productDtos.map { it.toProduct() })
            })
        }
    }

    private suspend fun isDatabaseEmpty(): Boolean {
        return productDao.getAllProducts().firstOrNull().isNullOrEmpty()
    }
}
