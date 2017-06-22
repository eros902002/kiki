package com.erostech.kiki.ui.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erostech.kiki.*
import com.erostech.kiki.listeners.InfiniteScrollListener
import com.erostech.kiki.managers.CountriesManager
import com.erostech.kiki.models.*
import com.erostech.kiki.ui.adapters.CountriesAdapter
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.ui.adapters.delegates.CountryDelegateAdapter
import com.mopub.mobileads.MoPubErrorCode
import com.mopub.mobileads.MoPubInterstitial
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_main.*
import net.pubnative.sdk.layouts.PNLargeLayout
import net.pubnative.sdk.layouts.PNLayout
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.ArrayList


/**
 * A placeholder fragment containing a simple view.
 */
class MainFragment : RxBaseFragment(),
        CountryDelegateAdapter.onViewSelectedListener {
    override fun onItemSelected(country: Country) {

    }

    companion object {
        private val KEY_COUNTRIES = "countries"
        //private val TAG = MainFragment::class.simpleName
    }

    @Inject lateinit var countriesManager: CountriesManager
    private var countries: List<ViewType>? = null

    private var mopubInterstitial : MoPubInterstitial? = null

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

        if (savedInstanceState == null) {
            // Code for PubNative Interstitial
            /*val largeAd = PNLargeLayout()
            largeAd.setLoadListener(object : PNLayout.LoadListener {
                override fun onPNLayoutLoadFail(layout: PNLayout?, exception: Exception?) {
                    Log.d("Large ad", exception!!.message ?: "")
                }

                override fun onPNLayoutLoadFinish(layout: PNLayout?) {
                    largeAd.show()
                }
            })
            largeAd.load(context, API_TOKEN, LAYOUT_LARGE_PLACEMENT_ID)*/

            // Code for MoPub Interstitial

            mopubInterstitial = MoPubInterstitial(activity, MOPUB_LARGE_AD_UNIT_ID)
            (mopubInterstitial as MoPubInterstitial).interstitialAdListener = object : MoPubInterstitial.InterstitialAdListener {
                override fun onInterstitialLoaded(interstitial: MoPubInterstitial?) {
                    Log.d("Large ad", "onInterstitialLoaded")
                }

                override fun onInterstitialFailed(interstitial: MoPubInterstitial?, errorCode: MoPubErrorCode?) {
                    Log.d("Large ad", "onInterstitialFailed: ${errorCode.toString()}")
                }

                override fun onInterstitialClicked(interstitial: MoPubInterstitial?) {
                    Log.d("Large ad", "onInterstitialClicked")
                }

                override fun onInterstitialDismissed(interstitial: MoPubInterstitial?) {
                    Log.d("Large ad", "onInterstitialDismissed")
                }

                override fun onInterstitialShown(interstitial: MoPubInterstitial?) {
                    Log.d("Large ad", "onInterstitialShown")
                }
            }
            (mopubInterstitial as MoPubInterstitial).load()
        }
    }

    override fun onDestroy() {
        mopubInterstitial?.destroy()
        super.onDestroy()
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
                    injectAds(countries!!)
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

    private fun injectAds(countries: List<ViewType>) {
        (countries as ArrayList<ViewType>)
        countries.add(2, PNAdCell(NATIVE_PLACEMENT_ID))
        countries.add(4, PNSmallLayoutAdCell(LAYOUT_SMALL_PLACEMENT_ID))
        countries.add(6, PNMediumLayoutAdCell(LAYOUT_MEDIUM_PLACEMENT_ID))
        countries.add(8, MoPubNativeAdCell(MOPUB_NATIVE_AD_UNIT_ID))
        countries.add(10, MoPubBannerAdCell(MOPUB_BANNER_AD_UNIT_ID))
        countries.add(12, MoPubMediumAdCell(MOPUB_MEDIUM_AD_UNIT_ID))
    }
}
