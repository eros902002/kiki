package com.erostech.kiki.models

/**
 * Created by erosgarciaponte on 19.06.17.
 */
class CountriesResponse(
        val countries: List<CountryResponse>?
)

class CountryResponse(
        val name: String?,
        val topLevelDomain: List<String>?,
        val alpha2Code: String?,
        val alpha3Code: String?,
        val callingCodes: List<String>?,
        val capital: String?,
        val altSpellings: List<String>?,
        val region: String?,
        val subregion: String?,
        val population: Long,
        val latlng: List<String>?,
        val demonym: String?,
        val area: Long,
        val gini: Double,
        val timezones: List<String>?,
        val borders: List<String>?,
        val nativeName: String?,
        val numericCode: String?,
        val currencies: List<CurrencyResponse>?,
        val languages: List<LanguageResponse>?,
        val translations: TranslationsResponse?,
        val flag: String?,
        val regionalBlocks: List<RegionalBlockResponse>?)

class CurrencyResponse(val code: String?, val name: String?, val symbol: String?)


class LanguageResponse(
        val iso639_1: String?,
        val iso639_2: String?,
        val name: String?,
        val nativeName: String?)

class TranslationsResponse(
        val de: String?,
        val es: String?,
        val fr: String?)

class RegionalBlockResponse(
        val acronym: String?,
        val name: String?,
        val otherAcronyms: List<String>?,
        val otherNames: List<String>?)