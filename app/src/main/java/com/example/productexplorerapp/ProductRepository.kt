package com.example.productexplorerapp

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private var service: ProductService) {

    suspend fun getProducts(): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading()) // Emit loading state
        try {
            val response = service.getProducts()
            emit(Resource.Success(response)) // Emit success with data
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An error occurred")) // Emit error
        }
    }
}
