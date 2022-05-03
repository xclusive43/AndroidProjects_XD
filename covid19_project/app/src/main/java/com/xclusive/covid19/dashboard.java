package com.xclusive.covid19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class dashboard extends AppCompatActivity {
WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//for full screen
        setContentView(R.layout.activity_dashboard);
        webView = findViewById(R.id.webview1);
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("file:///android_asset/india.html");

        String myurl="file:///android_asset/india.html";
        WebView view =(WebView) this.findViewById(R.id.webview1);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(myurl);
        view.setWebViewClient(new mybrowser());
    }
    @Override
    public void onBackPressed() {

        if(webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }

    }
    private class mybrowser extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}