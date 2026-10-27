
package com.idwp.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
    private WebView webView;
    private LinearLayout root;
    private boolean pageLoaded = false;

    private int navy = Color.rgb(8, 20, 55);
    private int cyan = Color.rgb(0, 205, 245);

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
                navy,
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

        TextView line = new TextView(this);
        line.setText("━━━━━━  ↗");
        line.setTextSize(24);
        line.setTextColor(cyan);
        line.setGravity(Gravity.CENTER);

        TextView tagline = new TextView(this);
        tagline.setText("LEARN  •  WORK  •  GROW");
        tagline.setTextSize(14);
        tagline.setTextColor(Color.WHITE);
        tagline.setGravity(Gravity.CENTER);
        tagline.setLetterSpacing(0.12f);

        TextView welcome = new TextView(this);
        welcome.setText("Welcome to IDWP");
        welcome.setTextSize(21);
        welcome.setTextColor(Color.WHITE);
        welcome.setGravity(Gravity.CENTER);
        welcome.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        TextView subtitle = new TextView(this);
        subtitle.setText("Your digital work journey starts here");
        subtitle.setTextSize(14);
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);

        ProgressBar progress = new ProgressBar(this);
        progress.setIndeterminate(true);

        root.addView(logo);
        root.addView(line);
        addSpace(root, 12);
        root.addView(tagline);
        addSpace(root, 52);
        root.addView(welcome);
        addSpace(root, 10);
        root.addView(subtitle);
        addSpace(root, 36);
        root.addView(progress);

        setContentView(root);
    }

    private void addSpace(LinearLayout layout, int dp) {
        View space = new View(this);
        layout.addView(space, new LinearLayout.LayoutParams(
            1, (int) (dp * getResources()
                .getDisplayMetrics().density)
        ));
    }

    private void loadWebsite() {
        webView = new WebView(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                pageLoaded = true;
                setContentView(webView);
            }
        });

        webView.loadUrl("https://idwp-nxwk.vercel.app/");

        new Handler().postDelayed(() -> {
            if (!pageLoaded) {
                TextView message = new TextView(this);
                message.setText(
                    "IDWP load ho raha hai...\\n" +
                    "Internet check karke thodi der intezar karein."
                );
                message.setTextColor(Color.WHITE);
                message.setTextSize(16);
                message.setGravity(Gravity.CENTER);
                root.removeAllViews();
                root.addView(message);
                setContentView(root);
            }
        }, 15000);
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
