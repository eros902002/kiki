package com.erostech.kiki.components

import com.erostech.kiki.modules.AppModule
import com.erostech.kiki.modules.CountriesModule
import com.erostech.kiki.modules.NetworkModule
import com.erostech.kiki.ui.fragments.MainActivityFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by erosgarciaponte on 19.06.17.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, CountriesModule::class, NetworkModule::class))
interface CountriesComponent {
    fun inject(fragment: MainActivityFragment)
}
