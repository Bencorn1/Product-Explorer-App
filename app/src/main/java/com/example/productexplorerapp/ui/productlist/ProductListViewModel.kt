package com.example.productexplorerapp.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productexplorerapp.repository.ProductRepository
import com.example.productexplorerapp.utils.Resource
import com.example.productexplorerapp.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProductListViewModel @Inject constructor(private var repo: ProductRepository) : ViewModel() {

    private val _products = MutableStateFlow<Resource<List<Product>>>(Resource.Loading())
    val products: StateFlow<Resource<List<Product>>> get() = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            repo.getProducts().collect { result ->
                _products.value = result
            }
        }
    }

}
