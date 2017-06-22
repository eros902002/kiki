package com.mopub.nativeads;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import net.pubnative.sdk.core.exceptions.PNException;
import net.pubnative.sdk.core.request.PNAdModel;
import net.pubnative.sdk.core.request.PNRequest;
import net.pubnative.sdk.core.request.PubnativeHelper;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by erosgarciaponte on 22.06.17.
 */

public class PubNativeAdapter extends CustomEventNative implements PNRequest.Listener {
    private static final String TAG = PubNativeAdapter.class.getSimpleName();
    private static final String SERVER_APP_TOKEN        = "app_token";
    private static final String SERVER_PLACEMENT_NAME   = "placement_name";

    private Context mContext;
    private CustomEventNativeListener mEventNativeListener;

    @Override
    protected void loadNativeAd(@NonNull Context context,
                                @NonNull CustomEventNativeListener customEventNativeListener,
                                @NonNull Map<String, Object> localExtras,
                                @NonNull Map<String, String> serverExtras) {
        if (context == null || customEventNativeListener == null) {
            customEventNativeListener.onNativeAdFailed(NativeErrorCode.NATIVE_ADAPTER_CONFIGURATION_ERROR);
        } else {
            mContext = context;
            mEventNativeListener = customEventNativeListener;
            if (extrasAreValid(serverExtras)) {
                doAdRequest(serverExtras);
            } else {
                mEventNativeListener.onNativeAdFailed(NativeErrorCode.NATIVE_ADAPTER_CONFIGURATION_ERROR);
            }
        }
    }

    private void invokeOnFail(NativeErrorCode error) {
        CustomEventNativeListener listener = mEventNativeListener;
        mEventNativeListener = null;
        if (listener != null) {
            listener.onNativeAdFailed(error);
        }
    }

    private void doAdRequest(Map<String, String> extras) {
        String appToken = extras.get(SERVER_APP_TOKEN);
        String placementName = extras.get(SERVER_PLACEMENT_NAME);

        PNRequest request = new PNRequest();
        request.setCacheResources(false);
        request.start(mContext, appToken, placementName, this);
    }

    private boolean extrasAreValid(final Map<String, String> serverExtras) {
        boolean result = false;

        String appToken = serverExtras.get(SERVER_APP_TOKEN);
        String placementName = serverExtras.get(SERVER_PLACEMENT_NAME);

        if (!TextUtils.isEmpty(appToken) && !TextUtils.isEmpty(placementName)) {
            result = true;
        }
        return result;
    }

    //==============================================================================================
    // PubnativeRequest.Listener
    //==============================================================================================

    @Override
    public void onPNRequestLoadFinish(PNRequest pnRequest, PNAdModel pnAdModel) {
        new PubnativeAdWrapper(mContext,
                pnAdModel,
                new ImpressionTracker(mContext),
                new NativeClickHandler(mContext),
                mEventNativeListener);
    }

    @Override
    public void onPNRequestLoadFail(PNRequest pnRequest, Exception exception) {
        if (exception == PNException.REQUEST_NO_FILL) {
            invokeOnFail(NativeErrorCode.NETWORK_NO_FILL);
        } else {
            invokeOnFail(NativeErrorCode.UNSPECIFIED);
        }
    }
    //==============================================================================================
    // PubnativeAdWrapper
    //==============================================================================================

    private class PubnativeAdWrapper extends StaticNativeAd implements NativeImageHelper.ImageListener,
            PNAdModel.Listener {

        private final String TAG = PubnativeAdWrapper.class.getSimpleName();
        private Context mContext;
        private PNAdModel mAdModel;
        private CustomEventNativeListener mCustomEventNativeListener;
        private ImpressionTracker mImpressionTracker;
        private NativeClickHandler mNativeClickHandler;

        public PubnativeAdWrapper(Context context, PNAdModel adModel, ImpressionTracker impressionTracker, NativeClickHandler nativeClickHandler, CustomEventNativeListener eventNativeListener) {
            mContext = context.getApplicationContext();
            mImpressionTracker = impressionTracker;
            mNativeClickHandler = nativeClickHandler;
            mAdModel = adModel;
            mCustomEventNativeListener = eventNativeListener;
            fillData();
            cacheImages();
        }

        protected void fillData() {
            setTitle(mAdModel.getTitle());
            setText(mAdModel.getDescription());
            setIconImageUrl(PubnativeHelper.getIconUrl(mAdModel));
            setMainImageUrl(PubnativeHelper.getBannerUrl(mAdModel));
            setCallToAction(mAdModel.getCallToAction());
            setStarRating((double) mAdModel.getStarRating());
            setPrivacyInformationIconImageUrl(PubnativeHelper.getContentInfoIconUrl(mAdModel));
            setPrivacyInformationIconClickThroughUrl(PubnativeHelper.getContentInfoClickUrl(mAdModel));

        }

        protected void cacheImages() {
            String[] imgUrls = new String[]{getIconImageUrl(), getMainImageUrl()};
            NativeImageHelper.preCacheImages(mContext, Arrays.asList(imgUrls), this);
        }

        protected void invokeLoaded(PubnativeAdWrapper wrapper) {
            CustomEventNativeListener listener = mCustomEventNativeListener;
            mCustomEventNativeListener = null;
            if (listener != null) {
                listener.onNativeAdLoaded(wrapper);
            }
        }

        protected void invokeFailed(NativeErrorCode errorCode) {
            CustomEventNativeListener listener = mCustomEventNativeListener;
            mCustomEventNativeListener = null;
            if (listener != null) {
                listener.onNativeAdFailed(errorCode);
            }
        }

        @Override
        public void prepare(final View view) {
            mImpressionTracker.addView(view, this);
            mNativeClickHandler.setOnClickListener(view, this);
            mAdModel.setListener(this);
            mAdModel.startTracking((ViewGroup) view);
            passClicksToRootView((ViewGroup) view);
        }

        @Override
        public void clear(final View view) {
            mImpressionTracker.removeView(view);
            mNativeClickHandler.clearOnClickListener(view);
            mAdModel.stopTracking();
        }

        @Override
        public void destroy() {
            mImpressionTracker.destroy();
        }

        @Override
        public void onImagesCached() {
            invokeLoaded(this);
        }

        @Override
        public void onImagesFailedToCache(NativeErrorCode errorCode) {
            invokeFailed(errorCode);
        }

        private void passClicksToRootView(ViewGroup layout) {
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                if (child instanceof ViewGroup) {
                    passClicksToRootView((ViewGroup) child);
                }
                child.setFocusable(false);
                child.setClickable(false);
            }
        }

        //==============================================================================================
        // PubnativeAdModel.Listener
        //==============================================================================================

        @Override
        public void onPNAdImpression(PNAdModel pnAdModel) {
            Log.v(TAG, "onPNAdImpression");
            notifyAdImpressed();
        }

        @Override
        public void onPNAdClick(PNAdModel pnAdModel) {
            Log.v(TAG, "onPNAdClick");
            notifyAdClicked();
        }
    }
}
