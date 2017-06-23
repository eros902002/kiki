package com.erostech.kiki.ui.adapters.delegates.facebook

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.erostech.kiki.FACEBOOK_NATIVE_PLACEMENT
import com.erostech.kiki.R
import com.erostech.kiki.models.FBNativeAdCell
import com.erostech.kiki.ui.adapters.DestroyableView
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.ui.adapters.delegates.ViewTypeDelegateAdapter
import com.erostech.kiki.util.inflate
import com.facebook.ads.*
import java.util.ArrayList

/**
 * Created by erosgarciaponte on 23.06.17.
 */
class FBNativeAdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = FBNativeAdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as FBNativeAdViewHolder
        holder.bind(item as FBNativeAdCell)
    }

    inner class FBNativeAdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_facebook_native)), DestroyableView {
        private val TAG = FBNativeAdViewHolder::class.java!!.getSimpleName()

        val adIcon: ImageView
        val adtitle: TextView
        val adMedia: MediaView
        val adSocialContext: TextView
        val adBody: TextView
        val adCallToAction: Button
        val adChoicesContainer: LinearLayout

        var nativeAd: NativeAd? = null

        init {
            adIcon = itemView.findViewById(R.id.native_ad_icon) as ImageView
            adtitle = itemView.findViewById(R.id.native_ad_title) as TextView
            adMedia = itemView.findViewById(R.id.native_ad_media) as MediaView
            adSocialContext = itemView.findViewById(R.id.native_ad_social_context) as TextView
            adBody = itemView.findViewById(R.id.native_ad_body) as TextView
            adCallToAction = itemView.findViewById(R.id.native_ad_call_to_action) as Button
            adChoicesContainer = itemView.findViewById(R.id.ad_choices_container) as LinearLayout
        }

        fun bind(item: FBNativeAdCell) = with(itemView) {
            nativeAd = NativeAd(itemView.context, FACEBOOK_NATIVE_PLACEMENT)
            nativeAd?.setAdListener(object : AdListener {
                override fun onError(ad: Ad?, adError: AdError?) {
                    Log.d(TAG, "onError: ${adError.toString()}")
                }

                override fun onAdClicked(ad: Ad?) {
                    Log.d(TAG, "onAdClicked")
                }

                override fun onAdLoaded(ad: Ad?) {
                    Log.d(TAG, "onAdLoaded")
                    nativeAd?.unregisterView()

                    adtitle.text = nativeAd?.adTitle
                    adSocialContext.text = nativeAd?.adSocialContext
                    adBody.text = nativeAd?.adBody
                    adCallToAction.text = nativeAd?.adCallToAction

                    val icon: NativeAd.Image = nativeAd?.adIcon!!
                    NativeAd.downloadAndDisplayImage(icon, adIcon)

                    adChoicesContainer.removeAllViews()
                    val adChoicesView: AdChoicesView = AdChoicesView(context, nativeAd, true)
                    adChoicesContainer.addView(adChoicesView)

                    val clickableViews = ArrayList<View>()
                    clickableViews.add(adtitle)
                    clickableViews.add(adCallToAction)
                    nativeAd?.registerViewForInteraction(itemView, clickableViews)

                }

                override fun onLoggingImpression(ad: Ad?) {
                    Log.d(TAG, "onLoggingImpression")
                }
            })
            nativeAd?.loadAd()
        }

        override fun destroy() {
            nativeAd?.destroy()
        }
    }
}