package com.erostech.kiki.ui.adapters.delegates.mopub

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import com.erostech.kiki.R
import com.erostech.kiki.models.MoPubMediumAdCell
import com.erostech.kiki.ui.adapters.DestroyableView
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.ui.adapters.delegates.ViewTypeDelegateAdapter
import com.erostech.kiki.util.inflate
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubView

/**
 * Created by erosgarciaponte on 22.06.17.
 */
class MoPubMediumAdBannerDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = MoPubMediumAdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as MoPubMediumAdViewHolder
        holder.bind(item as MoPubMediumAdCell)
    }

    inner class MoPubMediumAdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_mopub_medium)), DestroyableView {
        private val TAG = MoPubMediumAdViewHolder::class.java!!.getSimpleName()

        val mopubView : MoPubView

        init {
            mopubView = itemView.findViewById(R.id.mopub_medium_view) as MoPubView
        }

        fun bind(item: MoPubMediumAdCell) = with(itemView) {
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

        override fun destroy() {
            mopubView.destroy()
        }
    }
}