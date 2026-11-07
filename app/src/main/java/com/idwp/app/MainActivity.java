package com.idwp.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.content.res.ColorStateList;

public class MainActivity extends Activity {

    private WebView webView;
    private FrameLayout root;

    private boolean pageLoaded = false;
    private boolean welcomeFinished = false;
    private boolean showingError = false;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private static final String WEBSITE =
            "https://idwp-nxwk.vercel.app/";

    private int dp(int value) {
        return (int) (
                value * getResources()
                        .getDisplayMetrics()
                        .density + 0.5f
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(
                Color.rgb(4, 15, 48)
        );

        getWindow().setNavigationBarColor(
                Color.rgb(4, 15, 48)
        );

        root = new FrameLayout(this);
        setContentView(root);

        showWelcome();
        openWebsite();

        handler.postDelayed(() -> {
            welcomeFinished = true;
            showWebsiteIfReady();
        }, 2600);
    }

    private GradientDrawable background() {
        return new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(0, 48, 145),
                        Color.rgb(4, 19, 65),
                        Color.rgb(1, 8, 35)
                }
        );
    }

    private TextView makeText(
            String value,
            float size,
            int color,
            boolean bold) {

        TextView text = new TextView(this);

        text.setText(value);
        text.setTextSize(size);
        text.setTextColor(color);
        text.setGravity(Gravity.CENTER);

        if (bold) {
            text.setTypeface(
                    null,
                    android.graphics.Typeface.BOLD
            );
        }

        return text;
    }

    private void showWelcome() {

        LinearLayout page =
                new LinearLayout(this);

        page.setOrientation(
                LinearLayout.VERTICAL
        );

        page.setGravity(
                Gravity.CENTER_HORIZONTAL
        );

        page.setPadding(
                dp(20),
                dp(35),
                dp(20),
                dp(25)
        );

        page.setBackground(background());

        // IDWP LOGO
        ImageView logo =
                new ImageView(this);

        logo.setImageResource(
                R.drawable.idwp_logo
        );

        logo.setScaleType(
                ImageView.ScaleType.FIT_CENTER
        );

        LinearLayout.LayoutParams logoParams =
                new LinearLayout.LayoutParams(
                        dp(300),
                        dp(300)
                );

        logoParams.gravity =
                Gravity.CENTER_HORIZONTAL;

        page.addView(
                logo,
                logoParams
        );

        // TAGLINE
        TextView tagline =
                makeText(
                        "LEARN • WORK • GROW",
                        15,
                        Color.CYAN,
                        true
                );

        LinearLayout.LayoutParams taglineParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(38)
                );

        page.addView(
                tagline,
                taglineParams
        );

        // WELCOME
        TextView welcome =
                makeText(
                        "Welcome to IDWP",
                        28,
                        Color.WHITE,
                        true
                );

        LinearLayout.LayoutParams welcomeParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(58)
                );

        welcomeParams.topMargin =
                dp(10);

        page.addView(
                welcome,
                welcomeParams
        );

        // SUBTITLE
        TextView subtitle =
                makeText(
                        "Your Journey Starts Here",
                        17,
                        Color.WHITE,
                        false
                );

        page.addView(subtitle);

        // PROGRESS
        ProgressBar progress =
                new ProgressBar(this);

        progress.setIndeterminateTintList(
                ColorStateList.valueOf(
                        Color.CYAN
                )
        );

        LinearLayout.LayoutParams progressParams =
                new LinearLayout.LayoutParams(
                        dp(42),
                        dp(42)
                );

        progressParams.topMargin =
                dp(22);

        page.addView(
                progress,
                progressParams
        );

        // LOADING
        TextView loading =
                makeText(
                        "Loading...",
                        16,
                        Color.WHITE,
                        true
                );

        page.addView(loading);

        root.addView(
                page,
                new FrameLayout.LayoutParams(
                        -1,
                        -1
                )
        );
    }

    private void openWebsite() {

        try {

            webView =
                    new WebView(this);

            WebSettings settings =
                    webView.getSettings();

            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);

            webView.setWebChromeClient(
                    new WebChromeClient()
            );

            webView.setWebViewClient(
                    new WebViewClient() {

                        @Override
                        public void onPageFinished(
                                WebView view,
                                String url) {

                            pageLoaded = true;

                            showWebsiteIfReady();
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
                    }
            );

            webView.loadUrl(WEBSITE);

        } catch (Exception e) {

            showError();
        }
    }

    private void showWebsiteIfReady() {

        if (pageLoaded
                && welcomeFinished
                && webView != null
                && !showingError) {

            root.removeAllViews();

            if (webView.getParent() != null) {

                ((android.view.ViewGroup)
                        webView.getParent())
                        .removeView(webView);
            }

            root.addView(
                    webView,
                    new FrameLayout.LayoutParams(
                            -1,
                            -1
                    )
            );
        }
    }

    private void showError() {

        showingError = true;

        root.removeAllViews();

        LinearLayout error =
                new LinearLayout(this);

        error.setOrientation(
                LinearLayout.VERTICAL
        );

        error.setGravity(
                Gravity.CENTER
        );

        error.setPadding(
                dp(25),
                dp(25),
                dp(25),
                dp(25)
        );

        error.setBackground(
                background()
        );

        TextView message =
                makeText(
                        "IDWP load nahi ho paya.\nInternet check karein.",
                        17,
                        Color.WHITE,
                        false
                );

        error.addView(message);

        Button retry =
                new Button(this);

        retry.setText("Retry");

        retry.setOnClickListener(
                v -> {

                    showingError = false;
                    pageLoaded = false;
                    welcomeFinished = true;

                    root.removeAllViews();

                    if (webView != null) {

                        webView.loadUrl(
                                WEBSITE
                        );

                    } else {

                        openWebsite();
                    }
                }
        );

        error.addView(retry);

        root.addView(
                error,
                new FrameLayout.LayoutParams(
                        -1,
                        -1
                )
        );
    }

    @Override
    public void onBackPressed() {

        if (webView != null
                && webView.canGoBack()) {

            webView.goBack();

        } else {

            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {

        handler.removeCallbacksAndMessages(
                null
        );

        if (webView != null) {

            webView.stopLoading();
            webView.destroy();
            webView = null;
        }

        super.onDestroy();
    }
                 }
