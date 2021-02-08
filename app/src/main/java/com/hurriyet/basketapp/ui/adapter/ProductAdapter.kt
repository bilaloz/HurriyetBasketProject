package com.hurriyet.basketapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hurriyet.basketapp.R
import com.hurriyet.basketapp.databinding.ProductsItemBinding
import com.hurriyet.basketapp.ui.listener.ProductBtnClickListener
import com.hurriyet.basketapp.model.Product
import com.hurriyet.basketapp.ui.productListFragment.ProductListFragmentDirections

class ProductAdapter() :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(),
    ProductBtnClickListener {
    class ProductViewHolder(var view: ProductsItemBinding) :
        RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ProductsItemBinding>(
            inflater,
            R.layout.products_item,
            parent,
            false
        )
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        holder.view.productModel = products[position]
        holder.view.listener = this

    }

    var products: List<Product>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    private val diffUtil = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)



    override fun onUserClicked(view: View, product: Product) {


        if (product.id != null) {
            val action =
                ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                    productId = product.id
                )
            Navigation.findNavController(view).navigate(action)
        }

    }

}