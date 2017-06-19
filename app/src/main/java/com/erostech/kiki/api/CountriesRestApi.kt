package com.erostech.kiki.api

import com.erostech.kiki.models.CountriesResponse
import com.erostech.kiki.models.CountryResponse
import retrofit2.Call
import javax.inject.Inject

/**
 * Created by erosgarciaponte on 19.06.17.
 */
class CountriesRestApi @Inject constructor(private val countriesApi: CountriesApi): CountriesGenericApi {
    override fun getCountries(): Call<CountriesResponse> {
        return countriesApi.getCountries()
    }
}