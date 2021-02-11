package com.hurriyet.basketapp.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.hurriyet.basketapp.data.local.roomdb.ProductDao
import com.hurriyet.basketapp.data.local.roomdb.ProductDatabase
import com.hurriyet.basketapp.model.Basket
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@SmallTest
@ExperimentalCoroutinesApi
class ProductDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ProductDatabase
    private lateinit var dao: ProductDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ProductDatabase::class.java
        ).allowMainThreadQueries()
            .build()
        dao = database.productDao()
    }


    @Test
    fun basketInsertTesting() = runBlockingTest {

        val modelBasket =
            Basket(id = 1, price = "10", currency = "tl", name = "sampuan", image = "", count = 3)
        dao.insertBasket(modelBasket)

        val listBasket = dao.getAllBasket()

        assertThat(listBasket).contains(modelBasket)

    }

    @Test
    fun basketRemoveTesting() = runBlockingTest {

        val modelBasket =
            Basket(id = 21, price = "10", currency = "tl", name = "sampuan", image = "", count = 3)
        dao.insertBasket(modelBasket)
        dao.deleteBasket(modelBasket)

        val listBasket = dao.getAllBasket()

        assertThat(listBasket).doesNotContain(modelBasket)

    }


    @After
    fun finish() {
        database.close()
    }


}