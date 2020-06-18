package com.cornicosia.android;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.cornicosia.android.utils.TabPageAdapter;
import java.util.ArrayList;
import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private String url;
    private ProgressDialog pd;
    private ProgressBar pb;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        //viewPager initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        TabPageAdapter adapter = new TabPageAdapter(getSupportFragmentManager(), 1);
        viewPager.setAdapter(adapter);
        viewPager.setVisibility(View.VISIBLE);

        url = getString(R.string.url_one);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.setVisibility(View.GONE);
        pb = findViewById(R.id.progress_bar);
        //add bottom tab bar
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.home),
                        Color.parseColor("#ffffff")
                ).title("Acasă")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.categories),
                        Color.parseColor("#ffffff")
                ).title("Program")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.article),
                        Color.parseColor("#ffffff")
                ).title("Articole")
                        .badgeTitle("icon")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.brand),
                        Color.parseColor("#ffffff")
                ).title("Știri")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.gallery),
                        Color.parseColor("#ffffff")
                ).title("Galerie")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.onPageSelected(9);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
        navigationTabBar.setActiveColor(getResources().getColor(R.color.tab_bar_bg_color));
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.setActiveColor(getResources().getColor(R.color.tab_bar_color));
        navigationTabBar.setInactiveColor(getResources().getColor(R.color.tab_bar_bg_color));
        navigationTabBar.setBgColor(getResources().getColor(R.color.tab_bar_color));
        navigationTabBar.setTitleSize(25);
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
                viewPager.setVisibility(View.GONE);
                mWebView.setVisibility(View.VISIBLE);
                if (index == 0) {
                    viewPager.setVisibility(View.VISIBLE);
                    mWebView.setVisibility(View.GONE);
                    viewPager.setCurrentItem(0);
                } else if (index == 1) {
                    url = getString(R.string.url_two);
                } else if (index == 2) {
                    url = getString(R.string.url_three);
                } else if (index == 3) {
                    url = getString(R.string.url_four);
                } else if (index == 4) {
                    url = getString(R.string.url_five);
                }
                shouldOverrideUrlLoading(mWebView, url);
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {

            }
        });
        shouldOverrideUrlLoading(mWebView, url);
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d("sfa", url);
        String url2 = url;

        //check internet connection here, if you try to check before creation of tabs, it will throuw an error and app will be crashed
        if (haveNetworkConnection()) {
            pb.setVisibility(View.VISIBLE);
            mWebView.getSettings().setJavaScriptEnabled(true);
            if (url.startsWith("tel:")) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                startActivity(intent);
                view.reload();
                return true;
            }

            mWebView.setWebViewClient(new InsideWebViewClient());
            mWebView.getSettings().setLightTouchEnabled(true);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.getSettings().setGeolocationEnabled(true);
            String USER_AGENT = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";
            mWebView.getSettings().setUserAgentString(USER_AGENT);
            view.loadUrl(url);
            return true;
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();

            connectionLossDialog();
        }
        return false;
    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (haveNetworkConnection()) {
                view.loadUrl(url);
                return true;
            } else {
                connectionLossDialog();
            }
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            pb.setVisibility(View.GONE);
        }
    }

    public void connectionLossDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_connection, null);
        dialogBuilder.setView(dialogView);
        final AlertDialog findMeDialog = dialogBuilder.create();
        findMeDialog.show();
        LinearLayout reset_btn = (LinearLayout) dialogView.findViewById(R.id.ok);

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findMeDialog.dismiss();
                finish();
            }
        });
    }

    public boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
