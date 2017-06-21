package com.erostech.kiki.ui.adapters.delegates

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.erostech.kiki.API_TOKEN
import com.erostech.kiki.R
import com.erostech.kiki.models.PNAdCell
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.util.inflate
import net.pubnative.sdk.core.request.PNAdModel
import net.pubnative.sdk.core.request.PNRequest
import java.lang.Exception

/**
 * Created by erosgarciaponte on 20.06.17.
 */
class PNNativeAdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = PNNativeAdViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as PNNativeAdViewHolder
        holder.bind(item as PNAdCell)
    }

    inner class PNNativeAdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_pn_native_ad)) {

        private val TAG = PNNativeAdViewHolder::class.java!!.getSimpleName()

        var disclosureView : FrameLayout
        var titleView : TextView
        var descriptionView : TextView
        var ratingView : RatingBar
        var bannerView : FrameLayout
        var iconView : ImageView
        var callToActionView : Button

        var adCell: PNAdCell? = null

        init {
            disclosureView = itemView.findViewById(R.id.disclosure_view) as FrameLayout
            titleView = itemView.findViewById(R.id.title_view) as TextView
            descriptionView = itemView.findViewById(R.id.description_view) as TextView
            ratingView = itemView.findViewById(R.id.rating_view) as RatingBar
            bannerView = itemView.findViewById(R.id.banner_view) as FrameLayout
            iconView = itemView.findViewById(R.id.icon_view) as ImageView
            callToActionView = itemView.findViewById(R.id.call_to_action_view) as Button
        }

        fun bind(item: PNAdCell) = with(itemView) {
            adCell = item
            adCell!!.request.start(itemView.context, API_TOKEN, item.placementID, object: PNRequest.Listener {
                override fun onPNRequestLoadFail(request: PNRequest?, exception: Exception?) {
                    Log.d(TAG, exception!!.message ?: "")
                    adCell?.adModel = null
                    cleanAdView()
                    itemView.visibility = View.GONE
                }

                override fun onPNRequestLoadFinish(request: PNRequest?, adModel: PNAdModel?) {
                    if (adCell?.adModel!=null) {
                        (adCell?.adModel as PNAdModel).stopTracking()
                    }

                    adCell?.adModel = adModel
                    itemView.visibility = View.VISIBLE
                    renderAd()
                }
            })
        }

        private fun cleanAdView() {
            disclosureView.removeAllViews();
            titleView.text = ""
            descriptionView.text = ""
            ratingView.rating = 0f
            ratingView.visibility = View.GONE
            bannerView.visibility = View.VISIBLE
            bannerView.removeAllViews();
            iconView.setImageDrawable(null);
        }

        private fun renderAd() {
            if (adCell?.adModel != null) {
                val adModel = adCell?.adModel
                adModel!!.withTitle(titleView)
                        .withIcon(iconView)
                        .withDescription(descriptionView)
                        .withCallToAction(callToActionView)
                        .withRating(ratingView)
                        .withBanner(bannerView)
                        .withContentInfoContainer(disclosureView)
                        .startTracking(bannerView)
            }
        }
    }
}