package com.hurriyet.basketapp.listener

import android.view.View
import com.hurriyet.basketapp.model.Basket

interface DecreaseBtnClickListener {

    fun decrease(view: View, basket: Basket)

}