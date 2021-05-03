package com.efortshub.webview.weblibrary;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class WebView extends android.webkit.WebView {
    /**
     * Construct a new WebView with a Context object.
     *
     * @param context A Context object used to access application assets.
     */
    public WebView(@NonNull Context context) {
        super(context);
    }

    /**
     * Construct a new WebView with layout parameters.
     *
     * @param context A Context object used to access application assets.
     * @param attrs   An AttributeSet passed to our parent.
     */
    public WebView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Construct a new WebView with layout parameters and a default style.
     *
     * @param context      A Context object used to access application assets.
     * @param attrs        An AttributeSet passed to our parent.
     * @param defStyleAttr
     */
    public WebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WebView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
