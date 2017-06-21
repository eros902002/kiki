package com.erostech.kiki.ui.adapters.delegates

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.erostech.kiki.R
import com.erostech.kiki.ui.adapters.ViewType
import com.erostech.kiki.util.inflate

/**
 * Created by erosgarciaponte on 19.06.17.
 */
class LoadingDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = LoadingViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    class LoadingViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.item_loading))
}