package com.erostech.kiki.managers

import com.erostech.kiki.api.CountriesGenericApi
import com.erostech.kiki.models.*
import rx.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by erosgarciaponte on 19.06.17.
 */
@Singleton
class CountriesManager @Inject constructor(private val api: CountriesGenericApi) {
    fun getCountries() : Observable<List<Country>> {
        return Observable.create { subscriber ->
            val callResponse = api.getCountries()
            val response = callResponse.execute()

            if (response.isSuccessful) {
                val dataResponse = response.body()
                val countryList = dataResponse?.map {
                    val currencies = it.currencies?.map {
                        Currency(it.code, it.name, it.symbol)
                    }

                    val languages = it.languages?.map {
                        Language(it.name)
                    }

                    val regionalBlocks = it.regionalBlocks?.map {
                        RegionalBlock(it.name)
                    }

                    Country(it.name,
                            it.capital,
                            it.region,
                            it.subregion,
                            it.population,
                            it.latlng,
                            it.area,
                            currencies,
                            languages,
                            it.flag,
                            regionalBlocks)
                }

                subscriber.onNext(countryList)
                subscriber.onCompleted()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }
}