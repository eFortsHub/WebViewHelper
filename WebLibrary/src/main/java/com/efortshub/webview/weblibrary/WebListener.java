package com.efortshub.webview.weblibrary;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public interface WebListener {

    void onPageStarted(WebView webView, String url, Bitmap favicon);
    void onPageStopped(WebView webView, String url);
    void onProgressChanged(android.webkit.WebView webView, int progress);
    void onPermissionRequest(PermissionRequest permissionRequest);

    void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error);

    boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams);
}
