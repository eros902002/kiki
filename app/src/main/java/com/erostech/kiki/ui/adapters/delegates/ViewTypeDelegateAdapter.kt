package com.erostech.kiki.ui.adapters.delegates

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.erostech.kiki.ui.adapters.ViewType

/**
 * Created by erosgarciaponte on 19.06.17.
 * ViewTypeDelegate pattern interface.
 * This abstracts the rendering of the items on the RecyclerView
 */
interface ViewTypeDelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType)
}