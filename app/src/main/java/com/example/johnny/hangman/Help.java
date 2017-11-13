package com.example.johnny.hangman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

/**
 * Created by Johnny on 24/10/17.
 */

public class Help extends AppCompatActivity implements View.OnClickListener {

        Button menu;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        menu = (Button) findViewById(R.id.menuButton);
        menu.setOnClickListener(this);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        myWebView.loadUrl("https://en.wikipedia.org/wiki/Hangman_(game)");
        }

public void onClick(View v) {
        if (v == menu) {
        onBackPressed();
        }
    }
}
