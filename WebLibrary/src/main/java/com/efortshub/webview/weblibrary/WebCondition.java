package com.efortshub.webview.weblibrary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.DOWNLOAD_SERVICE;

public class WebCondition implements WebListener{
    public static int lastSyncedProgress =0;
    private static WebCondition webCondition;
    private static HbWebViewClient hbWebViewClient;
    private static HbWebChromeClient hbWebChromeClient;
    private static WebListener webListener;
    public static final int PERMISSION_CODE = 1076;

    public static boolean requestNewPermission(Context context, Activity activity, List<String> permissions) {

        List<String> notGrantedPermissions = new ArrayList<>();

        for (String p: permissions){
            if (ContextCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED){
                notGrantedPermissions.add(p);
            }
        }

        String[] arr = new String[notGrantedPermissions.size()];
        for (int i=0; i<notGrantedPermissions.size(); i++){
            arr[i] = notGrantedPermissions.get(i);
        }

        if (!notGrantedPermissions.isEmpty()){
            ActivityCompat.requestPermissions(activity,arr, PERMISSION_CODE);

            return false;

        }else return true;
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
                .setPositiveButton("Grant", (dialog, which) -> requestNewPermission(context, activity,  notGrantedPermissions))
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                }).create().show();


    }

    public static void HandleonShowFileChooser(Activity activity, WebChromeClient.FileChooserParams fileChooserParams) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String[] s =  fileChooserParams.getAcceptTypes();
            for (String a: s){
                Log.d("webloglf", "onShowFileChooser: s :"+a);
            }
            Intent intent = fileChooserParams.createIntent();
           ActivityCompat.startActivityForResult(activity, intent, 32, null);
        }
    }

    public HbWebViewClient getHbWebViewClient() {
        return hbWebViewClient;
    }

    public HbWebChromeClient getHbWebChromeClient() {
        return hbWebChromeClient;
    }

    public void setHbWebViewClient(HbWebViewClient webViewClient) {
        hbWebViewClient = webViewClient;
    }

    public void setHbWebChromeClient(HbWebChromeClient webChromeClient) {
        hbWebChromeClient = webChromeClient;
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

    public static WebCondition getInstance(@NonNull WebListener listener, HbWebViewClient webViewClient, HbWebChromeClient webChromeClient){
        webListener = listener;

        if (webCondition==null){
            webCondition = new WebCondition();
        }

        hbWebViewClient = webViewClient;
        hbWebChromeClient= webChromeClient;


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


        PermissionRequest request = permissionRequest;

        List<String> requiredPermissions = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (String s: request.getResources()){
                Log.d("webloglp", "onPermissionRequest: "+s);
                if (s.equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)){
                    requiredPermissions.add(Manifest.permission.RECORD_AUDIO);
                    requiredPermissions.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);

                }else if(s.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE)){
                    requiredPermissions.add(Manifest.permission.CAMERA);

                }else if (s.equals(PermissionRequest.RESOURCE_MIDI_SYSEX)){


                }else  if (s.equals(PermissionRequest.RESOURCE_PROTECTED_MEDIA_ID)){


                }
            }
           /* String[] r = new String[requiredPermissions.size()];
            for (int i=0; i<requiredPermissions.size(); i++){
                r[i] = requiredPermissions.get(i);

            }
*/
            webListener.checkPermission(request, requiredPermissions);


        }

        //webListener.onPermissionRequest(permissionRequest);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            request.grant(request.getResources());
        }
*/
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
    public boolean checkPermission(PermissionRequest request, List<String> permissions) {
        return webListener.checkPermission(request, permissions);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        webListener.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return webListener.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return webListener.shouldOverrideUrlLoading(view, request);
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
       w.setLoadsImagesAutomatically(true);
       w.setDatabaseEnabled(true);
       w.setSaveFormData(true);
       w.setRenderPriority(WebSettings.RenderPriority.HIGH);
       w.setSupportZoom(true);
       w.setDisplayZoomControls(false);
       w.setBuiltInZoomControls(false);
       w.setDatabasePath(webView.getContext().getFilesDir().getPath());
       w.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            w.setMediaPlaybackRequiresUserGesture(false);
        }


        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        }else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }


        w.setDomStorageEnabled(true);
        w.setJavaScriptCanOpenWindowsAutomatically(true);
        w.setAppCacheEnabled(true);


        String userAgent = String.format("%s [%s/%s]", w.getUserAgentString(), "App Android", Build.VERSION.CODENAME);


        userAgent = "Mozilla/5.0 (Linux; Android 4.4.4; One Build/KTU84L.H4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.135 Mobile Safari/537.36";

        w.setUserAgentString(userAgent);




        webView.setDownloadListener((s, s1, s2, s3, l) -> {
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(s));
            request.setMimeType(s3);
            String cookies = CookieManager.getInstance().getCookie(s);
            request.addRequestHeader("cookie",cookies);
            request.addRequestHeader("User-Agent",s1);
            request.setDescription("Downloading File...");
            request.setTitle(URLUtil.guessFileName(s,s2,s3));
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,URLUtil.guessFileName(s,s2,s3));
            DownloadManager downloadManager =(DownloadManager) webView.getContext().getSystemService(DOWNLOAD_SERVICE);
            downloadManager.enqueue(request);
            Toast.makeText(webView.getContext(),"Downloading File",Toast.LENGTH_SHORT).show();
        });




    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {


    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {


    }
}
