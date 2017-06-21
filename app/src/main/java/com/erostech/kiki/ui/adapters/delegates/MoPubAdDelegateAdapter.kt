package com.erostech.kiki.ui.adapters.delegates

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.erostech.kiki.API_TOKEN
import com.erostech.kiki.MOPUB_AD_UNIT_ID
import com.erostech.kiki.R
import com.erostech.kiki.models.MoPubAdCell
import com.erostech.kiki.models.PNAdCell
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.util.inflate
import com.mopub.nativeads.*
import net.pubnative.sdk.core.request.PNAdModel
import net.pubnative.sdk.core.request.PNRequest
import java.lang.Exception

/**
 * Created by erosgarciaponte on 20.06.17.
 */
class MoPubAdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = MoPubAdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as MoPubAdViewHolder
        holder.bind(item as MoPubAdCell)
    }

    inner class MoPubAdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_mopub)) {

        private val TAG = MoPubAdViewHolder::class.java!!.getSimpleName()

        var titleView : TextView
        var textView : TextView
        var mainImageView : ImageView
        var iconView : ImageView
        var daaIcon : ImageView

        init {
            titleView = itemView.findViewById(R.id.native_ad_title) as TextView
            textView = itemView.findViewById(R.id.native_ad_text) as TextView
            mainImageView = itemView.findViewById(R.id.native_ad_main_image) as ImageView
            iconView = itemView.findViewById(R.id.native_ad_icon_image) as ImageView
            daaIcon = itemView.findViewById(R.id.native_ad_daa_icon_image) as ImageView
        }

        fun bind(item: MoPubAdCell) = with(itemView) {
            val mopubNative = MoPubNative(context, item.adUnitId, object : MoPubNative.MoPubNativeNetworkListener {
                override fun onNativeFail(errorCode: NativeErrorCode?) {
                    Log.d(TAG, errorCode.toString())
                }

                override fun onNativeLoad(nativeAd: NativeAd?) {
                    val view = nativeAd?.createAdView(context, null)
                    nativeAd?.renderAdView(view)
                    nativeAd?.prepare(view!!)

                }
            })

            val viewBinder = ViewBinder.Builder(R.layout.item_mopub)
                    .mainImageId(R.id.native_ad_title)
                    .iconImageId(R.id.native_ad_icon_image)
                    .titleId(R.id.native_ad_title)
                    .textId(R.id.native_ad_text)
                    .privacyInformationIconImageId(R.id.native_ad_daa_icon_image)
                    .build()
            val adRenderer = MoPubStaticNativeAdRenderer(viewBinder)

            mopubNative.registerAdRenderer(adRenderer)
            mopubNative.makeRequest()
        }
    }
}