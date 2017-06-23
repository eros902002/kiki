package com.erostech.kiki.ui.adapters.delegates.pubnative

import com.erostech.kiki.ui.adapters.DestroyableView
import com.erostech.kiki.util.inflate

/**
 * Created by erosgarciaponte on 20.06.17.
 */
class PNSmallLayoutAdDelegateAdapter : com.erostech.kiki.ui.adapters.delegates.ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: android.view.ViewGroup): android.support.v7.widget.RecyclerView.ViewHolder = PNSmallLayoutViewHolder(parent)

    override fun onBindViewHolder(holder: android.support.v7.widget.RecyclerView.ViewHolder, item: com.erostech.kiki.ui.adapters.ViewType) {
        holder as com.erostech.kiki.ui.adapters.delegates.pubnative.PNSmallLayoutAdDelegateAdapter.PNSmallLayoutViewHolder
        holder.bind(item as com.erostech.kiki.models.PNSmallLayoutAdCell)
    }

    inner class PNSmallLayoutViewHolder(parent: android.view.ViewGroup) : android.support.v7.widget.RecyclerView.ViewHolder(
            parent.inflate(com.erostech.kiki.R.layout.item_pn_layout_small)), DestroyableView {

        private val TAG = com.erostech.kiki.ui.adapters.delegates.pubnative.PNSmallLayoutAdDelegateAdapter.PNSmallLayoutViewHolder::class.java!!.getSimpleName()

        var adCell: com.erostech.kiki.models.PNSmallLayoutAdCell? = null

        fun bind(item: com.erostech.kiki.models.PNSmallLayoutAdCell) = with(itemView) {
            adCell = item
            adCell!!.request.setLoadListener(object: net.pubnative.sdk.layouts.PNLayout.LoadListener {
                override fun onPNLayoutLoadFail(layout: net.pubnative.sdk.layouts.PNLayout?, exception: java.lang.Exception?) {
                    android.util.Log.d(TAG, exception!!.message ?: "")
                }

                override fun onPNLayoutLoadFinish(layout: net.pubnative.sdk.layouts.PNLayout?) {
                    val layout = adCell!!.request.getView(itemView.context)
                    adCell!!.request.startTrackingView()
                    (itemView as android.widget.FrameLayout).addView(layout)
                }
            })
            adCell!!.request.load(itemView.context, com.erostech.kiki.API_TOKEN, adCell!!.placementID)
        }

        override fun destroy() {
            adCell?.request?.stopTrackingView()
        }
    }
}