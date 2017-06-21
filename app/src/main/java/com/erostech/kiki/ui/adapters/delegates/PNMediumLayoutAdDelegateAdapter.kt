package com.erostech.kiki.ui.adapters.delegates

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.erostech.kiki.API_TOKEN
import com.erostech.kiki.R
import com.erostech.kiki.models.PNAdCell
import com.erostech.kiki.models.PNMediumLayoutAdCell
import com.erostech.kiki.models.PNSmallLayoutAdCell
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.util.inflate
import com.erostech.kiki.util.showToast
import net.pubnative.sdk.core.request.PNAdModel
import net.pubnative.sdk.core.request.PNRequest
import net.pubnative.sdk.layouts.PNLayout
import java.lang.Exception

/**
 * Created by erosgarciaponte on 20.06.17.
 */
class PNMediumLayoutAdDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = PNMediumLayoutViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        holder as PNMediumLayoutViewHolder
        holder.bind(item as PNMediumLayoutAdCell)
    }

    inner class PNMediumLayoutViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            parent.inflate(R.layout.item_pn_layout_medium)) {

        private val TAG = PNMediumLayoutViewHolder::class.java!!.getSimpleName()

        var adCell: PNMediumLayoutAdCell? = null

        fun bind(item: PNMediumLayoutAdCell) = with(itemView) {
            adCell = item
            adCell!!.request.setLoadListener(object: PNLayout.LoadListener {
                override fun onPNLayoutLoadFail(layout: PNLayout?, exception: Exception?) {
                    Log.d(TAG, exception!!.message ?: "")
                }

                override fun onPNLayoutLoadFinish(layout: PNLayout?) {
                    val layout = adCell!!.request.getView(itemView.context)
                    adCell!!.request.startTrackingView()
                    (itemView as FrameLayout).addView(layout)
                }
            })
            adCell!!.request.load(itemView.context, API_TOKEN, adCell!!.placementID)
        }
    }
}