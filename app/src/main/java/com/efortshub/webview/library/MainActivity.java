package com.efortshub.webview.library;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import com.efortshub.webview.library.databinding.ActivityMainBinding;
import com.efortshub.webview.weblibrary.WebCondition;
import com.efortshub.webview.weblibrary.WebListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "weblogl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());







        binding.wv.setWebCondition(WebCondition.getInstance(new WebListener() {
            @Override
            public void onPageStarted(WebView webView, String url, Bitmap favicon) {

                Log.d(TAG, "onPageStarted: ");

            }

            @Override
            public void onPageStopped(WebView webView, String url) {
                Log.d(TAG, "onPageStopped: ");

            }

            @Override
            public void onProgressChanged(WebView webView, int progress) {

                Log.d(TAG, "onProgressChanged: ");

            }

            @Override
            public void onPermissionRequest(PermissionRequest permissionRequest) {
                Log.d(TAG, "onPermissionRequest: ");

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.d(TAG, "onReceivedSslError: ");

            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

                Log.d(TAG, "onShowFileChooser: ");
                return false;
            }
        }));






    }
}