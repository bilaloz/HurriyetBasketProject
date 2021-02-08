package com.hurriyet.basketapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hurriyet.basketapp.model.Basket
import com.hurriyet.basketapp.model.Product

@Database(entities = [Product::class, Basket::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    //Singleton
    companion object {

        @Volatile
        private var instance: ProductDatabase? = null

        private val lock = Any()


        operator fun invoke(context: Context) = instance
            ?: synchronized(lock) {

            instance
                ?: createDatabase(
                    context
                ).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, ProductDatabase::class.java, "productdatabase"
        ).build()

    }

}