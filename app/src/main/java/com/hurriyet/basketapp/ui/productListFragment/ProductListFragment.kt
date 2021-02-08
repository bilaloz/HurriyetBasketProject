package com.hurriyet.basketapp.ui.productListFragment

import android.content.Context
import com.hurriyet.basketapp.ui.adapter.ProductAdapter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hurriyet.basketapp.R
import kotlinx.android.synthetic.main.products_page_fragment.*


class ProductListFragment : Fragment(R.layout.products_page_fragment) {

    private lateinit var viewModel: ProductListViewModel
    private val userAdapter = ProductAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)
        viewModel.refreshData()
        productPageRecyclerView.layoutManager = GridLayoutManager(context, 2)
        productPageRecyclerView.adapter = userAdapter
        recyclerViewDecoration(view.context)
        swipeRefreshLayout.setOnRefreshListener {
            productPageRecyclerView.visibility = View.GONE
            errorMessage.visibility = View.GONE
            productLoading.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
            viewModel.refreshFromAPI()
        }
        observeLiveData(view.context)
    }

    private fun recyclerViewDecoration(context: Context?) {
        context?.let {
            val decoratorVertical = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            val decoratorHorizontal = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
            decoratorVertical.setDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.divider_decoration
                )!!
            )
            decoratorHorizontal.setDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.divider_decoration
                )!!
            )
            productPageRecyclerView.addItemDecoration(decoratorHorizontal)
            productPageRecyclerView.addItemDecoration(decoratorVertical)
        }
    }

    private fun observeLiveData(contexts: Context) {
        viewModel.getProducts.observe(viewLifecycleOwner, Observer { product ->
            product?.let {
                productPageRecyclerView.visibility = View.VISIBLE
                userAdapter.products = it
            }
        })

        viewModel.getErrorMessage.observe(viewLifecycleOwner, Observer { errors ->
            errors?.let {
                if (it) {
                    errorMessage.visibility = View.VISIBLE
                } else
                    errorMessage.visibility = View.GONE
            }
        })

        viewModel.getRequestLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    productLoading.visibility = View.VISIBLE
                    errorMessage.visibility = View.GONE
                    productPageRecyclerView.visibility = View.GONE
                } else {
                    productLoading.visibility = View.GONE
                    productPageRecyclerView.visibility = View.GONE
                }
            }
        })
    }

}