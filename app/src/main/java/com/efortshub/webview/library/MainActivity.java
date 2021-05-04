package com.efortshub.webview.library;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.efortshub.webview.library.databinding.ActivityMainBinding;
import com.efortshub.webview.weblibrary.WebCondition;
import com.efortshub.webview.weblibrary.WebListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.wv.setWebCondition(WebCondition.getInstance(new WebListener() {
            @Override
            public void onPageStarted(WebView webView, String url, Bitmap favicon) {

            }

            @Override
            public void onPageStopped(WebView webView, String url) {

            }

            @Override
            public void onProgressChanged(WebView webView, int progress) {

            }

            @Override
            public void onPermissionRequest(PermissionRequest permissionRequest) {

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

                return false;
            }
        }));






    }
}