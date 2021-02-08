package com.hurriyet.basketapp.data.remote.service

import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.model.Product
import io.reactivex.Single
import retrofit2.http.*


interface ProductAPI {

    /*

        GET Listings: https://nonchalant-fang.glitch.me/listing
        POST Submit Order: https://nonchalant-fang.glitch.me/order
        Header Content-Type: “application/json”
        Parameters: [{"id":1,"amount":3}
     */

    @Headers("Content-type: application/json")
    @GET(value = "listing")
    fun getProductList(): Single<List<Product>>


    @FormUrlEncoded
    @POST("order")
    suspend fun postOrder(
        @Field("id") id: Int,
        @Field("amount") amount: Int
    ): Single<Basket>

}