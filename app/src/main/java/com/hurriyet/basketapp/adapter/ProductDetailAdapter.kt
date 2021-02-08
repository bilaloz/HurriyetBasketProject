package com.hurriyet.basketapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hurriyet.basketapp.R
import com.hurriyet.basketapp.databinding.ProductsDetailItemBinding
import com.hurriyet.basketapp.listener.DecreaseBtnClickListener
import com.hurriyet.basketapp.listener.IncrementBtnClickListener
import com.hurriyet.basketapp.listener.RemoveOnClickListener
import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.model.Product

class ProductDetailAdapter(
    private val decreaseBtnClickListener: DecreaseBtnClickListener,
    private val incrementBtnClickListener: IncrementBtnClickListener,
    private val removeOnClickListener: RemoveOnClickListener
) :
    RecyclerView.Adapter<ProductDetailAdapter.ProductDetailViewHolder>() {


    class ProductDetailViewHolder(var view: ProductsDetailItemBinding) :
        RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ProductsDetailItemBinding>(
            inflater,
            R.layout.products_detail_item,
            parent,
            false
        )
        return ProductDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return baskets.size
    }

    override fun onBindViewHolder(holder: ProductDetailViewHolder, position: Int) {
        holder.view.basketModel = baskets[position]
        holder.view.increment = incrementBtnClickListener
        holder.view.decrease = decreaseBtnClickListener
        holder.view.remove = removeOnClickListener
    }


    var baskets: List<Basket>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    private val diffUtil = object : DiffUtil.ItemCallback<Basket>() {
        override fun areItemsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Basket, newItem: Basket): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

}