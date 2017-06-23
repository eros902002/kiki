package com.erostech.kiki.ui.adapters.delegates.pubnative

import com.erostech.kiki.ui.adapters.DestroyableView
import com.erostech.kiki.util.inflate

/**
 * Created by erosgarciaponte on 20.06.17.
 */
class PNNativeAdDelegateAdapter : com.erostech.kiki.ui.adapters.delegates.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: android.view.ViewGroup): android.support.v7.widget.RecyclerView.ViewHolder = PNNativeAdViewHolder(parent)

    override fun onBindViewHolder(holder: android.support.v7.widget.RecyclerView.ViewHolder, item: com.erostech.kiki.ui.adapters.ViewType) {
        holder as com.erostech.kiki.ui.adapters.delegates.pubnative.PNNativeAdDelegateAdapter.PNNativeAdViewHolder
        holder.bind(item as com.erostech.kiki.models.PNAdCell)
    }

    inner class PNNativeAdViewHolder(parent: android.view.ViewGroup) : android.support.v7.widget.RecyclerView.ViewHolder(
            parent.inflate(com.erostech.kiki.R.layout.item_pn_native_ad)), DestroyableView {

        private val TAG = com.erostech.kiki.ui.adapters.delegates.pubnative.PNNativeAdDelegateAdapter.PNNativeAdViewHolder::class.java!!.getSimpleName()

        var disclosureView : android.widget.FrameLayout
        var titleView : android.widget.TextView
        var descriptionView : android.widget.TextView
        var ratingView : android.widget.RatingBar
        var bannerView : android.widget.FrameLayout
        var iconView : android.widget.ImageView
        var callToActionView : android.widget.Button

        var adCell: com.erostech.kiki.models.PNAdCell? = null

        init {
            disclosureView = itemView.findViewById(com.erostech.kiki.R.id.disclosure_view) as android.widget.FrameLayout
            titleView = itemView.findViewById(com.erostech.kiki.R.id.title_view) as android.widget.TextView
            descriptionView = itemView.findViewById(com.erostech.kiki.R.id.description_view) as android.widget.TextView
            ratingView = itemView.findViewById(com.erostech.kiki.R.id.rating_view) as android.widget.RatingBar
            bannerView = itemView.findViewById(com.erostech.kiki.R.id.banner_view) as android.widget.FrameLayout
            iconView = itemView.findViewById(com.erostech.kiki.R.id.icon_view) as android.widget.ImageView
            callToActionView = itemView.findViewById(com.erostech.kiki.R.id.call_to_action_view) as android.widget.Button
        }

        fun bind(item: com.erostech.kiki.models.PNAdCell) = with(itemView) {
            adCell = item
            adCell!!.request.start(itemView.context, com.erostech.kiki.API_TOKEN, item.placementID, object: net.pubnative.sdk.core.request.PNRequest.Listener {
                override fun onPNRequestLoadFail(request: net.pubnative.sdk.core.request.PNRequest?, exception: java.lang.Exception?) {
                    android.util.Log.d(TAG, exception!!.message ?: "")
                    adCell?.adModel = null
                    cleanAdView()
                    itemView.visibility = android.view.View.GONE
                }

                override fun onPNRequestLoadFinish(request: net.pubnative.sdk.core.request.PNRequest?, adModel: net.pubnative.sdk.core.request.PNAdModel?) {
                    if (adCell?.adModel!=null) {
                        (adCell?.adModel as net.pubnative.sdk.core.request.PNAdModel).stopTracking()
                    }

                    adCell?.adModel = adModel
                    itemView.visibility = android.view.View.VISIBLE
                    renderAd()
                }
            })
        }

        private fun cleanAdView() {
            disclosureView.removeAllViews();
            titleView.text = ""
            descriptionView.text = ""
            ratingView.rating = 0f
            ratingView.visibility = android.view.View.GONE
            bannerView.visibility = android.view.View.VISIBLE
            bannerView.removeAllViews();
            iconView.setImageDrawable(null);
        }

        private fun renderAd() {
            if (adCell?.adModel != null) {
                adCell?.adModel!!.withTitle(titleView)
                        .withIcon(iconView)
                        .withDescription(descriptionView)
                        .withCallToAction(callToActionView)
                        .withRating(ratingView)
                        .withBanner(bannerView)
                        .withContentInfoContainer(disclosureView)
                        .startTracking(bannerView)
            }
        }

        override fun destroy() {
            adCell?.adModel?.stopTracking()
        }
    }
}