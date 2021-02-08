package com.hurriyet.basketapp.utils

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hurriyet.basketapp.R

fun ImageView.downloadImage(url: String?, progressDrawable: CircularProgressDrawable) {

    val requestOptions = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .into(this)
}

fun placeHolderCircleProgress(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 9.0f
        centerRadius = 38f
        start()
    }
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String?) {
    view.downloadImage(url, placeHolderCircleProgress(view.context))
}