package com.hurriyet.basketapp.roomdb

import androidx.room.*
import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.model.Product

@Dao
interface ProductDao {

    @Insert
    suspend fun insertAll(vararg product: Product): List<Long>

    @Query("SELECT * FROM product")
    suspend fun getAllProduct(): List<Product>

    @Query("DELETE FROM product")
    suspend fun deleteAllProduct()

    @Query("SELECT * FROM basket")
    suspend fun getAllBasket(): List<Basket>

    @Delete(entity = Basket::class)
    suspend fun deleteBasket(basket: Basket)

    @Insert(entity = Basket::class)
    suspend fun insertBasket(basket: Basket)

    @Update(entity = Basket::class)
    suspend fun updateBasket(basket: Basket)

    @Query("DELETE FROM basket")
    suspend fun deleteAllBasket()


}