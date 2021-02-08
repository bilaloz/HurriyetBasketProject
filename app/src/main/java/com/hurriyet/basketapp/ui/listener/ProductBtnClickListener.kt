package com.hurriyet.basketapp.ui.listener

import android.view.View
import com.hurriyet.basketapp.model.Product

interface ProductBtnClickListener {

    fun onUserClicked(view: View, product: Product)

}