package com.erostech.kiki.ui.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import net.pubnative.sdk.core.config.PNConfigManager
import net.pubnative.sdk.core.request.PNAdModel
import net.pubnative.sdk.core.request.PNRequest
import android.widget.RelativeLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.ProgressBar
import com.erostech.kiki.API_TOKEN
import com.erostech.kiki.PLACEMENT_ID
import com.erostech.kiki.R
import com.erostech.kiki.util.RequestModel
import java.lang.Exception


/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment(), PNRequest.Listener {

    private var requestModel = RequestModel(PLACEMENT_ID)

    private var mAdLoading: ProgressBar? = null
    private var mAdViewContainer: ViewGroup? = null

    private var mContentInfo: ViewGroup? = null
    private var mDescription: TextView? = null
    private var mTitle: TextView? = null
    private var mRating: RatingBar? = null
    private var mIcon: ImageView? = null
    private var mBanner: RelativeLayout? = null
    private var mRequestButton: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        mAdLoading = rootView.findViewById(R.id.ad_spinner) as ProgressBar
        mAdViewContainer = rootView.findViewById(R.id.ad_view_container) as ViewGroup
        mRequestButton = rootView.findViewById(R.id.request_button) as Button
        mContentInfo = rootView.findViewById(R.id.ad_disclosure) as ViewGroup
        mTitle = rootView.findViewById(R.id.ad_title_text) as TextView
        mDescription = rootView.findViewById(R.id.ad_description_text) as TextView
        mRating = rootView.findViewById(R.id.ad_rating) as RatingBar
        mIcon = rootView.findViewById(R.id.ad_icon_image) as ImageView
        mBanner = rootView.findViewById(R.id.media_container) as RelativeLayout
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRequestButton?.setOnClickListener(View.OnClickListener { v ->
            request()
        })
    }

    override fun onResume() {
        super.onResume()
        PNConfigManager.getConfig(context, API_TOKEN, PNConfigManager.Listener { configModel ->
            val placements = ArrayList<String>()
            if (configModel != null && configModel.placements != null) {
                val placementsSet = configModel.placements.keys
                if (placementsSet != null) {
                    placements.addAll(placementsSet)
                }
            }

            val placement = PLACEMENT_ID
            requestModel = RequestModel(placement)
            requestModel!!.request.setCacheResources(true)
        })
    }

    fun request() {
        cleanView()
        mAdLoading?.visibility = View.VISIBLE
        requestModel!!.request.start(context, API_TOKEN, PLACEMENT_ID, this)
    }

    fun cleanView() {
        mContentInfo?.removeAllViews()
        mTitle?.setText("")
        mDescription?.setText("")
        mRating?.setRating(0f)
        mRating?.setVisibility(View.GONE)
        mBanner?.removeAllViews()
        mIcon?.setImageDrawable(null)
        mContentInfo?.removeAllViews()
        mAdLoading?.setVisibility(View.GONE)
    }

    fun renderAd() {
        val adModel = requestModel.adModel
        if (adModel != null) {
            mRating?.visibility = View.VISIBLE
            adModel.withTitle(mTitle)
                    .withIcon(mIcon)
                    .withDescription(mDescription)
                    .withBanner(mBanner)
                    .withRating(mRating)
                    .withContentInfoContainer(mContentInfo)
                    .startTracking(mAdViewContainer)
        }
    }

    override fun onPNRequestLoadFinish(request: PNRequest?, ad: PNAdModel?) {
        mAdLoading?.setVisibility(View.GONE);
        if (requestModel.adModel != null) {
            requestModel.adModel!!.stopTracking();
        }
        requestModel.adModel = ad;
        renderAd();
    }

    override fun onPNRequestLoadFail(request: PNRequest?, exception: Exception?) {
        Toast.makeText(context, exception.toString(), Toast.LENGTH_LONG).show();
        mAdLoading?.setVisibility(View.GONE);
        requestModel.adModel = null;
        cleanView();
    }
}
