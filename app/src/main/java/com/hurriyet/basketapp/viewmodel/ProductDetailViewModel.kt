package com.hurriyet.basketapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.model.Product
import com.hurriyet.basketapp.service.ProductAPIService
import com.hurriyet.basketapp.roomdb.ProductDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ProductDetailViewModel(application: Application) : BaseViewModel(application) {

    private val allBasket = MutableLiveData<List<Basket>>()
    val getBasketList: LiveData<List<Basket>> get() = allBasket

    private val status = MutableLiveData<Boolean?>()
    val getStatus: LiveData<Boolean?> get() = status


    private val loading = MutableLiveData<Boolean?>()
    val getLoading: LiveData<Boolean?> get() = loading

    private val error = MutableLiveData<Boolean>()
    val getError: LiveData<Boolean?> get() = error

    private val success = MutableLiveData<Boolean>()
    val getSuccess: LiveData<Boolean?> get() = success

    private val productsApiService = ProductAPIService()

    private val disposable = CompositeDisposable()


    fun getAllBasketsObservers(): MutableLiveData<List<Basket>> {
        return allBasket
    }

    private fun getAllBasket() {
        launch {
            val product = ProductDatabase(
                getApplication()
            ).productDao().getAllBasket()
            allBasket.value = product
        }
    }

    fun postDataFromAPI() {
        loading.value = true

        disposable.add(

            productsApiService.getData().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(t: List<Product>) {
                        success.value = true
                        loading.value = null

                    }

                    override fun onError(e: Throwable) {
                        loading.value = null
                        error.value = true
                        print(e.printStackTrace())
                    }

                })
        )


    }

    fun insertBasket(id: Int) {
        launch {
            val basketDao = ProductDatabase(
                getApplication()
            ).productDao()
            val productList = ProductDatabase(
                getApplication()
            ).productDao().getAllProduct()

            val basketList = basketDao.getAllBasket()

            var basketItem: Basket? = null

            basketItem = basketList.find { p -> p.id == id }

            if (basketItem == null) {

                val productItem = productList.find { p -> p.id == id }

                basketDao.insertBasket(
                    Basket(
                        id,
                        name = productItem?.name,
                        currency = productItem?.currency,
                        price = productItem?.price,
                        image = productItem?.image,
                        count = 1
                    )
                )
            }
            basketItem?.let {

                basketItem.count = basketItem.count.plus(1)

                basketDao.updateBasket(
                    basketItem
                )
            }
            getAllBasket()
        }
    }

    fun incrementBasket(basket: Basket) {

        launch {
            basket.let {
                val basketDao = ProductDatabase(
                    getApplication()
                ).productDao()

                basket.count = basket.count.plus(1)

                basketDao.updateBasket(
                    basket
                )
            }
            getAllBasket()
            status.value = true

        }

    }

    fun decreaseBasket(basket: Basket) {
        if (basket.count == 1)
            deleteBasket(basket)
        else {
            basket.let {
                val basketDao = ProductDatabase(
                    getApplication()
                ).productDao()

                basket.count = basket.count.plus(-1)

                launch {
                    basketDao.updateBasket(
                        basket
                    )
                }

                getAllBasket()
                status.value = true


            }


        }
    }

    fun updateBasket(basket: Basket) {
        launch {
            val basketDao = ProductDatabase(
                getApplication()
            ).productDao()
            basketDao.updateBasket(basket)
            getAllBasket()
        }
    }

    fun deleteBasket(basket: Basket) {
        launch {
            val basketDao = ProductDatabase(
                getApplication()
            ).productDao()
            basketDao.deleteBasket(basket)
            getAllBasket()
            status.value = true
        }
    }

    fun deleteBasketAll() {
        launch {
            val basketDao = ProductDatabase(
                getApplication()
            ).productDao()
            basketDao.deleteAllBasket()
            getAllBasket()
            status.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }


}