package com.example.ujjawaljain.earthquake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class About extends AppCompatActivity {
private static WebView c5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        openUrl();
    }
    public void openUrl(){
        c5=(WebView)findViewById(R.id.web);
        String url="https://www.britannica.com/science/earthquake-geology";
        c5.setWebViewClient(new WebViewClient());
        c5.getSettings().setLoadsImagesAutomatically(true);
        c5.getSettings().setJavaScriptEnabled(true);
        c5.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        c5.loadUrl(url);
    }
}
