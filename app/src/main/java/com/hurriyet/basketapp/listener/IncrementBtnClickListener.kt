package com.hurriyet.basketapp.listener

import android.view.View
import com.hurriyet.basketapp.model.Basket

interface IncrementBtnClickListener {

    fun increment(view: View, basket: Basket)

}