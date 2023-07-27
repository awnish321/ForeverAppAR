package foreverappar.com.foreverappar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import foreverappar.com.Common.ConnectionDetector;
import foreverappar.com.Common.Networking;

public class SplashScreen extends Activity {

    private static long SPLASH_MILLIS = 750;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;
    String Pref_School_UI,Pref_Restric_Id,Pref_UserType,Pref_UserClassID;
    String Device_Id,Mob_Id,Mob_Product,Mob_Brand,Mob_Manufacture,Mob_Model;
    private static final int TIME = 4 * 1000;// 4 seconds
    PackageInfo pinfo;
    public static String PACKAGE_NAME;
    String sVersionName;
    int sVersionCode;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        cd = new ConnectionDetector(getApplicationContext());

        GetDevicedetails();

        PACKAGE_NAME = getApplicationContext().getPackageName();

        try {
            pinfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        sVersionCode = pinfo.versionCode; // 1
        sVersionName = pinfo.versionName; // 1.0

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                preferences = getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);
                Pref_School_UI = preferences.getString("Rsar_School_UI", "");
                Pref_Restric_Id = preferences.getString("Rsar_Restric_ID", "");
                Pref_UserType = preferences.getString("Rsar_UserType", "");
                Pref_UserClassID = preferences.getString("Rsar_ClassID", "");
                isInternetPresent = cd.isConnectingToInternet();

                if (isInternetPresent) {

                    if (Pref_School_UI.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Enter the Details.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        SplashScreen.this.finish();
                    } else {
                        CheckActivation();
                    }
                }
                else
                {
                    if (Pref_School_UI.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please Enter the Details.", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SplashScreen.this,
                                MainActivity.class);

                        startActivity(intent);
                        SplashScreen.this.finish();
                    } else {
                        if(Pref_UserType.equalsIgnoreCase("Teacher"))
                        {
                            Intent intent = new Intent(SplashScreen.this, Class_Activity.class);

                            startActivity(intent);
                            SplashScreen.this.finish();
                        }else
                        {
                            Intent intent = new Intent(SplashScreen.this, Subject.class);
                            intent.putExtra("Rsar_Class_Id", Pref_UserClassID);
                            startActivity(intent);
                            SplashScreen.this.finish();
                        }
                    }
                }
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        }, TIME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, TIME);
    }

    private void GetDevicedetails() {

        Device_Id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Mob_Id = android.os.Build.ID;
        Mob_Product= android.os.Build.PRODUCT;
        Mob_Brand= android.os.Build.BRAND;
        Mob_Manufacture= android.os.Build.MANUFACTURER;
        Mob_Model = android.os.Build.MODEL;
    }
    private void CheckActivation() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlmanual = Networking.url+"checkActivation.php?";
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlmanual,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);

                        try {
                            final JSONArray array;
                            array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject object;
                                object = new JSONObject(array.getString(i).toString());
                                String Status=object.getString("Status");
                                String Msg=object.getString("Message");
                                System.out.println("Activation" + " "+Status+"   "+Msg );
                                if(Status.equalsIgnoreCase("True")) {
                                    if(Pref_UserType.equalsIgnoreCase("Teacher"))
                                    {
                                        Intent intent = new Intent(SplashScreen.this, Class_Activity.class);
                                        startActivity(intent);
                                        SplashScreen.this.finish();
                                    }else
                                    {
                                        Intent intent = new Intent(SplashScreen.this, Subject.class);
                                        intent.putExtra("Rsar_Class_Id", Pref_UserClassID);
                                        startActivity(intent);
                                        SplashScreen.this.finish();
                                    }
                                    System.out.println("Activationupdate" + " "+Status+"   "+Msg );
                                }else
                                {
                                    Toast.makeText(SplashScreen.this, Msg, Toast.LENGTH_SHORT).show();
                                    preferences = getSharedPreferences("FOREVER_APP", 0);
                                    editor = preferences.edit();
                                    editor.clear();
                                    editor.commit();
                                    Intent intent1 = new Intent(SplashScreen.this, MainActivity.class);
                                    startActivity(intent1);
                                    SplashScreen.this.finish();
                                }
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        // Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected HashMap<String, String> getParams()
            {

                HashMap<String, String>  params = new HashMap<String, String>();
                params.put("School_UI", Pref_School_UI);
                params.put("Restrict_SD", Pref_Restric_Id);
                params.put("package", PACKAGE_NAME);
                params.put("version_name", sVersionName);
                params.put("version_code", Integer.toString(sVersionCode));
                params.put("Device_Id", Device_Id);

                return params;
            }
        };
        queue.add(postRequest);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}
