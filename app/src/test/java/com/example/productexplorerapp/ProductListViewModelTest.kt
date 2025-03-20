package com.example.productexplorerapp


import app.cash.turbine.test
import com.example.productexplorerapp.model.Product
import com.example.productexplorerapp.model.Rating
import com.example.productexplorerapp.repository.ProductRepository
import com.example.productexplorerapp.ui.productlist.ProductListViewModel
import com.example.productexplorerapp.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductViewModelTest {

    @MockK
    private lateinit var repository: ProductRepository

    @InjectMockKs
    private lateinit var viewModel: ProductListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set test dispatcher
        MockKAnnotations.init(this, relaxed = true)
        viewModel = ProductListViewModel(repository) // Manually initialize ViewModel
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset after test
    }

    @Test
    fun `fetchProducts should emit loading and then success`() = runTest {
        val mockProducts = listOf(
            Product(1, "Laptop", 23.0, "", "men's wears", "", Rating(4.5, 120)),
            Product(2, "Phone", 23.0, "", "men's wears", "", Rating(4.3, 90))
        )

        coEvery { repository.getProducts() } returns flow {
            emit(Resource.Loading()) // Emit loading state
            delay(2000) // Simulate network delay
            emit(Resource.Success(mockProducts)) // Emit success state
        }

        viewModel.fetchProducts()

        viewModel.products.test {
            assertTrue(awaitItem() is Resource.Loading) // Verify Loading state
            testScheduler.advanceTimeBy(2000) // Advance coroutine time
            assertEquals(Resource.Success(mockProducts), awaitItem()) // Verify Success state
            cancelAndIgnoreRemainingEvents()
        }
    }
}


