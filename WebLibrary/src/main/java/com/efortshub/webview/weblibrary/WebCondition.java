package com.efortshub.webview.weblibrary;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;

public class WebCondition implements WebListener{
    public static int lastSyncedProgress =0;
    private static WebCondition webCondition;
    private static HbWebViewClient hbWebViewClient;
    private static HbWebChromeClient hbWebChromeClient;
    private static WebListener webListener;

    public HbWebViewClient getHbWebViewClient() {
        return hbWebViewClient;
    }

    public HbWebChromeClient getHbWebChromeClient() {
        return hbWebChromeClient;
    }


    public static WebCondition getInstance(@NonNull WebListener listener){
        webListener = listener;

        if (webCondition==null){
            webCondition = new WebCondition();
        }
        if (hbWebViewClient==null) {
            hbWebViewClient = new HbWebViewClient(webCondition);
        }
        if (hbWebChromeClient==null) {
            hbWebChromeClient= new HbWebChromeClient(webCondition);
        }



        return webCondition;
    }

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {

        webListener.onPageStarted(webView, url, favicon);


    }

    @Override
    public void onPageStopped(WebView webView, String url) {
        webListener.onPageStopped(webView, url);

    }

    @Override
    public void onProgressChanged(android.webkit.WebView webView, int progress) {
        lastSyncedProgress = progress;

    }

    @Override
    public void onPermissionRequest(PermissionRequest permissionRequest) {
        PermissionRequest request = permissionRequest;

        webListener.onPermissionRequest(permissionRequest);


    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        webListener.onReceivedSslError(view, handler, error);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return webListener.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }


    public static void applyWebSettings(com.efortshub.webview.weblibrary.WebView webView){
       WebSettings w =  webView.getSettings();
       w.setJavaScriptEnabled(true);
       w.setSavePassword(true);
       w.setUseWideViewPort(true);
       w.setAllowContentAccess(true);
       w.setAllowFileAccess(true);
       w.setAllowFileAccessFromFileURLs(true);
       w.setAllowUniversalAccessFromFileURLs(true);
       w.setCacheMode(WebSettings.LOAD_DEFAULT);
       w.setDatabaseEnabled(true);
       w.setSaveFormData(true);
       w.setRenderPriority(WebSettings.RenderPriority.HIGH);
       w.setSupportZoom(true);
       w.setDisplayZoomControls(false);
       w.setBuiltInZoomControls(true);
       w.setDatabasePath(webView.getContext().getFilesDir().getPath());
       w.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            w.setMediaPlaybackRequiresUserGesture(false);
        }
        w.setDomStorageEnabled(true);
        w.setJavaScriptCanOpenWindowsAutomatically(true);


    }

}
