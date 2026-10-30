
package com.idwp.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceError;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.Button;

public class MainActivity extends Activity {

    private WebView webView;
    private FrameLayout root;
    private LinearLayout loadingScreen;
    private boolean pageLoaded = false;

    private int dp(float n) {
        return (int) (n * getResources()
                .getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openWebsite();
    }

    private void openWebsite() {

        root = new FrameLayout(this);
        root.setBackgroundColor(Color.rgb(8, 20, 55));

        webView = new WebView(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(false);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        root.addView(webView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));

        createLoadingScreen();

        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url,
                                      android.graphics.Bitmap favicon) {
                pageLoaded = false;
                loadingScreen.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                pageLoaded = true;
                loadingScreen.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view,
                    WebResourceRequest request,
                    WebResourceError error) {

                if (request.isForMainFrame()) {
                    showRetry();
                }
            }
        });

        setContentView(root);
        webView.loadUrl("https://idwp-nxwk.vercel.app/");
    }

    private void createLoadingScreen() {

        loadingScreen = new LinearLayout(this);
        loadingScreen.setOrientation(LinearLayout.VERTICAL);
        loadingScreen.setGravity(Gravity.CENTER);
        loadingScreen.setBackgroundColor(Color.rgb(8, 20, 55));

        TextView title = new TextView(this);
        title.setText("IDWP");
        title.setTextColor(Color.WHITE);
        title.setTextSize(34);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        loadingScreen.addView(title);

        TextView tagline = new TextView(this);
        tagline.setText("LEARN  •  WORK  •  GROW");
        tagline.setTextColor(Color.CYAN);
        tagline.setTextSize(14);
        tagline.setPadding(0, dp(12), 0, dp(24));
        tagline.setGravity(Gravity.CENTER);

        loadingScreen.addView(tagline);

        ProgressBar progress = new ProgressBar(this);
        loadingScreen.addView(progress);

        root.addView(loadingScreen, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        ));
    }

    private void showRetry() {

        loadingScreen.removeAllViews();

        TextView message = new TextView(this);
        message.setText("IDWP load nahi ho paya.\nInternet check karein.");
        message.setTextColor(Color.WHITE);
        message.setTextSize(17);
        message.setGravity(Gravity.CENTER);
        message.setPadding(dp(20), dp(20), dp(20), dp(20));

        loadingScreen.addView(message);

        Button retry = new Button(this);
        retry.setText("Retry");

        retry.setOnClickListener(v -> {
            loadingScreen.removeAllViews();
            createLoadingContentsAgain();
            webView.loadUrl("https://idwp-nxwk.vercel.app/");
        });

        loadingScreen.addView(retry);
        loadingScreen.setVisibility(View.VISIBLE);
    }

    private void createLoadingContentsAgain() {

        TextView title = new TextView(this);
        title.setText("IDWP");
        title.setTextColor(Color.WHITE);
        title.setTextSize(30);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        loadingScreen.addView(title);

        ProgressBar progress = new ProgressBar(this);
        loadingScreen.addView(progress);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.stopLoading();
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
