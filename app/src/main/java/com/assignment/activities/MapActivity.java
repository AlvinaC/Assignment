package com.assignment.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.assignment.BaseActivity;
import com.assignment.R;
import com.assignment.util.Constants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Summary: Show map
 */
public class MapActivity extends BaseActivity {
    private WebView map_view;
    private String location_name, latitude, longitude;
    private String url;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        getIntentData();
        setToolbarWithBackNavigation(location_name);
        init();
        setUpUrl();
        setUpWebView();
        loadData();
    }

    private void loadData() {
        progress.setVisibility(View.VISIBLE);
        map_view.loadUrl(url);
    }

    private void setUpWebView() {
        map_view.getSettings().setJavaScriptEnabled(true);
        map_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //allow intents from webview to execute, by splitting the request
                if (url.startsWith("intent://") && url.contains("scheme=http")) {
                    url = Uri.decode(url);
                    String bkpUrl = null;
                    Pattern regexBkp = Pattern.compile("intent://(.*?)#");
                    Matcher regexMatcherBkp = regexBkp.matcher(url);
                    if (regexMatcherBkp.find()) {
                        bkpUrl = regexMatcherBkp.group(1);
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + bkpUrl));
                        startActivity(myIntent);
                        return true;
                    } else {
                        return false;
                    }
                }
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                progress.setVisibility(View.GONE);
            }
        });

    }

    private void setUpUrl() {
        url = "https://www.google.com/maps/search/?api=1&query=+" + latitude + "," + longitude;
    }

    private void getIntentData() {
        location_name = getIntent().getStringExtra(Constants.LOCATION_NAME);
        latitude = getIntent().getStringExtra(Constants.lATITUDE);
        longitude = getIntent().getStringExtra(Constants.LONGITUDE);

    }

    private void init() {
        map_view = (WebView) findViewById(R.id.webView);
        progress = (ProgressBar) findViewById(R.id.progressbar);
    }
}
