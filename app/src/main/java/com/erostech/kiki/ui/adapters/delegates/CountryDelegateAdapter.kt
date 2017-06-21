package com.erostech.kiki.ui.adapters.delegates

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.StreamEncoder
import com.bumptech.glide.load.resource.file.FileToStreamDecoder
import com.caverock.androidsvg.SVG
import com.erostech.kiki.R
import com.erostech.kiki.models.Country
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.util.inflate
import com.erostech.kiki.util.loadImage
import com.erostech.kiki.util.svg.SvgDecoder
import com.erostech.kiki.util.svg.SvgDrawableTranscoder
import com.erostech.kiki.util.svg.SvgSoftwareLayerSetter

import kotlinx.android.synthetic.main.item_country.view.*
import java.io.InputStream

/**
 * Created by erosgarciaponte on 19.06.17.
 */
class CountryDelegateAdapter(val viewActions: onViewSelectedListener) : ViewTypeDelegateAdapter {
    interface onViewSelectedListener {
        fun onItemSelected(country: Country)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = CountryViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as CountryViewHolder
        holder.bind(item as Country)
    }

    inner class CountryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_country)) {

        fun bind(item: Country) = with(itemView) {
            name_view.setText(item.name!!)

            if (item.flag != null) {
                loadImage(flag_view, item.flag)
            }

            if (item.region != null) {
                region_view.setText(item.region)
            }

            super.itemView.setOnClickListener {
                viewActions.onItemSelected(item)
            }
        }

        private fun loadImage(imageView: ImageView, url: String) {
            if (!TextUtils.isEmpty(url)) {
                val requestBuilder = Glide.with(imageView.context)
                        .using(Glide.buildStreamModelLoader(Uri::class.java, imageView.context), InputStream::class.java)
                        .from(Uri::class.java)
                        .`as`(SVG::class.java)
                        .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
                        .sourceEncoder(StreamEncoder())
                        .cacheDecoder(FileToStreamDecoder(SvgDecoder()))
                        .decoder(SvgDecoder())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .listener(SvgSoftwareLayerSetter())
                val uri = Uri.parse(url)
                requestBuilder.diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .load(uri)
                        .into(imageView)
            }
        }
    }
}