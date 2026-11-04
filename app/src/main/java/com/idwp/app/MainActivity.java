
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
import android.widget.ScrollView;
import android.graphics.Color;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.content.res.ColorStateList;

public class MainActivity extends Activity {
    private WebView webView;
    private FrameLayout root;
    private boolean pageLoaded = false;
    private boolean welcomeTimeFinished = false;
    private boolean showingError = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private static final String WEBSITE = "https://idwp-nxwk.vercel.app/";

    private int dp(int v) {
        return (int)(v * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(5, 16, 52));
        getWindow().setNavigationBarColor(Color.rgb(5, 16, 52));

        root = new FrameLayout(this);
        setContentView(root);

        showWelcome();

        handler.postDelayed(() -> {
            welcomeTimeFinished = true;
            showWebsiteIfReady();
        }, 2600);

        openWebsite();
    }

    private GradientDrawable gradientBackground() {
        return new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{
                Color.rgb(0, 42, 145),
                Color.rgb(4, 17, 61),
                Color.rgb(0, 8, 38)
            }
        );
    }

    private Bitmap loadAsset(String name) {
        try {
            return BitmapFactory.decodeStream(getAssets().open(name));
        } catch (Exception e) {
            return null;
        }
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER);

        if (bold) {
            t.setTypeface(null, android.graphics.Typeface.BOLD);
        }

        return t;
    }

    private void showWelcome() {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.CENTER_HORIZONTAL);
        page.setPadding(dp(18), dp(12), dp(18), dp(18));
        page.setBackground(gradientBackground());

        ImageView portrait = new ImageView(this);
        Bitmap photo = loadAsset("founder_photo.jpg");

        if (photo != null) {
            portrait.setImageBitmap(photo);
        }

        portrait.setScaleType(ImageView.ScaleType.CENTER_CROP);
        portrait.setBackground(new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(0, 190, 255), Color.rgb(7, 25, 85)}
        ));
        portrait.setClipToOutline(true);

        LinearLayout.LayoutParams photoParams =
            new LinearLayout.LayoutParams(dp(220), dp(255));

        photoParams.topMargin = dp(8);
        portrait.setLayoutParams(photoParams);
        page.addView(portrait);

        TextView founder = text(
            "Ibrahim  •  CEO & Founder",
            17,
            Color.WHITE,
            true
        );

        LinearLayout.LayoutParams fp =
            new LinearLayout.LayoutParams(-1, dp(38));

        fp.topMargin = dp(5);
        page.addView(founder, fp);

        ImageView logo = new ImageView(this);
        Bitmap logoBitmap = loadAsset("idwp_logo.png");

        if (logoBitmap != null) {
            logo.setImageBitmap(logoBitmap);
        }

        logo.setScaleType(ImageView.ScaleType.FIT_CENTER);

        LinearLayout.LayoutParams lp =
            new LinearLayout.LayoutParams(dp(210), dp(210));

        lp.topMargin = dp(4);
        page.addView(logo, lp);

        page.addView(text(
            "LEARN  •  WORK  •  GROW",
            15,
            Color.CYAN,
            true
        ));

        TextView welcome = text(
            "Welcome to IDWP",
            28,
            Color.WHITE,
            true
        );

        LinearLayout.LayoutParams wp =
            new LinearLayout.LayoutParams(-1, dp(58));

        wp.topMargin = dp(14);
        page.addView(welcome, wp);

        page.addView(text(
            "Your Journey Starts Here",
            17,
            Color.WHITE,
            false
        ));

        ProgressBar progress = new ProgressBar(this);
        progress.setIndeterminateTintList(
            ColorStateList.valueOf(Color.CYAN)
        );

        LinearLayout.LayoutParams pp =
            new LinearLayout.LayoutParams(dp(42), dp(42));

        pp.topMargin = dp(22);
        page.addView(progress, pp);

        TextView loading = text(
            "Loading...",
            16,
            Color.WHITE,
            true
        );

        LinearLayout.LayoutParams loadp =
            new LinearLayout.LayoutParams(-1, dp(36));

        page.addView(loading, loadp);

        scroll.addView(page);
        root.addView(scroll, new FrameLayout.LayoutParams(-1, -1));
    }

    private void showWebsiteIfReady() {
        if (pageLoaded && welcomeTimeFinished
                && webView != null && !showingError) {

            root.removeAllViews();

            if (webView.getParent() != null) {
                ((android.view.ViewGroup) webView.getParent())
                    .removeView(webView);
            }

            root.addView(
                webView,
                new FrameLayout.LayoutParams(-1, -1)
            );
        }
    }

    private void openWebsite() {
        try {
            webView = new WebView(this);

            WebSettings s = webView.getSettings();
            s.setJavaScriptEnabled(true);
            s.setDomStorageEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    pageLoaded = true;
                    showWebsiteIfReady();
                }

                @Override
                public void onReceivedError(
                    WebView view,
                    WebResourceRequest request,
                    android.webkit.WebResourceError error
                ) {
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
        showingError = true;
        root.removeAllViews();

        LinearLayout screen = new LinearLayout(this);
        screen.setOrientation(LinearLayout.VERTICAL);
        screen.setGravity(Gravity.CENTER);
        screen.setPadding(dp(24), dp(24), dp(24), dp(24));
        screen.setBackground(gradientBackground());

        TextView msg = text(
            "IDWP load nahi ho paya. Internet check karein.",
            17,
            Color.WHITE,
            false
        );

        screen.addView(msg);

        Button retry = new Button(this);
        retry.setText("Retry");

        retry.setOnClickListener(v -> {
            showingError = false;
            pageLoaded = false;
            welcomeTimeFinished = true;
            root.removeAllViews();

            if (webView != null) {
                webView.loadUrl(WEBSITE);
            } else {
                openWebsite();
            }
        });

        screen.addView(retry);

        root.addView(screen, new FrameLayout.LayoutParams(-1, -1));
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
        handler.removeCallbacksAndMessages(null);

        if (webView != null) {
            webView.stopLoading();
            webView.destroy();
            webView = null;
        }

        super.onDestroy();
    }
}
