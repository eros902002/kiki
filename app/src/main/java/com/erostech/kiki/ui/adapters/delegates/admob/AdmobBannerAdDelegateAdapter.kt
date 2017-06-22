package com.erostech.kiki.ui.adapters.delegates.admob

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.erostech.kiki.R
import com.erostech.kiki.models.AdmobBannerAdCell
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.ui.adapters.delegates.ViewTypeDelegateAdapter
import com.erostech.kiki.util.inflate
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

/**
 * Created by erosgarciaponte on 22.06.17.
 */
class AdmobBannerAdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = AdmobBannerAdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as AdmobBannerAdViewHolder
        holder.bind()
    }

    inner class AdmobBannerAdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_admob_banner)) {
        private val TAG = AdmobBannerAdViewHolder::class.java!!.getSimpleName()

        val adView : AdView

        init {
            adView = itemView.findViewById(R.id.admob_banner_view) as AdView
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
            adView.loadAd(adRequest)
        }
    }
}