package com.efortshub.webview.weblibrary;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class WebCondition implements WebListener{
    public static int lastSyncedProgress =0;

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap favicon) {


    }

    @Override
    public void onPageStopped(WebView webView, String url) {


    }

    @Override
    public void onProgressChanged(android.webkit.WebView webView, int progress) {
        lastSyncedProgress = progress;

    }

    @Override
    public void onPermissionRequest(PermissionRequest permissionRequest) {
        PermissionRequest request = permissionRequest;




    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

        return true;
    }

}
