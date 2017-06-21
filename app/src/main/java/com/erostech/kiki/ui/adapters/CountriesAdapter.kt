package com.erostech.kiki.ui.adapters

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.erostech.kiki.models.Country
import com.erostech.kiki.ui.adapters.delegates.*

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
        delegateAdapters.put(AdapterConstants.MOPUD_NATIVE_AD, MoPubAdDelegateAdapter())
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