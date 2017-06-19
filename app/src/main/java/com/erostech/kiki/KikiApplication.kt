package com.erostech.kiki

import android.app.Application
import com.erostech.kiki.components.CountriesComponent
import com.erostech.kiki.components.DaggerCountriesComponent
import com.erostech.kiki.modules.AppModule
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
        Pubnative.setCoppaMode(false)
        val targetingModel = PNAdTargetingModel()
        Pubnative.setTargeting(targetingModel)
        Pubnative.init(this, API_TOKEN)

        countriesComponent = DaggerCountriesComponent.builder().appModule(AppModule(this)).build()
    }
}