package com.hurriyet.basketapp.respository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.model.Product
import com.hurriyet.basketapp.roomdb.ProductDao


class FakeRepository {

    private val product = mutableListOf<Product>()
    private val bask = mutableListOf<Basket>()
    val productLiveData = MutableLiveData<List<Product>>()
    val basketLiveData = MutableLiveData<List<Basket>>()

    fun insertProduct(p: Product) {
        product.add(p)
        refreshDataAsProduct()
    }

    fun getProductListFromApi(): LiveData<List<Product>> {
        return productLiveData;
    }

    fun getBasketListFromApi(): LiveData<List<Basket>> {
        return basketLiveData;
    }

    fun insertBasket(basket: Basket) {
        bask.add(basket)
        refreshDataAsBasket()
    }

    fun deleteBasket(basket: Basket) {
        bask.remove(basket)
        refreshDataAsBasket()
    }


    private fun refreshDataAsBasket() {
        basketLiveData.value = bask
    }

    private fun refreshDataAsProduct() {
        productLiveData.value = product
    }

}