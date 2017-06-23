package com.erostech.kiki.ui.adapters.delegates.mopub

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.*
import com.erostech.kiki.R
import com.erostech.kiki.models.MoPubNativeAdCell
import com.erostech.kiki.ui.adapters.DestroyableView
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.ui.adapters.delegates.ViewTypeDelegateAdapter
import com.erostech.kiki.util.inflate
import com.mopub.nativeads.*

/**
 * Created by erosgarciaponte on 20.06.17.
 */
class MoPubNativeAdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = MoPubNativeAdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as MoPubNativeAdViewHolder
        holder.bind(item as MoPubNativeAdCell)
    }

    inner class MoPubNativeAdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_mopub_native)), DestroyableView {

        private val TAG = MoPubNativeAdViewHolder::class.java!!.getSimpleName()

        var mopubNative : MoPubNative? = null

        init {

        }

        fun bind(item: MoPubNativeAdCell) = with(itemView) {
            mopubNative = MoPubNative(context, item.adUnitId, object : MoPubNative.MoPubNativeNetworkListener {
                override fun onNativeFail(errorCode: NativeErrorCode?) {
                    Log.d(TAG, errorCode.toString())
                }

                override fun onNativeLoad(nativeAd: NativeAd?) {
                    val view = nativeAd?.createAdView(context, null)
                    nativeAd?.renderAdView(view)
                    nativeAd?.prepare(view!!)
                    (itemView as FrameLayout).addView(view)

                }
            })

            val viewBinder = ViewBinder.Builder(R.layout.view_mopub_native)
                    .mainImageId(R.id.native_ad_main_image)
                    .iconImageId(R.id.native_ad_icon_image)
                    .titleId(R.id.native_ad_title)
                    .textId(R.id.native_ad_text)
                    .privacyInformationIconImageId(R.id.native_ad_daa_icon_image)
                    .build()
            val adRenderer = MoPubStaticNativeAdRenderer(viewBinder)

            mopubNative?.registerAdRenderer(adRenderer)
            mopubNative?.makeRequest()
        }

        override fun destroy() {
            mopubNative?.destroy()
        }
    }
}