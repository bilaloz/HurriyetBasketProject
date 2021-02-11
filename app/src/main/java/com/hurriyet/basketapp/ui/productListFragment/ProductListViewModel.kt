package com.hurriyet.basketapp.ui.productListFragment

import com.hurriyet.basketapp.data.remote.service.ProductAPIService
import com.hurriyet.basketapp.data.local.roomdb.ProductDatabase
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hurriyet.basketapp.data.local.sharedPreferences.MySharedPreferences
import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.model.Product
import com.hurriyet.basketapp.ui.baseViewModel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class ProductListViewModel(application: Application) : BaseViewModel(application) {


    private val products = MutableLiveData<List<Product>>()
    val getProducts: LiveData<List<Product>> get() = products
    private val errorMessage = MutableLiveData<Boolean>()
    val getErrorMessage: LiveData<Boolean?> get() = errorMessage
    private val requestLoading = MutableLiveData<Boolean>()
    val getRequestLoading: LiveData<Boolean?> get() = requestLoading
    private val productsApiService = ProductAPIService()
    private val disposable = CompositeDisposable()
    private var mySharedPreferences =
        MySharedPreferences(
            getApplication()
        )
    private val refreshTime = 10 * 60 * 1000 * 1000 * 2000L


    fun refreshData() {
        val updateTime = mySharedPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQL()
        } else
            getDataFromAPI()

    }

    fun refreshFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromSQL() {

        launch {
            val product = ProductDatabase(
                getApplication()
            ).productDao().getAllProduct()
            showUser(product)
            Toast.makeText(getApplication(), "REQUEST SQL", Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromAPI() {
        Toast.makeText(getApplication(), "REQUEST API", Toast.LENGTH_LONG).show()

        requestLoading.value = true
        disposable.add(
            productsApiService.getData().
            subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(t: List<Product>) {
                        saveSQL(t)
                    }

                    override fun onError(e: Throwable) {
                        requestLoading.value = false
                        errorMessage.value = true
                        print(e.printStackTrace())
                    }

                })
        )
    }


    private fun showUser(productList: List<Product>) {
        errorMessage.value = false
        requestLoading.value = false
        products.value = productList
    }

    private fun saveSQL(productList: List<Product>) {

        launch {
            val dao = ProductDatabase(
                getApplication()
            ).productDao()
            dao.deleteAllProduct()
            val listLong = dao.insertAll(*productList.toTypedArray()) //individual
            for (i in productList.indices) {
                productList[i].uuid = listLong[i].toInt()
            }
            Toast.makeText(getApplication(), "SUCCESS SAVE SQL ", Toast.LENGTH_LONG).show()

            showUser(productList)
        }

        mySharedPreferences.addTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }


}