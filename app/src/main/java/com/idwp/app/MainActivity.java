
package com.idwp.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
    private WebView webView;
    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showWelcome();
        loadWebsite();
    }

    private void showWelcome() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(32, 32, 32, 32);

        GradientDrawable bg = new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[] {
                Color.rgb(10, 48, 120),
                Color.rgb(8, 20, 55),
                Color.rgb(3, 9, 30)
            }
        );
        root.setBackground(bg);

        TextView logo = new TextView(this);
        logo.setText("IDWP");
        logo.setTextSize(48);
        logo.setTextColor(Color.WHITE);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);

        TextView tagline = new TextView(this);
        tagline.setText("LEARN  •  WORK  •  GROW");
        tagline.setTextSize(14);
        tagline.setTextColor(Color.CYAN);
        tagline.setGravity(Gravity.CENTER);

        TextView welcome = new TextView(this);
        welcome.setText("Welcome to IDWP");
        welcome.setTextSize(22);
        welcome.setTextColor(Color.WHITE);
        welcome.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        welcome.setGravity(Gravity.CENTER);

        ProgressBar progress = new ProgressBar(this);
        progress.setIndeterminate(true);

        root.addView(logo);
        root.addView(tagline);
        root.addView(welcome);
        root.addView(progress);

        setContentView(root);
    }

    private void loadWebsite() {
        webView = new WebView(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!isFinishing() && !isDestroyed()) {
                    setContentView(webView);
                }
            }
        });

        webView.loadUrl("https://idwp-nxwk.vercel.app/");
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
