package com.mystreetprayer.app;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Fragment_Bible extends Fragment {

    private WebView webView_bible;
    private ProgressBar progressBarBible;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bible, container, false);

        webView_bible =(WebView) rootView.findViewById(R.id.kjvwebview);
        progressBarBible = (ProgressBar) rootView.findViewById(R.id.kjvProgressBar);

        final SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.bible_sr);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bibleWebView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        bibleWebView();

        return rootView;
    }

    private void bibleWebView() {

        webView_bible.loadUrl("https://mystreetprayer-d7aa2.firebaseapp.com/");
//      webView_bible.loadUrl("https://www.kingjamesbibleonline.org/");
        progressBarBible.setMax(100);

        WebSettings webSettings= webView_bible.getSettings();

        webView_bible.getSettings().setDomStorageEnabled(true);
        webView_bible.getSettings().setAppCacheEnabled(true);
        webView_bible.getSettings().setLoadsImagesAutomatically(true);
        webView_bible.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setJavaScriptEnabled(true);

        webView_bible.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarBible.setProgress(newProgress);
            }
        });
        webView_bible.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                webView_bible.loadUrl("file:///android_asset/error.html");
            }
        });


        webView_bible.canGoBack();
        webView_bible.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK
                        && event.getAction() == MotionEvent.ACTION_UP
                        && webView_bible.canGoBack()) {
                    webView_bible.goBack();
                    return true;
                }
            return false;
            }
        });
    }




    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Bible");
    }

}
