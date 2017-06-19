package com.erostech.kiki.util

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.erostech.kiki.R
import com.squareup.picasso.Picasso

/**
 * Created by erosgarciaponte on 19.06.17.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImage(imageUrl: String) {
    if (!TextUtils.isEmpty(imageUrl)) {

    }
}