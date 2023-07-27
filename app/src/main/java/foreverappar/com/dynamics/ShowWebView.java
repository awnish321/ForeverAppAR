package foreverappar.com.dynamics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import foreverappar.com.foreverappar.R;

public class ShowWebView extends Activity {
	String Uri;
    //private Button button;
    private WebView webView;
    public void onCreate(Bundle savedInstanceState) {
         
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_web_view);
         
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("Linkpass")) {
             Uri = extras.getString("Linkpass");
            //Uri = extras.getString("https://www.google.co.in/");
        }
        //Get webview 
        webView = (WebView) findViewById(R.id.webView1);
         System.out.println("LInkkkkk"+" "+Uri);
        
        startWebView(Uri);
         
    }///storage/emulated/0/.foreverappar/Chapter1/videos/a.mp4
     
    @SuppressLint("SetJavaScriptEnabled") private void startWebView(String url) {
         
        //Create new webview Client to show progress dialog
        //When opening a url or click on link


         
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;
          
            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {              
                view.loadUrl(url);
                return true;
            }
        
            //Show loader on url load
           /* public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(ShowWebView.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }*/
           /* public void onPageFinished(WebView view, String url) {
                try{
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }*/
             
        }); 
          
         // Javascript inabled on webview  
        webView.getSettings().setJavaScriptEnabled(true); 
         
        // Other webview options
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);


        webView.loadUrl(url);


    }






    // Open previous opened link from history on webview when back button pressed
     
    
    // Detect when the back button is pressed
    /*public void onBackPressed() {
    
    	this.finish();
    	
    	super.onBackPressed();
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }*/
 
}
