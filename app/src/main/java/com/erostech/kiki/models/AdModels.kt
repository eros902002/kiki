package com.erostech.kiki.models

import com.erostech.kiki.ui.adapters.AdapterConstants
import com.erostech.kiki.ui.adapters.ViewType
import net.pubnative.sdk.core.request.PNAdModel
import net.pubnative.sdk.core.request.PNRequest
import net.pubnative.sdk.layouts.PNLayout
import net.pubnative.sdk.layouts.PNMediumLayout
import net.pubnative.sdk.layouts.PNSmallLayout
import net.pubnative.sdk.layouts.adapter.PNLayoutAdModel

/**
 * Created by erosgarciaponte on 20.06.17.
 */
class PNAdCell(var placementID: String) : ViewType {
    var request: PNRequest
    var adModel: PNAdModel? = null

    init {
        request = PNRequest()
        request.setCacheResources(true)
    }

    override fun hashCode(): Int {
        return placementID.hashCode()
    }

    override fun getViewType(): Int = AdapterConstants.PN_NATIVE_AD
}

class PNSmallLayoutAdCell(var placementID: String) : ViewType {
    var request: PNSmallLayout

    init {
        request = PNSmallLayout()
    }

    override fun hashCode(): Int {
        return placementID.hashCode()
    }

    override fun getViewType(): Int = AdapterConstants.PN_LAYOUT_SMALL_AD
}

class PNMediumLayoutAdCell(var placementID: String) : ViewType {
    var request: PNMediumLayout

    init {
        request = PNMediumLayout()
    }

    override fun hashCode(): Int {
        return placementID.hashCode()
    }

    override fun getViewType(): Int = AdapterConstants.PN_LAYOUT_MEDIUM_AD
}

class MoPubNativeAdCell(var adUnitId: String) : ViewType {

    override fun hashCode(): Int {
        return adUnitId.hashCode()
    }

    override fun getViewType(): Int = AdapterConstants.MOPUB_NATIVE_AD
}

class MoPubBannerAdCell(var adUnitId: String) : ViewType {

    override fun hashCode(): Int {
        return adUnitId.hashCode()
    }

    override fun getViewType(): Int = AdapterConstants.MOPUB_BANNER_AD
}

class MoPubMediumAdCell(var adUnitId: String) : ViewType {

    override fun hashCode(): Int {
        return adUnitId.hashCode()
    }

    override fun getViewType(): Int = AdapterConstants.MOPUB_MEDIUM_AD
}