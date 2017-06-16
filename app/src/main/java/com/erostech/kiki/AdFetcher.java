package com.erostech.kiki;

import android.content.Context;
import android.util.Log;

import net.pubnative.api.core.request.model.PNAPIAdModel;
import net.pubnative.sdk.core.adapter.request.PubnativeLibraryAdModel;
import net.pubnative.sdk.core.request.PNAdModel;
import net.pubnative.sdk.core.request.PNRequest;

/**
 * Created by erosgarciaponte on 16.06.17.
 */

public class AdFetcher {
    public void fetchAds(Context context) {
        PNAdModel adModel = new PubnativeLibraryAdModel(context, new PNAPIAdModel());
    }
}
