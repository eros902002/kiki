package com.erostech.kiki.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by erosgarciaponte on 19.06.17.
 */
class Countries(
        val countries: List<Country>?
)

class Country(
        val name: String?,
        val capital: String?,
        val region: String?,
        val subregion: String?,
        val population: Long,
        val latlng: List<String>?,
        val area: Long,
        val currencies: List<Currency>?,
        val languages: List<Language>?,
        val flag: String?,
        val regionalBlocks: List<RegionalBlock>?) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Country> = object : Parcelable.Creator<Country> {
            override fun createFromParcel(source: Parcel): Country = Country(source)
            override fun newArray(size: Int): Array<Country?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readLong(),
            source.createStringArrayList(),
            source.readLong(),
            source.createTypedArrayList(Currency.CREATOR),
            source.createTypedArrayList(Language.CREATOR),
            source.readString(),
            source.createTypedArrayList(RegionalBlock.CREATOR)
            )

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
        dest?.writeString(capital)
        dest?.writeString(region)
        dest?.writeString(subregion)
        dest?.writeLong(population)
        dest?.writeStringList(latlng)
        dest?.writeLong(area)
        dest?.writeTypedList(currencies)
        dest?.writeTypedList(languages)
        dest?.writeString(flag)
        dest?.writeTypedList(regionalBlocks)
    }
}

class Currency(val code: String?, val name: String?, val symbol: String?) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Currency> = object : Parcelable.Creator<Currency> {
            override fun createFromParcel(source: Parcel): Currency = Currency(source)
            override fun newArray(size: Int): Array<Currency?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString(), source.readString(), source.readString())

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(code)
        dest?.writeString(name)
        dest?.writeString(symbol)
    }
}

class Language(val name: String?) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Language> = object : Parcelable.Creator<Language> {
            override fun createFromParcel(source: Parcel): Language = Language(source)
            override fun newArray(size: Int): Array<Language?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString())

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
    }
}

class RegionalBlock(val name: String?) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<RegionalBlock> = object : Parcelable.Creator<RegionalBlock> {
            override fun createFromParcel(source: Parcel): RegionalBlock = RegionalBlock(source)
            override fun newArray(size: Int): Array<RegionalBlock?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readString())

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(name)
    }
}