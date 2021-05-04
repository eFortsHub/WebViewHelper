# WebViewHelper

websites:

https://efortshub.com

https://facebook.com/efortshub

https://facebook.com/h.bappi.hp



Current version available in jitpack: [![](https://jitpack.io/v/eFortsHub/WebViewHelper.svg)](https://jitpack.io/#eFortsHub/WebViewHelper)



# Implementation 

Gradle:

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.eFortsHub:WebViewHelper:version'
	}
	
	

# Documentation


 in XML use :
 
 
    <com.efortshub.webview.weblibrary.WebView
        android:id="@+id/wv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />



in Java use:

	
	com.efortshub.webview.weblibrary.WebView webview = findViewById(R.id.wv);
	
	WebCondition.applyWebSettings(webview);
	
	
	WebListener webListener;
	
        if (webListener==null) {
            webListener = new WebListener() {
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


                    webFilePathCallback = filePathCallback;
                    WebCondition.HandleonShowFileChooser(MainActivity.this, fileChooserParams);
                    return true;
                }

                @Override
                public boolean checkPermission(PermissionRequest request, List<String> permissions) {

                    permissionRequest = request;

                   boolean isAllGranted =  WebCondition.requestNewPermission(

                            MainActivity.this,
                            MainActivity.this,
                                    permissions);

                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        request.grant(request.getResources());

                    }

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
	webview.setWebCondition(webCondition);
	
	
 

