package com.efortshub.webview.weblibrary;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
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

}
