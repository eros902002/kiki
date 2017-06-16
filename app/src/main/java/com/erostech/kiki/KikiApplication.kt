package com.erostech.kiki

import android.app.Application
import net.pubnative.sdk.core.Pubnative
import net.pubnative.sdk.core.request.PNAdTargetingModel

/**
 * Created by erosgarciaponte on 16.06.17.
 */
class KikiApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Pubnative.setCoppaMode(false)
        val targetingModel = PNAdTargetingModel()
        Pubnative.setTargeting(targetingModel)
        Pubnative.init(this, "7c26af3aa5f6c0a4ab9f4414787215f3bdd004f80b1b358e72c3137c94f5033c")
    }
}