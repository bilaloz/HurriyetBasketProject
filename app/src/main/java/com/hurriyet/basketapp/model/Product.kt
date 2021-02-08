package com.hurriyet.basketapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product(

    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?,
    @ColumnInfo(name = "price")
    @SerializedName("price")
    val price: String?,
    @ColumnInfo(name = "currency")
    @SerializedName("currency")
    val currency: String?,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val image: String?

) {
    @PrimaryKey(autoGenerate = true)
    var uuid = 0
}