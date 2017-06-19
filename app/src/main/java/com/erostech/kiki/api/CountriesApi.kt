package com.erostech.kiki.api

import com.erostech.kiki.models.CountryResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by erosgarciaponte on 19.06.17.
 */
interface CountriesApi {
    @GET("name/colombia")
    fun getCountries(): Call<List<CountryResponse>>
}