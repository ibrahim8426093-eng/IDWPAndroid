
package com.idwp.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.graphics.Color;
import android.view.Gravity;

public class MainActivity extends Activity {

    private WebView webView;
    private FrameLayout root;
    private LinearLayout loadingScreen;

    private static final String WEBSITE =
            "https://idwp-nxwk.vercel.app/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        root = new FrameLayout(this);
        setContentView(root);

        showLoading();
        openWebsite();
    }

    private int dp(int value) {
        return (int) (value *
                getResources().getDisplayMetrics().density + 0.5f);
    }

    private void showLoading() {
        loadingScreen = new LinearLayout(this);
        loadingScreen.setOrientation(LinearLayout.VERTICAL);
        loadingScreen.setGravity(Gravity.CENTER);
        loadingScreen.setBackgroundColor(Color.rgb(8, 20, 55));

        TextView title = new TextView(this);
        title.setText("IDWP");
        title.setTextSize(34);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, 1);

        TextView subtitle = new TextView(this);
        subtitle.setText("LEARN  •  WORK  •  GROW");
        subtitle.setTextSize(14);
        subtitle.setTextColor(Color.CYAN);
        subtitle.setGravity(Gravity.CENTER);

        ProgressBar progress = new ProgressBar(this);

        loadingScreen.addView(title);
        loadingScreen.addView(subtitle);
        loadingScreen.addView(progress);

        root.addView(loadingScreen,
                new FrameLayout.LayoutParams(
                        -1, -1));
    }

    private void openWebsite() {
        try {
            webView = new WebView(this);

            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);

            webView.setWebViewClient(new WebViewClient() {

                @Override
                public void onPageFinished(WebView view, String url) {
                    root.removeView(loadingScreen);
                    if (webView.getParent() == null) {
                        root.addView(webView,
                                new FrameLayout.LayoutParams(-1, -1));
                    }
                }

                @Override
                public void onReceivedError(
                        WebView view,
                        WebResourceRequest request,
                        android.webkit.WebResourceError error) {

                    if (request.isForMainFrame()) {
                        showError();
                    }
                }
            });

            webView.setWebChromeClient(new WebChromeClient());
            webView.loadUrl(WEBSITE);

        } catch (Exception e) {
            showError();
        }
    }

    private void showError() {
        root.removeAllViews();

        LinearLayout screen = new LinearLayout(this);
        screen.setOrientation(LinearLayout.VERTICAL);
        screen.setGravity(Gravity.CENTER);
        screen.setPadding(dp(24), dp(24), dp(24), dp(24));
        screen.setBackgroundColor(Color.rgb(8, 20, 55));

        TextView message = new TextView(this);
        message.setText("IDWP load nahi ho paya. Internet check karein.");
        message.setTextColor(Color.WHITE);
        message.setTextSize(17);
        message.setGravity(Gravity.CENTER);

        Button retry = new Button(this);
        retry.setText("Retry");
        retry.setOnClickListener(v -> {
            root.removeAllViews();
            showLoading();
            if (webView != null) {
                webView.loadUrl(WEBSITE);
            } else {
                openWebsite();
            }
        });

        screen.addView(message);
        screen.addView(retry);
        root.addView(screen,
                new FrameLayout.LayoutParams(-1, -1));
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
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}
