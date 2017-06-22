package com.erostech.kiki.ui.adapters.delegates.mopub

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.erostech.kiki.R
import com.erostech.kiki.models.MoPubBannerAdCell
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.ui.adapters.delegates.ViewTypeDelegateAdapter
import com.erostech.kiki.util.inflate
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubView

/**
 * Created by erosgarciaponte on 22.06.17.
 */
class MoPubBannerAdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = MoPubBannerAdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as MoPubBannerAdViewHolder
        holder.bind(item as MoPubBannerAdCell)
    }

    inner class MoPubBannerAdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_mopub_banner)) {
        private val TAG = MoPubBannerAdViewHolder::class.java!!.getSimpleName()

        val mopubView : MoPubView

        init {
            mopubView = itemView.findViewById(R.id.mopub_banner_view) as MoPubView
        }

        fun bind(item: MoPubBannerAdCell) = with(itemView) {
            mopubView.adUnitId = item.adUnitId
            mopubView.loadAd()
            mopubView.bannerAdListener = object : MoPubView.BannerAdListener {
                override fun onBannerClicked(banner: MoPubView?) {
                    Log.d(TAG, "onBannerClicked")
                }

                override fun onBannerCollapsed(banner: MoPubView?) {
                    Log.d(TAG, "onBannerCollapsed")
                }

                override fun onBannerExpanded(banner: MoPubView?) {
                    Log.d(TAG, "onBannerExpanded")
                }

                override fun onBannerFailed(banner: MoPubView?, errorCode: MoPubErrorCode?) {
                    Log.d(TAG, "onBannerFailed: ${errorCode.toString()}")
                }

                override fun onBannerLoaded(banner: MoPubView?) {
                    Log.d(TAG, "onBannerLoaded")
                }
            }
        }
    }
}