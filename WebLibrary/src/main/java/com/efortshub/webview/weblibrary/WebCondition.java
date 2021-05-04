package com.efortshub.webview.weblibrary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class WebCondition implements WebListener{
    public static int lastSyncedProgress =0;
    private static WebCondition webCondition;
    private static HbWebViewClient hbWebViewClient;
    private static HbWebChromeClient hbWebChromeClient;
    private static WebListener webListener;
    private static final String PERMISSION_AUDIO = "android.webkit.resource.AUDIO_CAPTURE";
    public static final int PERMISSION_CODE = 1129238;

    public static void requestNewPermission(Context context, Activity activity, String[] permissions) {

        List<String> notGrantedPermissions = new ArrayList<>();

        for (String p: permissions){
            if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED){
                notGrantedPermissions.add(p);
            }
        }

        if (!notGrantedPermissions.isEmpty()){
            ActivityCompat.requestPermissions(activity, (String[]) notGrantedPermissions.toArray(), PERMISSION_CODE);
        }
    }

    public static List<String> getNotGrantedPermissions(String[] permissions, int[] grantResults) {
        List<String> list = new ArrayList<>();

        for (int i=0; i<permissions.length; i++){
            try{
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED){

                    list.add(permissions[i]);

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }



        return list;
    }

    public static void showPermissionNotGrantedDialog(@NonNull Context context, @NonNull Activity activity,@NonNull  List<String> notGrantedPermissions) {
        new AlertDialog.Builder(context)
                .setTitle("Permission Required !")
                .setMessage(notGrantedPermissions.size()+" permission need to be granted")
                .setPositiveButton("Grant", (dialog, which) -> requestNewPermission(context, activity, (String[]) notGrantedPermissions.toArray()))
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                }).create().show();


    }

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

        webListener.onProgressChanged(webView, progress);

    }

    @Override
    public void onPermissionRequest(PermissionRequest permissionRequest) {
        PermissionRequest request = permissionRequest;

        List<String> requiredPermissions = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (String s: request.getResources()){
                if (s.equals(PERMISSION_AUDIO)){
                    requiredPermissions.add(Manifest.permission.RECORD_AUDIO);
                    requiredPermissions.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
                }
            }

            webListener.checkPermission((String[]) requiredPermissions.toArray());
        }

        //webListener.onPermissionRequest(permissionRequest);


    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        webListener.onReceivedSslError(view, handler, error);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return webListener.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }

    @Override
    public void checkPermission(String... permissions) {
        webListener.checkPermission(permissions);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        webListener.onReceivedError(view, errorCode, description, failingUrl);
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
