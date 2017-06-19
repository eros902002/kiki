package com.erostech.kiki.ui.fragments

import android.support.v4.app.Fragment
import rx.subscriptions.CompositeSubscription

/**
 * Created by erosgarciaponte on 19.06.17.
 */
open class RxBaseFragment() : Fragment() {
    protected var subscriptions = CompositeSubscription()

    override fun onResume() {
        super.onResume()
        subscriptions = CompositeSubscription()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }
}