package com.erostech.kiki.api

import com.erostech.kiki.models.CountryResponse
import retrofit2.Call

/**
 * Created by erosgarciaponte on 19.06.17.
 */
interface CountriesGenericApi {
    fun getCountries(): Call<List<CountryResponse>>
}