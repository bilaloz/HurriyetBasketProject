package com.hurriyet.basketapp.ui.productDetailFragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hurriyet.basketapp.R
import com.hurriyet.basketapp.ui.adapter.ProductDetailAdapter
import com.hurriyet.basketapp.ui.listener.DecreaseBtnClickListener
import com.hurriyet.basketapp.ui.listener.IncrementBtnClickListener
import com.hurriyet.basketapp.ui.listener.RemoveOnClickListener
import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.view.ProductDetailFragmentArgs
import com.hurriyet.basketapp.view.ProductDetailFragmentDirections
import kotlinx.android.synthetic.main.products_detail_page_fragment.*

class ProductDetailFragment : Fragment(R.layout.products_detail_page_fragment),
    DecreaseBtnClickListener, IncrementBtnClickListener, RemoveOnClickListener {

    private lateinit var viewModel: ProductDetailViewModel
    private val recyclerViewAdapter = ProductDetailAdapter(this, this, this)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProductDetailViewModel::class.java)
        //viewModel.getAllBasketsObservers()


        arguments?.let {
            val id = ProductDetailFragmentArgs.fromBundle(
                it
            ).productId
            viewModel.insertBasket(id)
        }



        productDetailRecyclerView.apply {
            layoutManager = LinearLayoutManager(view.context)
            productDetailRecyclerView.adapter = recyclerViewAdapter
        }

        productDetailContinueShopping.setOnClickListener {
            val action =
                ProductDetailFragmentDirections.actionProductDetailFragmentToProductListFragment()
            Navigation.findNavController(it).navigate(action)
        }

        productDetailPlaceHolder.setOnClickListener {
            viewModel.postDataFromAPI()
        }

        recyclerViewDecoration(view.context)

        observeLiveData(view)

    }

    private fun observeLiveData(view: View) {
        viewModel.getAllBasketsObservers().observe(viewLifecycleOwner, Observer { basket ->
            basket?.let {
                productDetailRecyclerView.visibility = View.VISIBLE
                recyclerViewAdapter.baskets = it
            }
        })
        viewModel.getStatus.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                Snackbar.make(view, "Succes", Snackbar.LENGTH_LONG).show()
            }
        })
        viewModel.getError.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                Snackbar.make(view, "Error", Snackbar.LENGTH_LONG).show()
            }
        })
        viewModel.getSuccess.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                productDetailRecyclerView.visibility = View.VISIBLE
                Snackbar.make(view, "Order Successful", Snackbar.LENGTH_LONG).show()
                val action =
                    ProductDetailFragmentDirections.actionProductDetailFragmentToProductListFragment()
                Navigation.findNavController(view).navigate(action)
                viewModel.deleteBasketAll()
            }
        })
        viewModel.getLoading.observe(viewLifecycleOwner, Observer { status ->
            status?.let {
                productDetailRecyclerView.visibility = View.GONE
                Snackbar.make(view, "Order Reading", Snackbar.LENGTH_LONG).show()
            }
            productDetailRecyclerView.visibility = View.VISIBLE


        })

    }

    private fun recyclerViewDecoration(context: Context?) {
        context?.let {
            val decoratorVertical = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            decoratorVertical.setDrawable(
                ContextCompat.getDrawable(
                    context,
                    R.drawable.divider_decoration
                )!!
            )
            productDetailRecyclerView.addItemDecoration(decoratorVertical)
        }
    }

    override fun decrease(view: View, basket: Basket) {
        viewModel.decreaseBasket(basket)
    }

    override fun increment(view: View, basket: Basket) {
        viewModel.incrementBasket(basket)
    }

    override fun remove(view: View, basket: Basket) {
        viewModel.deleteBasket(basket)
    }


}