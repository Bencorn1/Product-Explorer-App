package com.example.productexplorerapp

import android.content.SharedPreferences
import app.cash.turbine.test
import app.cash.turbine.testIn
import app.cash.turbine.turbineScope
import com.example.productexplorerapp.database.ProductDao
import com.example.productexplorerapp.model.Product
import com.example.productexplorerapp.model.ProductDto
import com.example.productexplorerapp.model.Rating
import com.example.productexplorerapp.model.RatingDto
import com.example.productexplorerapp.model.toProduct
import com.example.productexplorerapp.model.toRatingDto
import com.example.productexplorerapp.network.ProductService
import com.example.productexplorerapp.repository.ProductRepository
import com.example.productexplorerapp.utils.Resource
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

@ExperimentalCoroutinesApi
class ProductRepositoryTest {

    // Mock dependencies
    private lateinit var repository: ProductRepository
    private val service: ProductService = mockk()
    private val productDao: ProductDao = mockk()
    private val sharedPreferences: SharedPreferences = mockk()
    private val sharedPrefsEditor: SharedPreferences.Editor = mockk()

    // Custom JUnit rule for handling coroutines
    @get:Rule
    val coroutineRule = MainDispatcherRule()

    @Before
    fun setUp() {
        every { sharedPreferences.edit() } returns sharedPrefsEditor
        every { sharedPrefsEditor.putLong(any(), any()) } returns sharedPrefsEditor
        every { sharedPrefsEditor.apply() } just Runs

        repository = ProductRepository(service, productDao, sharedPreferences)
    }

    @Test
    fun `fetchProducts should fetch from API and cache in database`() = runTest {
        // Mock API response
        val apiProducts = listOf(
            Product(1, "Laptop", 23.0, "", "Electronics", "", Rating(4.5, 10)),
            Product(2, "Phone", 20.0, "", "Electronics", "", Rating(4.2, 5))
        )

        val productDtos = apiProducts.map {
            ProductDto(
                it.id,
                it.title,
                it.price,
                it.description,
                it.category,
                it.image,
                it.rating.toRatingDto()
            )
        }

        // Stale data scenario (force API refresh)
        every { sharedPreferences.getLong("last_fetch_time", any()) } returns 0L
        every { productDao.getAllProducts() } returns flowOf(emptyList()) // No cached data
        coEvery { service.getProducts() } returns apiProducts
        coEvery { productDao.insertProducts(productDtos) } just Runs

        // ✅ Use Turbine to consume emissions one by one
        repository.getProducts().test {
            // Assert Loading state is emitted first
            assertIs<Resource.Loading<*>>(awaitItem()) // ✅ Corrected Loading Assertion
            println("🔥 Emitted: Loading")

            // Assert Success state with expected API data
            val successState = awaitItem()
            assertIs<Resource.Success<List<Product>>>(successState)
            assertEquals(apiProducts, successState.data)
            println("✅ Emitted: Success with ${successState.data?.size} products")

            expectNoEvents() // Ensure no extra emissions
            cancelAndIgnoreRemainingEvents() // Stop flow collection
        }
    }


    @Test
    fun `fetchProducts should return cached data if API fails`() = runTest {
        // Mock cached data in Room
        val cachedProducts = listOf(
            ProductDto(1, "Laptop", 23.0, "", "Electronics", "", RatingDto(4.5, 10)),
            ProductDto(2, "Phone", 20.0, "", "Electronics", "", RatingDto(4.2, 5))
        )

        // Convert DTOs to domain models
        val expectedProducts = cachedProducts.map { it.toProduct() }

        // Simulate API failure
        every {
            sharedPreferences.getLong(
                "last_fetch_time",
                any()
            )
        } returns System.currentTimeMillis()
        every { productDao.getAllProducts() } returns flowOf(cachedProducts)
        coEvery { service.getProducts() } throws Exception("Network Error")

        val result = repository.getProducts().testIn(this)

        // Assert loading state is emitted first
        assertTrue(result.awaitItem() is Resource.Loading)

        // Assert error state is emitted due to API failure
        val errorState = result.awaitItem()
        assertTrue(errorState is Resource.Error)
        assertEquals("Expected error message", (errorState as Resource.Error).message)

        assertEquals("Network Error", errorState.message)

        // Assert local database data is emitted
        val successState = result.awaitItem()
        assertIs<Resource.Success<List<Product>>>(successState)
        assertEquals(expectedProducts, successState.data)

        result.cancel() // Cancel flow collection
    }

    @Test
    fun `fetchProducts should return cached data when API is unavailable`() = runTest {
        turbineScope {
            // Mock cached data
            val cachedProducts = listOf(
                ProductDto(1, "Laptop", 23.0, "", "Electronics", "", RatingDto(4.5, 10)),
                ProductDto(2, "Phone", 20.0, "", "Electronics", "", RatingDto(4.2, 5))
            )
            every { productDao.getAllProducts() } returns flowOf(cachedProducts)

            // Convert to domain model
            val expectedProducts = cachedProducts.map { it.toProduct() }

            // Test Flow emissions
            repository.getProducts().test {
                val emittedData = awaitItem()
                println("🔍 Expected: $expectedProducts")
                println("🔍 Actual: $emittedData")

                assertEquals(expectedProducts, emittedData) // ✅ Now it should match
                awaitComplete()
            }
        }
    }

}
