package com.erostech.kiki.util

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.GenericRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.StreamEncoder
import com.caverock.androidsvg.SVG
import com.erostech.kiki.R
import com.squareup.picasso.Picasso
import java.io.InputStream
import com.erostech.kiki.util.svg.SvgSoftwareLayerSetter
import com.erostech.kiki.util.svg.SvgDecoder
import com.bumptech.glide.load.resource.file.FileToStreamDecoder
import com.erostech.kiki.util.svg.SvgDrawableTranscoder


/**
 * Created by erosgarciaponte on 19.06.17.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun ImageView.loadImage(imageUrl: String) {
    if (!TextUtils.isEmpty(imageUrl)) {
        val requestBuilder = Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri::class.java, context), InputStream::class.java)
                .from(Uri::class.java)
                .`as`(SVG::class.java)
                .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
                .sourceEncoder(StreamEncoder())
                .cacheDecoder(FileToStreamDecoder(SvgDecoder()))
                .decoder(SvgDecoder())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .listener(SvgSoftwareLayerSetter())
        val uri = Uri.parse(imageUrl)
        requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .load(uri)
                .into(this)
    }
}

fun View.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}