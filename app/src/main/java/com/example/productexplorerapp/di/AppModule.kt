package com.example.productexplorerapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.productexplorerapp.database.ProductDao
import com.example.productexplorerapp.database.ProductDatabase
import com.example.productexplorerapp.network.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ProductService {
        return retrofit.create(ProductService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context, ProductDatabase::class.java, "product_db")
            .fallbackToDestructiveMigration() // Handle version changes gracefully
            .build()
    }

    @Provides
    fun provideProductDao(database: ProductDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }
}
