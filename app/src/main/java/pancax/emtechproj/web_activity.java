package pancax.emtechproj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class web_activity extends AppCompatActivity {
    private WebView myWebView;
    private WebSettings webSettings;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_web_activity);

        myWebView=(WebView) findViewById(R.id.webview);
        setUpWebView();
        //callZabo();
    }
    private void setUpWebView(){

        myWebView.loadUrl("file:///android_asset/index.html");
        webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new MyWebViewClient());
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // This is my website, so do not override; let my WebView load the page
            return false;

        }

    }
    private void callZabo(){
        myWebView.evaluateJavascript("zaboConnect();",null);
    }
}

