package com.example.k_travel.utils;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewUtil {

    public static void setWebViewSetting(WebView webView){
        WebSettings settings = webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

//        webView.removeJavascriptInterface("searchBoxJavaBridge_");
//        webView.removeJavascriptInterface("accessibility");
//        webView.removeJavascriptInterface("accessibilityTraversal");
    }

}
