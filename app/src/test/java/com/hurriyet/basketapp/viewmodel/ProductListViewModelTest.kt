package com.hurriyet.basketapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hurriyet.basketapp.MainCoroutineRule
import com.hurriyet.basketapp.getOrAwaitValueTest
import com.hurriyet.basketapp.respository.FakeRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ProductListViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var repository: FakeRepository


    @Before
    fun setup() {
        repository = FakeRepository()
    }


    @Test
    fun `is list not empty product`() {

        repository.productLiveData.value =
            listOf(Product(id = 3, price = "", currency = "", image = "", name = ""))

        val value = repository.getProductListFromApi().getOrAwaitValueTest()

        assertThat(value).isNotEmpty()

    }


    @Test
    fun `basket delete from db`() {

        val data = Basket(
            image = "",
            currency = "",
            name = "",
            id = 21,
            price = "",
            count = 2
        )

        repository.insertBasket(data)

        val value = repository.getBasketListFromApi().getOrAwaitValueTest()

        repository.deleteBasket(data)

        assertThat(value).isEmpty()

    }


    @Test
    fun `is not equal to data from db, if not save data`() {
        val data = Basket(
            image = "",
            currency = "",
            name = "",
            id = 21,
            price = "",
            count = 2
        )

        repository.insertBasket(
            data
        )

        val value = repository.getBasketListFromApi().getOrAwaitValueTest()

        assertThat(value).isNotEqualTo(data)

    }

    @Test
    fun `is list empty product`() {

        repository.productLiveData.value = null

        val value = repository.getProductListFromApi().getOrAwaitValueTest()

        assertThat(value).isNull()

    }


}