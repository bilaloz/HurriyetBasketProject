package com.hurriyet.basketapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Basket(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "price")
    val price: String?,
    @ColumnInfo(name = "currency")
    val currency: String?,
    @ColumnInfo(name = "image")
    val image: String?,
    @ColumnInfo(name = "count")
    var count: Int
)