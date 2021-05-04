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
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.efortshub.webview.library.databinding.ActivityMainBinding;
import com.efortshub.webview.weblibrary.HbWebChromeClient;
import com.efortshub.webview.weblibrary.HbWebViewClient;
import com.efortshub.webview.weblibrary.WebCondition;
import com.efortshub.webview.weblibrary.WebListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final String TAG = "weblogl";
    private WebListener webListener;
    private PermissionRequest permissionRequest;
    private ValueCallback<Uri[]> webFilePathCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeWebView();



       // binding.wv.loadUrl("https://amarassistant.com");
       // binding.wv.loadUrl("http://google.com");
        binding.wv.loadUrl("https://gamboll.in/?rId=6766&uType=guessr&uId=100&uName=Bappi");
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
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    Log.d(TAG, "onReceivedSslError: ");


                }

                @Override
                public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {

                    Log.d(TAG, "onShowFileChooser: ");

                    webFilePathCallback = filePathCallback;

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

                    permissionRequest = request;

                    Log.d(TAG, "checkPermission: "+permissions.get(0));
                   boolean isAllGranted =  WebCondition.requestNewPermission(

                            MainActivity.this,
                            MainActivity.this,
                                    permissions);

                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        request.grant(request.getResources());

                    }

                    Log.d(TAG, "checkPermission: "+isAllGranted);

                   return isAllGranted;
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                    Toast.makeText(MainActivity.this, description, Toast.LENGTH_SHORT).show();

                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    return false;
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

                    return false;
                }
            };

        }
        WebCondition webCondition = WebCondition.getInstance(webListener);

        binding.wv.setWebCondition(webCondition);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode== WebCondition.PERMISSION_CODE){
            List<String> notGrantedPermissions = WebCondition.getNotGrantedPermissions(permissions, grantResults);

            if (!notGrantedPermissions.isEmpty()){
             WebCondition.showPermissionNotGrantedDialog(MainActivity.this,MainActivity.this,  notGrantedPermissions);
            }

            if (permissionRequest!=null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    permissionRequest.grant(permissionRequest.getResources());
                }
            }

        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 32 && resultCode==RESULT_OK) {
            Log.d(TAG, "onActivityResult: reuslt got : "+data.getData());

            if (webFilePathCallback!=null){
                webFilePathCallback.onReceiveValue(new Uri[]{data.getData()});
            }

            webFilePathCallback = null;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}