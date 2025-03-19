package com.example.productexplorerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.productexplorerapp.databinding.FragmentProductDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: ProductDetailsFragmentArgs by navArgs()  // Get passed data

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        // Bind views and Set data
        binding.productName.text = args.product.title
        binding.productPrice.text = "$${args.product.price}"
        Timber.d("Product Details: ${args.product}")
        Timber.d("Product Details: ${args.product.price}")
        Timber.d("ProductDetailsFragment", "Image URL: ${args.product.image}")

        // Load image using Glide
        Glide.with(this)
            .load(args.product.image)
            .into(binding.productImage)

        binding.backIcon.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.buyNowButton.setOnClickListener {
            // Handle buy now button click
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}