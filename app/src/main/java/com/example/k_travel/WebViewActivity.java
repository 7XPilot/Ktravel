package com.example.k_travel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;

import com.example.k_travel.base.BaseActivity;
import com.example.k_travel.databinding.ActivityWebViewBinding;
import com.example.k_travel.utils.WebViewUtil;

public class WebViewActivity extends BaseActivity {

    private ActivityWebViewBinding binding;
    private String url;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_view);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        url = getIntent().getStringExtra("url");
        initWebViewSetting(url);
    }


    @SuppressLint("JavascriptInterface")
    private void initWebViewSetting(String url) {
        WebViewUtil.setWebViewSetting(binding.wv);
        binding.wv.setWebViewClient(new MyWebViewClient());
        binding.wv.setWebChromeClient(new MyChromeWebClient());
        binding.wv.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String title = view.getTitle();

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }
    }


    public class MyChromeWebClient extends WebChromeClient {
        // For Android 5.0+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Log.d(TAG, "onShowFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");

            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);

        }
    }

    private void uploadPicture() {

    }
}