package com.example.productexplorerapp.network

import com.example.productexplorerapp.model.Product
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProducts() : List<Product>
}