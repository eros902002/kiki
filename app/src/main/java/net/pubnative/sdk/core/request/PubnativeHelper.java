package net.pubnative.sdk.core.request;

/**
 * Created by erosgarciaponte on 22.06.17.
 */

public class PubnativeHelper {
    public static String getContentInfoIconUrl(PNAdModel model) {
        return model.getContentInfoImageUrl();
    }

    public static String getContentInfoClickUrl(PNAdModel model) {
        return model.getContentInfoClickUrl();
    }

    public static String getIconUrl(PNAdModel model) {
        return model.getIconUrl();
    }

    public static String getBannerUrl(PNAdModel model) {
        return model.getBannerUrl();
    }
}
