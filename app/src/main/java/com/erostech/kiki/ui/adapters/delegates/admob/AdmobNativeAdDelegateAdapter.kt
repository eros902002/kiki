package com.erostech.kiki.ui.adapters.delegates.admob

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.erostech.kiki.R
import com.erostech.kiki.models.AdmobBannerAdCell
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.ui.adapters.delegates.ViewTypeDelegateAdapter
import com.erostech.kiki.util.inflate
import com.google.android.gms.ads.*

/**
 * Created by erosgarciaponte on 22.06.17.
 */
class AdmobNativeAdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = AdmobNativeAdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AdmobNativeAdViewHolder
        holder.bind()
    }

    inner class AdmobNativeAdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_admob_native)) {
        private val TAG = AdmobNativeAdViewHolder::class.java!!.getSimpleName()

        val adView : NativeExpressAdView

        init {
            adView = itemView.findViewById(R.id.admob_native_view) as NativeExpressAdView
        }

        fun bind() = with(itemView) {
            adView.adListener = object : AdListener() {
                override fun onAdClicked() {
                    Log.d(TAG, "onAdClicked")
                }

                override fun onAdClosed() {
                    Log.d(TAG, "onAdClosed")
                }

                override fun onAdFailedToLoad(errorCode: Int) {
                    Log.d(TAG, "onAdFailedToLoad")
                }

                override fun onAdImpression() {
                    Log.d(TAG, "onAdImpression")
                }

                override fun onAdLeftApplication() {
                    Log.d(TAG, "onAdLeftApplication")
                }

                override fun onAdLoaded() {
                    Log.d(TAG, "onAdLoaded")
                }

                override fun onAdOpened() {
                    Log.d(TAG, "onAdOpened")
                }
            }
            val adRequest = AdRequest.Builder().build()
            adView.videoOptions = VideoOptions.Builder().setStartMuted(true).build()
            adView.loadAd(adRequest)
        }
    }
}