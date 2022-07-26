package com.example.rickandmorty.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.getImage(url: String){
    Picasso.get().load(url).into(this)
}