package com.hurriyet.basketapp.data.remote.service

import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.model.Product
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ProductAPIService {

    private val BASE_URL = "https://nonchalant-fang.glitch.me/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(ProductAPI::class.java)

    fun getData(): Single<List<Product>> {
        return api.getProductList()
    }

    fun postData(basket: Basket): Single<Basket> {
        return api.postOrder(id = basket.id, amount = basket.count)
    }

}