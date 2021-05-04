package com.efortshub.webview.weblibrary;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import java.util.List;

public interface WebListener {

    void onPageStarted(WebView webView, String url, Bitmap favicon);
    void onPageStopped(WebView webView, String url);
    void onProgressChanged(android.webkit.WebView webView, int progress);
    void onPermissionRequest(PermissionRequest permissionRequest);

    void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error);

    boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams);

    boolean checkPermission(PermissionRequest request, List<String> permissions);

    void onReceivedError(WebView view, int errorCode, String description, String failingUrl);

    boolean shouldOverrideUrlLoading(WebView view, String url);
    boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request);
}
