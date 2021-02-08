package com.hurriyet.basketapp.ui.listener

import android.view.View
import com.hurriyet.basketapp.model.Basket

interface RemoveOnClickListener {

    fun remove(view: View, basket: Basket)

}