package com.efortshub.webview.library;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.efortshub.webview.library.databinding.ActivityMainBinding;
import com.efortshub.webview.weblibrary.WebCondition;
import com.efortshub.webview.weblibrary.WebListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "weblogl";
    private WebListener webListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeWebView();



        binding.wv.loadUrl("http://google.com");


    }

    private void initializeWebView() {

        WebCondition.applyWebSettings(binding.wv);

        if (webListener==null) {
            webListener = new WebListener() {
                @Override
                public void onPageStarted(WebView webView, String url, Bitmap favicon) {

                    Log.d(TAG, "onPageStarted: "+url);

                }

                @Override
                public void onPageStopped(WebView webView, String url) {
                    Log.d(TAG, "onPageStopped: ");

                }

                @Override
                public void onProgressChanged(WebView webView, int progress) {

                    Log.d(TAG, "onProgressChanged: "+progress);

                }

                @Override
                public void onPermissionRequest(PermissionRequest permissionRequest) {
                /*    Log.d(TAG, "onPermissionRequest: ");
                    Log.d(TAG, "onPermissionRequest: "+permissionRequest);
                    Log.d(TAG, "onPermissionRequest.toString : "+permissionRequest.toString());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Log.d(TAG, "origin: "+permissionRequest.getOrigin());
                        Log.d(TAG, "path: "+permissionRequest.getOrigin().getPath());
                        Log.d(TAG, "query: "+permissionRequest.getOrigin().getQuery());
                        Log.d(TAG, "host: "+permissionRequest.getOrigin().getHost());
                        Log.d(TAG, "authority: "+permissionRequest.getOrigin().getAuthority());
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        for (String s: permissionRequest.getResources()){
                            Log.d(TAG, "onPermissionRequest: resource :"+s);
                        }

                        Log.d(TAG, "onPermissionRequest: "+permissionRequest.getResources());
                        Log.d(TAG, "onPermissionRequest: "+permissionRequest.getResources().toString());
                    }
*/

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

                @Override
                public void checkPermission(String... permissions) {

                    WebCondition.requestNewPermission(

                            MainActivity.this,
                            MainActivity.this,
                                    permissions);

                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                }
            };

        }

        binding.wv.setWebCondition(
                WebCondition.getInstance(
                        webListener
                )
        );


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode== WebCondition.PERMISSION_CODE){
            List<String> notGrantedPermissions = WebCondition.getNotGrantedPermissions(permissions, grantResults);

            if (!notGrantedPermissions.isEmpty()){
             WebCondition.showPermissionNotGrantedDialog(MainActivity.this,MainActivity.this,  notGrantedPermissions);
            }

        }
    }
}