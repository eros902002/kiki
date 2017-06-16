package com.erostech.kiki.models

/**
 * Created by erosgarciaponte on 16.06.17.
 */
class Country {
    var name: String? = null
    var topLevelDomain: List<String>? = null
    var alpha2Code: String? = null
    var alpha3Code: String? = null
    var callingCodes: List<String>? = null
    var capital: String? = null
    var altSpellings: List<String>? = null
    var region: String? = null
    var subregion: String? = null
    var population: Long = 0
    var latlng: List<String>? = null
    var demonym: String? = null
    var area: Long = 0
    var gini: Double = 0.toDouble()
    var timezones: List<String>? = null
    var borders: List<String>? = null
    var nativeName: String? = null
    var numericCode: String? = null
    var currencies: List<Currency>? = null
    var languages: List<Language>? = null
    var translations: Translations? = null
    var flag: String? = null
    var regionalBlocks: List<RegionalBlock>? = null
}