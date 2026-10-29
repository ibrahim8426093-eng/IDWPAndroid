
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
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceError;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.Button;

public class MainActivity extends Activity {
    private WebView webView;
    private int dp(float n) {
        return (int)(n * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showWelcome();
        openWebsite();
    }

    private void showWelcome() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(dp(24), dp(24), dp(24), dp(24));

        GradientDrawable bg = new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{Color.rgb(10,48,120), Color.rgb(8,20,55), Color.rgb(3,9,30)}
        );
        root.setBackground(bg);

        ImageView logo = new ImageView(this);
        try {
            logo.setImageDrawable(android.graphics.drawable.Drawable.createFromStream(
                getAssets().open("idwp_logo.png"), null));
        } catch (Exception ignored) {}

        logo.setScaleType(ImageView.ScaleType.FIT_CENTER);
        root.addView(logo, new LinearLayout.LayoutParams(dp(180), dp(180)));

        TextView title = new TextView(this);
        title.setText("Welcome to IDWP");
        title.setTextColor(Color.WHITE);
        title.setTextSize(23);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView tagline = new TextView(this);
        tagline.setText("LEARN  •  WORK  •  GROW");
        tagline.setTextColor(Color.CYAN);
        tagline.setTextSize(14);
        tagline.setGravity(Gravity.CENTER);
        tagline.setPadding(0, dp(12), 0, dp(24));
        root.addView(tagline);

        ProgressBar progress = new ProgressBar(this);
        progress.setIndeterminate(true);
        root.addView(progress);
        setContentView(root);
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
                    if (!isFinishing() && !isDestroyed()) {
                        setContentView(view);
                    }
                }

                @Override
                public void onReceivedError(WebView view,
                        WebResourceRequest request, WebResourceError error) {
                    if (request.isForMainFrame()) showRetry();
                }
            });

            webView.loadUrl("https://idwp-nxwk.vercel.app/");
        } catch (Exception e) {
            showRetry();
        }
    }

    private void showRetry() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding(dp(24), dp(24), dp(24), dp(24));
        root.setBackgroundColor(Color.rgb(8,20,55));

        TextView message = new TextView(this);
        message.setText("IDWP load nahi ho paya. Internet check karein.");
        message.setTextColor(Color.WHITE);
        message.setTextSize(17);
        message.setGravity(Gravity.CENTER);
        root.addView(message);

        Button retry = new Button(this);
        retry.setText("Retry");
        retry.setOnClickListener(v -> {
            if (webView != null) webView.loadUrl("https://idwp-nxwk.vercel.app/");
            else openWebsite();
        });
        root.addView(retry);
        setContentView(root);
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            try {
                webView.stopLoading();
                webView.destroy();
            } catch (Exception ignored) {}
            webView = null;
        }
        super.onDestroy();
    }
}
