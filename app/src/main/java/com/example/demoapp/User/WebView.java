package com.example.demoapp.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebViewClient;

import com.example.demoapp.R;

public class WebView extends AppCompatActivity {

    android.webkit.WebView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String url = getIntent().getStringExtra("SitioWeb");
        wb = findViewById(R.id.wv1);

        wb.getSettings().setLoadsImagesAutomatically(true);
        wb.getSettings().setJavaScriptEnabled(true);
        wb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        wb.setWebViewClient(new WebViewClient());

        wb.loadUrl(url);
    }
}