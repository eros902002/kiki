package com.erostech.kiki.modules

import com.erostech.kiki.api.CountriesApi
import com.erostech.kiki.api.CountriesGenericApi
import com.erostech.kiki.api.CountriesRestApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by erosgarciaponte on 19.06.17.
 */
@Module
class CountriesModule {
    @Provides
    @Singleton
    fun provideCountriesRestApi(countriesApi: CountriesApi): CountriesGenericApi = CountriesRestApi(countriesApi)

    @Provides
    @Singleton
    fun provideCountriesApi(retrofit: Retrofit): CountriesApi = retrofit.create(CountriesApi::class.java)
}