package com.erostech.kiki

import android.app.Application
import com.erostech.kiki.components.CountriesComponent
import com.erostech.kiki.components.DaggerCountriesComponent
import com.erostech.kiki.modules.AppModule
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.flurry.android.FlurryAgent
import net.pubnative.sdk.core.Pubnative
import net.pubnative.sdk.core.request.PNAdTargetingModel

/**
 * Created by erosgarciaponte on 16.06.17.
 */
class KikiApplication: Application() {

    companion object {
        lateinit var countriesComponent: CountriesComponent
    }

    override fun onCreate() {
        super.onCreate()
        Pubnative.setCoppaMode(true)
        val targetingModel = PNAdTargetingModel()
        Pubnative.setTargeting(targetingModel)
        Pubnative.init(this, API_TOKEN)

        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "DXYDJQ55RCS88VP9JF62")

        countriesComponent = DaggerCountriesComponent.builder().appModule(AppModule(this)).build()
    }
}