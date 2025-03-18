package com.example.productexplorerapp


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productexplorerapp.databinding.ItemProductBinding


class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.productName.text = product.name
        holder.binding.productPrice.text = product.price
        holder.binding.productImage.setImageResource(product.imageRes)

//        // Navigate to detail fragment when clicked
//        holder.itemView.setOnClickListener {
//            val action =
//                ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment()
//            it.findNavController().navigate(action)
//        }
    }

    override fun getItemCount() = productList.size
}