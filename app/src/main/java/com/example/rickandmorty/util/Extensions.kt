package com.example.rickandmorty.util

import android.app.Activity
import android.util.DisplayMetrics
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.getImage(url: String){
    Picasso.get().load(url).into(this)
}

fun Activity.displayMetrics(): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics
}