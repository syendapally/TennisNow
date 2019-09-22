package com.example.syend.tennisnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Search extends AppCompatActivity {
    String p1;
    String p2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            p1= extras.getString("player1");
            p2 = extras.getString("player2");

        }
        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("https://www.youtube.com/results?search_query=" +p1 +"+vs+"+p2);
    }
}
