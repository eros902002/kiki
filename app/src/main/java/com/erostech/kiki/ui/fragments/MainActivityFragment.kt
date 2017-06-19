package com.erostech.kiki.ui.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import net.pubnative.sdk.core.config.PNConfigManager
import net.pubnative.sdk.core.request.PNAdModel
import net.pubnative.sdk.core.request.PNRequest
import android.widget.RelativeLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.ProgressBar
import com.erostech.kiki.API_TOKEN
import com.erostech.kiki.KikiApplication
import com.erostech.kiki.PLACEMENT_ID
import com.erostech.kiki.R
import com.erostech.kiki.listeners.InfiniteScrollListener
import com.erostech.kiki.managers.CountriesManager
import com.erostech.kiki.models.Country
import com.erostech.kiki.ui.adapters.CountriesAdapter
import com.erostech.kiki.ui.adapters.CountryDelegateAdapter
import com.erostech.kiki.util.RequestModel
import java.lang.Exception
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_main.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.ArrayList


/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : RxBaseFragment(), CountryDelegateAdapter.onViewSelectedListener {
    override fun onItemSelected(country: Country) {

    }

    companion object {
        private val KEY_COUNTRIES = "countries"
    }

    @Inject lateinit var countriesManager: CountriesManager
    private var countries: List<Country>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        KikiApplication.countriesComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        countries_list.apply {
            setHasFixedSize(true)
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({ requestCountries() }, linearLayout))
        }

        initAdapter()

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_COUNTRIES)) {
            countries = savedInstanceState.get(KEY_COUNTRIES) as List<Country>
            (countries_list.adapter as CountriesAdapter).addCountries(countries!!)
        } else {
            requestCountries()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        val countriesList = (countries_list.adapter as CountriesAdapter).getCountries()
        if (countries != null && countriesList.isNotEmpty()) {
            outState!!.putParcelableArrayList(KEY_COUNTRIES, countriesList as ArrayList<Country>)
        }
    }

    fun requestCountries() {
        val subscription = countriesManager.getCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                { retrievedCountries ->
                    countries = retrievedCountries
                    (countries_list.adapter as CountriesAdapter).addCountries(countries!!)
                },
                { e ->
                    Snackbar.make(countries_list, e.message ?: "", Snackbar.LENGTH_LONG).show()
                }
        )
        subscriptions.add(subscription)
    }

    private fun initAdapter() {
        if (countries_list.adapter == null) {
            countries_list.adapter = CountriesAdapter(this)
        }
    }
}
