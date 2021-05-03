package com.efortshub.webview.weblibrary;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class WebView extends android.webkit.WebView {

    public WebView(@NonNull Context context) {
        super(context);
    }

    public WebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void setWebChromeClient(@Nullable WebChromeClient client) {
        super.setWebChromeClient(client);
    }

    @Override
    public void setWebViewClient(@NonNull WebViewClient client) {
        super.setWebViewClient(client);

    }


}
