package com.erostech.kiki.util

import net.pubnative.sdk.core.request.PNAdModel
import net.pubnative.sdk.core.request.PNRequest

/**
 * Created by erosgarciaponte on 16.06.17.
 */
class RequestModel(var placementID: String) {
    var request: PNRequest
    var adModel: PNAdModel? = null

    init {
        request = PNRequest()
    }

    override fun hashCode(): Int {
        return placementID.hashCode()
    }

    companion object {
        private val TAG = RequestModel::class.java.simpleName
    }
}