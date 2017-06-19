package com.erostech.kiki.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.erostech.kiki.R
import com.erostech.kiki.models.Country
import com.erostech.kiki.util.inflate
import com.erostech.kiki.util.loadImage

import kotlinx.android.synthetic.main.item_country.*
import kotlinx.android.synthetic.main.item_country.view.*

/**
 * Created by erosgarciaponte on 19.06.17.
 */
class CountryDelegateAdapter(val viewActions: onViewSelectedListener) : ViewTypeDelegateAdapter {
    interface onViewSelectedListener {
        fun onItemSelected(country: Country)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = CountryViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as CountryViewHolder
        holder.bind(item as Country)
    }

    inner class CountryViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_country)) {

        fun bind(item: Country) = with(itemView) {
            name_view.setText(item.name!!)

            if (item.flag != null) {
                flag_view.loadImage(item.flag)
            }

            if (item.region != null) {
                region_view.setText(item.region)
            }

            super.itemView.setOnClickListener {
                viewActions.onItemSelected(item)
            }
        }
    }
}