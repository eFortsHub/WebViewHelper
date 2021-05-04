package com.efortshub.webview.library;

import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
       // binding.wv.loadUrl("https://image.online-convert.com/convert-to-jpg");


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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                       String[] s =  fileChooserParams.getAcceptTypes();
                       for (String a: s){
                           Log.d(TAG, "onShowFileChooser: s :"+a);
                       }
                       Intent intent = fileChooserParams.createIntent();
                       startActivityForResult(intent, 32);
                    }
                    return true;
                }

                @Override
                public boolean checkPermission(PermissionRequest request, List<String> permissions) {

                    Log.d(TAG, "checkPermission: "+permissions.get(0));
                   boolean isAllGranted =  WebCondition.requestNewPermission(

                            MainActivity.this,
                            MainActivity.this,
                                    permissions);

                   if (isAllGranted){
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                           runOnUiThread(()-> request.grant(request.getResources()));

                       }
                   }

                   return isAllGranted;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 32) {

            Log.d(TAG, "onActivityResult: reuslt got : "+data.getData());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}