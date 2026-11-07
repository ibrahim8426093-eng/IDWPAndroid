package com.idwp.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final String WEBSITE_URL =
            "https://idwp-nxwk.vercel.app/";

    // HTML payment flow isi backend ko use karta hai
    private static final String API_BASE =
            "https://idwp-nxwk.vercel.app";

    private FrameLayout container;
    private WebView webView;

    private int dp(float value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(8, 20, 55));
        getWindow().setNavigationBarColor(Color.rgb(3, 9, 30));

        showWelcome();
        createWebView();
    }

    private void showWelcome() {

        LinearLayout welcome = new LinearLayout(this);
        welcome.setOrientation(LinearLayout.VERTICAL);
        welcome.setGravity(Gravity.CENTER);
        welcome.setPadding(dp(18), dp(18), dp(18), dp(18));

        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(10, 48, 120),
                        Color.rgb(8, 20, 55),
                        Color.rgb(3, 9, 30)
                }
        );

        welcome.setBackground(bg);

        ImageView logo = new ImageView(this);

        try {
            logo.setImageDrawable(
                    android.graphics.drawable.Drawable.createFromStream(
                            getAssets().open("idwp_logo.png"),
                            null
                    )
            );
        } catch (Exception ignored) {
        }

        logo.setScaleType(ImageView.ScaleType.FIT_CENTER);

        // Pehle 190dp tha — compact kar diya
        welcome.addView(
                logo,
                new LinearLayout.LayoutParams(
                        dp(125),
                        dp(125)
                )
        );

        TextView title = new TextView(this);
        title.setText("Welcome to IDWP");
        title.setTextSize(18);
        title.setTextColor(Color.WHITE);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        TextView tagline = new TextView(this);
        tagline.setText("LEARN • WORK • GROW");
        tagline.setTextSize(11);
        tagline.setTextColor(Color.CYAN);
        tagline.setGravity(Gravity.CENTER);

        TextView subtitle = new TextView(this);
        subtitle.setText("Your digital work journey starts here");
        subtitle.setTextSize(11);
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);

        ProgressBar progress = new ProgressBar(this);
        progress.setIndeterminate(true);

        welcome.addView(title);

        LinearLayout.LayoutParams tagParams =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );
        tagParams.topMargin = dp(6);
        tagParams.bottomMargin = dp(12);
        welcome.addView(tagline, tagParams);

        LinearLayout.LayoutParams subParams =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );
        subParams.bottomMargin = dp(18);
        welcome.addView(subtitle, subParams);

        LinearLayout.LayoutParams progressParams =
                new LinearLayout.LayoutParams(
                        dp(30),
                        dp(30)
                );
        welcome.addView(progress, progressParams);

        container = new FrameLayout(this);
        container.addView(
                welcome,
                new FrameLayout.LayoutParams(-1, -1)
        );

        setContentView(container);
    }

    private void createWebView() {

        webView = new WebView(this);

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setTextZoom(100);

        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                // Website ko normal mobile-app size par render karna
                view.evaluateJavascript(
                        "document.documentElement.style.webkitTextSizeAdjust='100%';",
                        null
                );

                container.removeAllViews();

                container.addView(
                        webView,
                        new FrameLayout.LayoutParams(
                                -1,
                                -1
                        )
                );
            }

            @Override
            public void onReceivedError(
                    WebView view,
                    WebResourceRequest request,
                    WebResourceError error
            ) {

                if (request.isForMainFrame()) {
                    showRetry();
                }
            }
        });

        webView.loadUrl(WEBSITE_URL);
    }

    private void showRetry() {

        LinearLayout retry = new LinearLayout(this);
        retry.setOrientation(LinearLayout.VERTICAL);
        retry.setGravity(Gravity.CENTER);
        retry.setPadding(dp(24), dp(24), dp(24), dp(24));

        TextView message = new TextView(this);
        message.setText(
                "IDWP load nahi ho paya.\nInternet check karein."
        );
        message.setTextSize(14);
        message.setTextColor(Color.DKGRAY);
        message.setGravity(Gravity.CENTER);

        TextView button = new TextView(this);
        button.setText("Retry");
        button.setTextSize(14);
        button.setTextColor(Color.WHITE);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setGravity(Gravity.CENTER);

        GradientDrawable buttonBg = new GradientDrawable();
        buttonBg.setColor(Color.rgb(36, 91, 234));
        buttonBg.setCornerRadius(dp(10));

        button.setBackground(buttonBg);
        button.setPadding(
                dp(22),
                dp(11),
                dp(22),
                dp(11)
        );

        button.setOnClickListener(v -> {
            container.removeAllViews();
            showWelcome();
            createWebView();
        });

        retry.addView(message);

        LinearLayout.LayoutParams buttonParams =
                new LinearLayout.LayoutParams(
                        -2,
                        -2
                );

        buttonParams.topMargin = dp(18);

        retry.addView(button, buttonParams);

        setContentView(retry);
    }

    @Override
    public void onBackPressed() {

        if (webView != null && webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
