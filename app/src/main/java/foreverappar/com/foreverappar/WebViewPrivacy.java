package foreverappar.com.foreverappar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewPrivacy extends AppCompatActivity {
    WebView webView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_privacy);
        webView=findViewById(R.id.webprivacy);
        progressDialog=new ProgressDialog(WebViewPrivacy.this);
        progressDialog.setMessage("Wait.....");
        progressDialog.setCancelable(false);
        progressDialog.show();

        webView.loadUrl("https://www.rachnasagar.in/android/privacy-swa.php\n");
        // this will enable the javascript.
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        },2000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
}