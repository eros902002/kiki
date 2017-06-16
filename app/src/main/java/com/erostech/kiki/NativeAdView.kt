package com.erostech.kiki

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.*
import net.pubnative.sdk.core.request.PNAdModel
import net.pubnative.sdk.core.request.PNRequest

/**
 * Created by erosgarciaponte on 16.06.17.
 */
class NativeAdView : FrameLayout, PNRequest.Listener {

    private var mCellRequestModel: RequestModel? = null
    // Behaviour
    private var mAdLoading: ProgressBar? = null
    private var mAdViewContainer: ViewGroup? = null

    private var mContentInfo: ViewGroup? = null
    private var mDescription: TextView? = null
    private var mTitle: TextView? = null
    private var mRating: RatingBar? = null
    private var mIcon: ImageView? = null
    private var mBanner: RelativeLayout? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @TargetApi(21)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        val view = View.inflate(context, R.layout.view_native_ad, null)
        mAdLoading = view.findViewById(R.id.ad_spinner) as ProgressBar
        mAdViewContainer = view.findViewById(R.id.ad_view_container) as ViewGroup
        mContentInfo = view.findViewById(R.id.ad_disclosure) as ViewGroup
        mTitle = view.findViewById(R.id.ad_title_text) as TextView
        mDescription = view.findViewById(R.id.ad_description_text) as TextView
        mRating = view.findViewById(R.id.ad_rating) as RatingBar
        mIcon = view.findViewById(R.id.ad_icon_image) as ImageView
        mBanner = view.findViewById(R.id.media_container) as RelativeLayout
    }

    fun setCellRequestModel(cellRequestModel: RequestModel) {
        if (mCellRequestModel != null && mCellRequestModel!!.adModel != null) {
            mCellRequestModel!!.adModel?.stopTracking()
        }
        mCellRequestModel = cellRequestModel
        cleanView()
        renderAd()
    }

    fun renderAd() {
        // Placement data
        val model = mCellRequestModel!!.adModel
        if (model != null) {
            // Privacy container
            mRating?.visibility = View.VISIBLE

            // Tracking with views
            model.withTitle(mTitle)
                    .withDescription(mDescription)
                    .withIcon(mIcon)
                    .withBanner(mBanner)
                    .withRating(mRating)
                    .withContentInfoContainer(mContentInfo)
                    .startTracking(mAdViewContainer)
        }
    }

    fun cleanView() {
        mContentInfo?.removeAllViews()
        mTitle?.text = ""
        mDescription?.text = ""
        mRating?.rating = 0f
        mRating?.visibility = View.GONE
        mBanner?.removeAllViews()
        mIcon?.setImageDrawable(null)
        mContentInfo?.removeAllViews()
        mAdLoading?.visibility = View.GONE
    }

    fun request() {
        cleanView()
        mAdLoading?.visibility = View.VISIBLE
        mCellRequestModel!!.request.start(context, "7c26af3aa5f6c0a4ab9f4414787215f3bdd004f80b1b358e72c3137c94f5033c", "pubnative_only", this)
    }

    override fun onPNRequestLoadFinish(request: PNRequest, ad: PNAdModel) {
        mAdLoading?.visibility = View.GONE
        if (mCellRequestModel!!.adModel != null) {
            mCellRequestModel!!.adModel?.stopTracking()
        }
        mCellRequestModel!!.adModel = ad
        renderAd()
    }

    override fun onPNRequestLoadFail(request: PNRequest, exception: Exception) {
        Toast.makeText(context, exception.toString(), Toast.LENGTH_LONG).show()
        mAdLoading?.visibility = View.GONE
        mCellRequestModel!!.adModel = null
        cleanView()
    }
}