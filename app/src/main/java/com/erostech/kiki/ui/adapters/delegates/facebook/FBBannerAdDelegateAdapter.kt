package com.erostech.kiki.ui.adapters.delegates.facebook

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import com.erostech.kiki.FACEBOOK_BANNER_PLACEMENT
import com.erostech.kiki.R
import com.erostech.kiki.models.FBBannerAdCell
import com.erostech.kiki.ui.adapters.DestroyableView
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.ui.adapters.delegates.ViewTypeDelegateAdapter
import com.erostech.kiki.util.inflate
import com.facebook.ads.*

/**
 * Created by erosgarciaponte on 23.06.17.
 */
class FBBannerAdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = FBBannerAdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as FBBannerAdViewHolder
        holder.bind(item as FBBannerAdCell)
    }

    inner class FBBannerAdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_facebook_banner)), DestroyableView {
        private val TAG = FBBannerAdViewHolder::class.java!!.getSimpleName()

        var adView : AdView? = null

        init {

        }

        fun bind(item: FBBannerAdCell) = with(itemView as FrameLayout) {
            adView = AdView(itemView.context, FACEBOOK_BANNER_PLACEMENT, AdSize.BANNER_HEIGHT_50)
            addView(adView)
            adView?.setAdListener(object : AdListener {
                override fun onError(ad: Ad?, adError: AdError?) {
                    Log.d(TAG, "onError: ${adError.toString()}")
                }

                override fun onAdClicked(ad: Ad?) {
                    Log.d(TAG, "onAdClicked")
                }

                override fun onAdLoaded(ad: Ad?) {
                    Log.d(TAG, "onAdLoaded")
                }

                override fun onLoggingImpression(ad: Ad?) {
                    Log.d(TAG, "onLoggingImpression")
                }
            })
            adView?.loadAd()
        }

        override fun destroy() {
            adView?.destroy()
            (itemView as FrameLayout).removeAllViews()
        }
    }
}