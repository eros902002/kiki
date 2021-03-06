package com.erostech.kiki.ui.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.erostech.kiki.models.Country
import com.erostech.kiki.ui.adapters.delegates.*
import com.erostech.kiki.ui.adapters.delegates.admob.AdmobBannerAdDelegateAdapter
import com.erostech.kiki.ui.adapters.delegates.admob.AdmobNativeAdDelegateAdapter
import com.erostech.kiki.ui.adapters.delegates.facebook.FBBannerAdDelegateAdapter
import com.erostech.kiki.ui.adapters.delegates.facebook.FBNativeAdDelegateAdapter
import com.erostech.kiki.ui.adapters.delegates.mopub.MoPubBannerAdDelegateAdapter
import com.erostech.kiki.ui.adapters.delegates.mopub.MoPubMediumAdBannerDelegateAdapter
import com.erostech.kiki.ui.adapters.delegates.mopub.MoPubNativeAdDelegateAdapter
import com.erostech.kiki.ui.adapters.delegates.pubnative.PNMediumLayoutAdDelegateAdapter
import com.erostech.kiki.ui.adapters.delegates.pubnative.PNNativeAdDelegateAdapter
import com.erostech.kiki.ui.adapters.delegates.pubnative.PNSmallLayoutAdDelegateAdapter

/**
 * Created by erosgarciaponte on 19.06.17.
 */
class CountriesAdapter(listener: CountryDelegateAdapter.onViewSelectedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val loadingItem = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.LOADING
    }

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        delegateAdapters.put(AdapterConstants.COUNTRY, CountryDelegateAdapter(listener))
        delegateAdapters.put(AdapterConstants.PN_NATIVE_AD, PNNativeAdDelegateAdapter())
        delegateAdapters.put(AdapterConstants.PN_LAYOUT_SMALL_AD, PNSmallLayoutAdDelegateAdapter())
        delegateAdapters.put(AdapterConstants.PN_LAYOUT_MEDIUM_AD, PNMediumLayoutAdDelegateAdapter())
        delegateAdapters.put(AdapterConstants.MOPUB_NATIVE_AD, MoPubNativeAdDelegateAdapter())
        delegateAdapters.put(AdapterConstants.MOPUB_BANNER_AD, MoPubBannerAdDelegateAdapter())
        delegateAdapters.put(AdapterConstants.MOPUB_MEDIUM_AD, MoPubMediumAdBannerDelegateAdapter())
        delegateAdapters.put(AdapterConstants.ADMOB_BANNER_AD, AdmobBannerAdDelegateAdapter())
        delegateAdapters.put(AdapterConstants.ADMOB_NATIVE_AD, AdmobNativeAdDelegateAdapter())
        delegateAdapters.put(AdapterConstants.FACEBOOK_BANNER_AD, FBBannerAdDelegateAdapter())
        delegateAdapters.put(AdapterConstants.FACEBOOK_NATIVE_AD, FBNativeAdDelegateAdapter())

        items = ArrayList()
        items.add(loadingItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder!!, this.items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return this.items[position].getViewType()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
        super.onViewDetachedFromWindow(holder)
        if (holder is DestroyableView) {
            holder.destroy()
        }
    }

    fun addCountries(countries: List<ViewType>) {
        val initPosition = items.size -1
        items.removeAt(initPosition)
        notifyItemRemoved(initPosition)

        items.addAll(countries)
        items.add(loadingItem)
        notifyItemRangeChanged(initPosition, items.size + 1)
    }

    fun clearAndAddCountries(countries: List<Country>) {
        items.clear()
        notifyItemRangeChanged(0, getLastPosition())

        items.addAll(countries)
        items.add(loadingItem)
        notifyItemRangeInserted(0, items.size)
    }

    fun getCountries(): List<Country> {
        return items
                .filter { it.getViewType() == AdapterConstants.COUNTRY }
                .map { it as Country }
    }

    private fun getLastPosition() = if (items.lastIndex == -1) 0 else items.lastIndex
}