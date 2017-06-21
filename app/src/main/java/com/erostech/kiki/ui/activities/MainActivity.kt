package com.erostech.kiki.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.erostech.kiki.ADMOB_APP_ID
import com.erostech.kiki.R
import com.google.android.gms.ads.MobileAds
import com.mopub.mobileads.MoPubConversionTracker

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            MoPubConversionTracker().reportAppOpen(this)
            MobileAds.initialize(this, ADMOB_APP_ID)
        }
    }
}
