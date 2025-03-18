package com.example.productexplorerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.productexplorerapp.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val productList = listOf(
        Product("Smartphone", "$699", R.drawable.smartphone),
        Product("Laptop", "$1299", R.drawable.laptop),
        Product("Headphones", "$199", R.drawable.headphones),
        Product("Smartphone", "$699", R.drawable.smartphone),
        Product("Laptop", "$1299", R.drawable.laptop),
        Product("Headphones", "$199", R.drawable.headphones),
        Product("Smartphone", "$699", R.drawable.smartphone),
        Product("Laptop", "$1299", R.drawable.laptop),
        Product("Headphones", "$199", R.drawable.headphones),
        Product("Smartphone", "$699", R.drawable.smartphone),
        Product("Laptop", "$1299", R.drawable.laptop),
        Product("Headphones", "$199", R.drawable.headphones),
        Product("Smartphone", "$699", R.drawable.smartphone),
        Product("Laptop", "$1299", R.drawable.laptop),
        Product("Headphones", "$199", R.drawable.headphones),
        Product("Smartphone", "$699", R.drawable.smartphone),
        Product("Laptop", "$1299", R.drawable.laptop),
        Product("Headphones", "$199", R.drawable.headphones),
        Product("Smartphone", "$699", R.drawable.smartphone),
        Product("Laptop", "$1299", R.drawable.laptop),
        Product("Headphones", "$199", R.drawable.headphones)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        val adapter = ProductAdapter(productList)
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}