package foreverappar.com.ui.ActivityList;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import foreverappar.com.foreverappar.R;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class AboutScreen3 extends Activity implements OnClickListener {

	private static final int PERMISSION_CALLBACK_CONSTANT = 100;
	private static final int REQUEST_PERMISSION_SETTING = 101;
	String[] permissionsRequired = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission
			.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission
			.INTERNET,Manifest.permission.ACCESS_NETWORK_STATE };

	private SharedPreferences permissionStatus;
	private boolean sentToSettings = false;

    private static final String LOGTAG = "AboutScreen";
    
    
    public static String Av_Sucess1="";public static String Av_Sucess2="";public static String Av_Sucess3="";public static String Av_Sucess4="";
    public static String Av_Sucess5="";public static String Av_Sucess6="";public static String Av_Sucess7="";public static String Av_Sucess8="";
    public static String Av_Sucess9="";public static String Av_Sucess10="";public static String Av_Sucess11="";public static String Av_Sucess12="";
    public static String Av_Sucess13="";
    
    public static String Chapter_Details_Value="";
    public static String ACTIVITY_TO_LAUNCH="";
    
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public static String Value;
    private WebView mAboutWebText;
    private Button mStartButton,mBtnDownload;
    private TextView mAboutTextTitle;
    private static String mClassToLaunch;
    private static String mClassToLaunchPackage;
    private boolean isCanceled;
    
    public static final String Sucess = " ";
    ScrollView sv;
    TextView Txt_Sucess1,Txt_Sucess2,Txt_Sucess3,Txt_Sucess4,Txt_Sucess5,Txt_Sucess6,Txt_Sucess7,
    Txt_Sucess8,Txt_Sucess9,Txt_Sucess10,Txt_Sucess11,Txt_Sucess12,Txt_Sucess13,Txt_Delete_All;
   
    LinearLayout Chapter_detail,Ln_Ch1,Ln_Ch2,Ln_Ch3,Ln_Ch4,Ln_Ch5,Ln_Ch6,Ln_Ch7,Ln_Ch8,Ln_Ch9,Ln_Ch10,Ln_Ch11,Ln_Ch12,Ln_Ch13,Ln_Delete_All;
    
    
    ImageView img_delete1,img_delete2,img_delete3,img_delete4,img_delete5,img_delete6,img_delete7,img_delete8,img_delete9,img_delete10,
    img_delete11,img_delete12,img_delete13;
    
    boolean Sucess1,Sucess2,Sucess3,Sucess4,Sucess5,Sucess6,Sucess7,Sucess8,Sucess9,Sucess10,Sucess11,Sucess12,Sucess13,Refresh,Searching;
    // To check net connection
    boolean deleted ;
	// flag for Internet connection status
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;
	private ProgressBar bar;
	private ProgressDialog mProgressDialog;
      //  String unzipLocation = Environment.getExternalStorageDirectory() + "/Android/data/com.rsar.VideoPlayback/";
	    String zipFile ;
	    File dirDeleteFolder;


	PackageInfo pinfo;
	public static String PACKAGE_NAME;
	String sVersionName;
	int sVersionCode;
	String unzipLocation;
	String Update_Status,Update_Message,Update_Link,Service_Status,Status_Notification,Notify_Msg;
	String	Str_Notify_Msg,Str_Notify_Msg_Link;
	String Device_Id,Mob_Id,Mob_Product,Mob_Brand,Mob_Manufacture,Mob_Model;
	    
	    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		setContentView(R.layout.about_screen);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			unzipLocation =  getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+ "/.VideoPlayBack/MathsBuzz1/";
			Log.d("pathvv",""+unzipLocation);
		}else {
			unzipLocation = Environment.getExternalStorageDirectory() + "/.VideoPlayBack/MathsBuzz1/";
			Log.d("pathvv2",""+unzipLocation);
		}
		permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
		cd = new ConnectionDetector(getApplicationContext());
		Value=" ";
      try{
          Bundle extras = getIntent().getExtras();
          String webText = extras.getString("ABOUT_TEXT");
          ACTIVITY_TO_LAUNCH = extras.getString("ACTIVITY_TO_LAUNCH");
          mClassToLaunchPackage = getPackageName();
          mClassToLaunch = mClassToLaunchPackage + "."
              + ACTIVITY_TO_LAUNCH;
          System.out.println("asasasa"+"   "+mClassToLaunch+"   "+mClassToLaunchPackage);
          }catch(Exception e)
          {
       	   System.out.println("jaggggg"+"   "+e);
          }
          mStartButton = (Button) findViewById(R.id.button_start);
          mStartButton.setOnClickListener(this);
          Device_Id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
				Settings.Secure.ANDROID_ID);
		Mob_Id = Build.ID;
		Mob_Product= Build.PRODUCT;
		Mob_Brand= Build.BRAND;
		Mob_Manufacture= Build.MANUFACTURER;
		Mob_Model = Build.MODEL;
		PACKAGE_NAME = getApplicationContext().getPackageName();
		try {
			pinfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sVersionCode = pinfo.versionCode; // 1
		sVersionName = pinfo.versionName; // 1.0
		System.out.println("details version"+"  "+sVersionCode+" "+sVersionName+" "+PACKAGE_NAME);
		//-- To take permission ------------------
		GetAndroidPermission();
		buttondetails();
		SharedPreferenceDetails();
		ButtonClick();
		NotifyUpdate();
     
       if(Chapter_Details_Value.equalsIgnoreCase(""))
       {
       	Chapter_detail.setVisibility(View.GONE);
       }
       else
       {
       	Chapter_detail.setVisibility(View.VISIBLE);
       }
       NewSearch();
    }
    private void buttondetails() {

		// TODO Auto-generated method stub
    	 Txt_Sucess1=(TextView)findViewById(R.id.Txt_Sucess1);
         Txt_Sucess2=(TextView)findViewById(R.id.Txt_Sucess2);
         Txt_Sucess3=(TextView)findViewById(R.id.Txt_Sucess3);
         Txt_Sucess4=(TextView)findViewById(R.id.Txt_Sucess4);
         Txt_Sucess5=(TextView)findViewById(R.id.Txt_Sucess5);
         Txt_Sucess6=(TextView)findViewById(R.id.Txt_Sucess6);
         Txt_Sucess7=(TextView)findViewById(R.id.Txt_Sucess7);
         Txt_Sucess8=(TextView)findViewById(R.id.Txt_Sucess8);
         Txt_Sucess9=(TextView)findViewById(R.id.Txt_Sucess9);
         Txt_Sucess10=(TextView)findViewById(R.id.Txt_Sucess10);
         Txt_Sucess11=(TextView)findViewById(R.id.Txt_Sucess11);
         Txt_Sucess12=(TextView)findViewById(R.id.Txt_Sucess12);
         Txt_Sucess13=(TextView)findViewById(R.id.Txt_Sucess13);
        
         Txt_Delete_All=(TextView)findViewById(R.id.Txt_Delete_All);
         mAboutTextTitle = (TextView) findViewById(R.id.about_text_title);
	      //   mAboutTextTitle.setText(extras.getString("ABOUT_TEXT_TITLE"));
         
         img_delete1= (ImageView)findViewById(R.id.img_delete1);
         img_delete2= (ImageView)findViewById(R.id.img_delete2);
         img_delete3= (ImageView)findViewById(R.id.img_delete3);
         img_delete4= (ImageView)findViewById(R.id.img_delete4);
         img_delete5= (ImageView)findViewById(R.id.img_delete5);
         
         img_delete6= (ImageView)findViewById(R.id.img_delete6);
         img_delete7= (ImageView)findViewById(R.id.img_delete7);
         img_delete8= (ImageView)findViewById(R.id.img_delete8);
         img_delete9= (ImageView)findViewById(R.id.img_delete9);
         img_delete10= (ImageView)findViewById(R.id.img_delete10);
         
         img_delete11= (ImageView)findViewById(R.id.img_delete11);
         img_delete12= (ImageView)findViewById(R.id.img_delete12);
         img_delete13= (ImageView)findViewById(R.id.img_delete13);
        
         mStartButton = (Button) findViewById(R.id.button_start);
         mBtnDownload= (Button) findViewById(R.id.button_download);
         Chapter_detail =(LinearLayout)findViewById(R.id.Linear_Chap_detail);
         Ln_Ch1 =(LinearLayout)findViewById(R.id.Linear_Ch_1);
         Ln_Ch2 =(LinearLayout)findViewById(R.id.Linear_Ch_2);
         Ln_Ch3 =(LinearLayout)findViewById(R.id.Linear_Ch_3);
         Ln_Ch4 =(LinearLayout)findViewById(R.id.Linear_Ch_4);
         Ln_Ch5 =(LinearLayout)findViewById(R.id.Linear_Ch_5);
         Ln_Ch6 =(LinearLayout)findViewById(R.id.Linear_Ch_6);
         Ln_Ch7 =(LinearLayout)findViewById(R.id.Linear_Ch_7);
         Ln_Ch8 =(LinearLayout)findViewById(R.id.Linear_Ch_8);
         Ln_Ch9 =(LinearLayout)findViewById(R.id.Linear_Ch_9);
         Ln_Ch10 =(LinearLayout)findViewById(R.id.Linear_Ch_10);
         Ln_Ch11 =(LinearLayout)findViewById(R.id.Linear_Ch_11);
         Ln_Ch12 =(LinearLayout)findViewById(R.id.Linear_Ch_12);
         Ln_Ch13 =(LinearLayout)findViewById(R.id.Linear_Ch_13);
         
         Ln_Delete_All=(LinearLayout)findViewById(R.id.Ln_Delete_All);
         sv = (ScrollView)findViewById(R.id.scrollView1);
         
    }



	private void SharedPreferenceDetails() {

		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
 		
 		
		 Sucess1 = sharedpreferences.getBoolean("Sucess1", false);
		 Sucess2 = sharedpreferences.getBoolean("Sucess2", false);
		 Sucess3 = sharedpreferences.getBoolean("Sucess3", false);
		 Sucess4 = sharedpreferences.getBoolean("Sucess4", false);
		 Sucess5 = sharedpreferences.getBoolean("Sucess5", false);
		 Sucess6 = sharedpreferences.getBoolean("Sucess6", false);
		 Sucess7 = sharedpreferences.getBoolean("Sucess7", false);
		 Sucess8 = sharedpreferences.getBoolean("Sucess8", false);
		 Sucess9 = sharedpreferences.getBoolean("Sucess9", false);
		 Sucess10 = sharedpreferences.getBoolean("Sucess10", false);
		 Sucess11 = sharedpreferences.getBoolean("Sucess11", false);
		 Sucess12 = sharedpreferences.getBoolean("Sucess12", false);
		 Sucess13 = sharedpreferences.getBoolean("Sucess13", false);
		 Refresh = sharedpreferences.getBoolean("Refresh", false);
	//	 Searching= sharedpreferences.getBoolean("Searching", false);
		 
		System.out.println("kkkkkk"+" "+Searching+"  "+Sucess1);
		
	}

	private void NewSearch() {
		// TODO Auto-generated method stub
		
		
		if (ActivitySplashScreen.Av_Sucess1.equalsIgnoreCase("Exists")) {
			
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess1.setVisibility(View.VISIBLE);
			img_delete1.setVisibility(View.VISIBLE);
 			Ln_Ch1.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));

		}if (ActivitySplashScreen.Av_Sucess2.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess2.setVisibility(View.VISIBLE);
			img_delete2.setVisibility(View.VISIBLE);
 			Ln_Ch2.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}
		if (ActivitySplashScreen.Av_Sucess3.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess3.setVisibility(View.VISIBLE);
			img_delete3.setVisibility(View.VISIBLE);
 			Ln_Ch3.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}if (ActivitySplashScreen.Av_Sucess4.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess4.setVisibility(View.VISIBLE);
			img_delete4.setVisibility(View.VISIBLE);
 			Ln_Ch4.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}
		if (ActivitySplashScreen.Av_Sucess5.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess5.setVisibility(View.VISIBLE);
			img_delete5.setVisibility(View.VISIBLE);
 			Ln_Ch5.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}if (ActivitySplashScreen.Av_Sucess6.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess6.setVisibility(View.VISIBLE);
			img_delete6.setVisibility(View.VISIBLE);
 			Ln_Ch6.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}
		if (ActivitySplashScreen.Av_Sucess7.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess7.setVisibility(View.VISIBLE);
			img_delete7.setVisibility(View.VISIBLE);
 			Ln_Ch7.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}if (ActivitySplashScreen.Av_Sucess8.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess8.setVisibility(View.VISIBLE);
			img_delete8.setVisibility(View.VISIBLE);
 			Ln_Ch8.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}
		if (ActivitySplashScreen.Av_Sucess9.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess9.setVisibility(View.VISIBLE);
			img_delete9.setVisibility(View.VISIBLE);
 			Ln_Ch9.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}if (ActivitySplashScreen.Av_Sucess10.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess10.setVisibility(View.VISIBLE);
			img_delete10.setVisibility(View.VISIBLE);
 			Ln_Ch10.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}
		
		if (ActivitySplashScreen.Av_Sucess11.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess11.setVisibility(View.VISIBLE);
			img_delete11.setVisibility(View.VISIBLE);
 			Ln_Ch11.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}if (ActivitySplashScreen.Av_Sucess12.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess12.setVisibility(View.VISIBLE);
			img_delete12.setVisibility(View.VISIBLE);
 			Ln_Ch12.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}
		if (ActivitySplashScreen.Av_Sucess13.equalsIgnoreCase("Exists"))
		{
			mStartButton.setVisibility(View.VISIBLE);
			Txt_Sucess13.setVisibility(View.VISIBLE);
			img_delete13.setVisibility(View.VISIBLE);
 			Ln_Ch13.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		}
		
	}





	private void ButtonClick() {
		// TODO Auto-generated method stub
		   mBtnDownload.setOnClickListener(this);
	         mStartButton.setOnClickListener(this);
	         
	         
	        
	         
	         Ln_Ch1.setOnClickListener(this);
	         Ln_Ch2.setOnClickListener(this);
	         Ln_Ch3.setOnClickListener(this);
	         Ln_Ch4.setOnClickListener(this);
	         Ln_Ch5.setOnClickListener(this);
	         Ln_Ch6.setOnClickListener(this);
	         Ln_Ch7.setOnClickListener(this);
	         Ln_Ch8.setOnClickListener(this);
	         Ln_Ch9.setOnClickListener(this);
	         Ln_Ch10.setOnClickListener(this);
	         Ln_Ch11.setOnClickListener(this);
	         Ln_Ch12.setOnClickListener(this);
	         Ln_Ch13.setOnClickListener(this);
	      
	       //  Ln_Ch33.setOnClickListener(this);
	         Ln_Delete_All.setOnClickListener(this);
	         
	         
	         Chapter_detail.setOnClickListener(this);
	         
	         if(Sucess1 == true )
	     		{
	        	 
	        	 mStartButton.setVisibility(View.VISIBLE);
	     			Txt_Sucess1.setVisibility(View.VISIBLE);
	     			img_delete1.setVisibility(View.VISIBLE);
	     			Ln_Ch1.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     		}
				if (Sucess2 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);
		     			Txt_Sucess2.setVisibility(View.VISIBLE);
		     			img_delete2.setVisibility(View.VISIBLE);
		     			Ln_Ch2.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		     	
					
				}
				if (Sucess3 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);
	     			Txt_Sucess3.setVisibility(View.VISIBLE);
	     			img_delete3.setVisibility(View.VISIBLE);
	     		Ln_Ch3.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}
				if (Sucess4 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);
	     			Txt_Sucess4.setVisibility(View.VISIBLE);
	     			img_delete4.setVisibility(View.VISIBLE);
	     			Ln_Ch4.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}
				if (Sucess5 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);
	     			Txt_Sucess5.setVisibility(View.VISIBLE);
	     			img_delete5.setVisibility(View.VISIBLE);
	     			Ln_Ch5.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}
				if (Sucess6 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);
	     			Txt_Sucess6.setVisibility(View.VISIBLE);
	     			img_delete6.setVisibility(View.VISIBLE);
	     			Ln_Ch6.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}
				if (Sucess7 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);
	     			Txt_Sucess7.setVisibility(View.VISIBLE);
	     			img_delete7.setVisibility(View.VISIBLE);
	     			Ln_Ch7.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}
				if (Sucess8 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);	
	     			Txt_Sucess8.setVisibility(View.VISIBLE);
	     			img_delete8.setVisibility(View.VISIBLE);
	     			Ln_Ch8.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}
				if (Sucess9 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);
	     			Txt_Sucess9.setVisibility(View.VISIBLE);
	     			img_delete9.setVisibility(View.VISIBLE);
	     		Ln_Ch9.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}
				if (Sucess10 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);
	     			Txt_Sucess10.setVisibility(View.VISIBLE);
	     			img_delete10.setVisibility(View.VISIBLE);
	     			Ln_Ch10.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}
				if (Sucess11 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);	
	     			Txt_Sucess11.setVisibility(View.VISIBLE);
	     			img_delete11.setVisibility(View.VISIBLE);
	     			Ln_Ch11.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}
				if (Sucess12 == true) {
					
					mStartButton.setVisibility(View.VISIBLE);
	     			Txt_Sucess12.setVisibility(View.VISIBLE);
	     			img_delete12.setVisibility(View.VISIBLE);
	     			Ln_Ch12.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	     	
				
			}if (Sucess13 == true) {
				
				mStartButton.setVisibility(View.VISIBLE);
     			Txt_Sucess13.setVisibility(View.VISIBLE);
     			img_delete13.setVisibility(View.VISIBLE);
     			Ln_Ch13.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
     	
			
		}
				
	}
	// Starts the chosen activity
    private void startARActivity()
    {
    	
    	try{
        Intent i = new Intent();
        i.setClassName(mClassToLaunchPackage, mClassToLaunch);
        AboutScreen3.this.startActivity(i);
    	}
    	catch(Exception e){
    	System.out.println("gfgfgf"+"    "+e);
    	}
    }
    
    
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_start:
                startARActivity();
                break;
            case R.id.button_download:
            //	sv.scrollTo(0, sv.getBottom());
            	
            	Chapter_detail.setVisibility(View.VISIBLE);
            	
            /*	sv.post(new Runnable() { 
                    public void run() { 
                    	sv.scrollTo(10, sv.getBottom());
                    } 
            });*/
            	
             
                break;
 case R.id.Linear_Ch_1:
            	
            	
        
            	
            	Value=null;
                
            	// get Internet status
				isInternetPresent = cd.isConnectingToInternet();

				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					
					
					
					if(Sucess1== false && ActivitySplashScreen.Av_Sucess1.isEmpty())
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/a.zip"); 
	                
	                Value="a.zip";
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/a.zip";
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/a.zip";
						}

	                System.out.println("qqqqqq"+" "+Value);
					}
					else{
						
						
						
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						 
				        // Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				 
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				 
				        // Setting Icon to Dialog
				   //     alertDialog.setIcon(R.drawable.delete);
				 
				        // Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
								File dir;
				            // Write your code here to invoke YES event
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
									dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									dir = Environment.getExternalStorageDirectory();
								}

						     //   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
								// System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/a.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "a.zip");
									}else{
										file1 = new File(Environment.getExternalStorageDirectory(), "a.zip");
									}
									boolean deleted1 = file1.delete();
									if(deleted1== true)
									{
										File filev;
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
											filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/a.mp4");
										}else{
											filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/a.mp4");
										}

										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											
											Chapter_detail.setVisibility(View.VISIBLE);
											ActivitySplashScreen.Av_Sucess1="";
										}
										Txt_Sucess1.setVisibility(View.GONE);
										img_delete1.setVisibility(View.GONE);
										
										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								            SharedPreferences.Editor editor = sharedpreferences.edit();
								            
								            editor.putBoolean("Sucess1", false);
								            
								            editor.commit();
								            
								            
								            Chapter_Details_Value="abc";
										Ln_Ch1.setBackground(getResources().getDrawable(R.drawable.ch_bg));
										 
									}
						        }
						        else
						        {
									File filev;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/a.mp4");
									}else{
										filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/a.mp4");
									}

									deleted = filev.delete();
									if(deleted== true)
									{
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										Chapter_detail.setVisibility(View.VISIBLE);
										ActivitySplashScreen.Av_Sucess1="";
									}
									Txt_Sucess1.setVisibility(View.GONE);
									img_delete1.setVisibility(View.GONE);
									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
							            SharedPreferences.Editor editor = sharedpreferences.edit();
							            editor.putBoolean("Sucess1", false);
							            editor.commit();
							            Chapter_Details_Value="abc";
									Ln_Ch1.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
				            }
				        });
				 
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
						
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}
              
                break;
				case R.id.Linear_Ch_2:
            	Value=null;
	                      // get Internet status
				isInternetPresent = cd.isConnectingToInternet();
				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					if(Sucess2== false && ActivitySplashScreen.Av_Sucess2.isEmpty())
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/b.zip"); 
	                Value="b.zip";
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/b.zip";
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/b.zip";
						}
						System.out.println("qqqqqq"+" "+Value);
					}
					else{
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						 
				        // Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				 
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				 
				        // Setting Icon to Dialog
				   //     alertDialog.setIcon(R.drawable.delete);
				 
				        // Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
								File dir;
								// Write your code here to invoke YES event
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
									 dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									 dir = Environment.getExternalStorageDirectory();
								}

						     //   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
						        
						       // System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/b.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "b.zip");
									}else{
										file1 = new File(Environment.getExternalStorageDirectory(), "b.zip");
									}
									boolean deleted1 = file1.delete();
									if(deleted1== true)
									{
										File filev;
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
											filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/b.mp4");
										}else{
											filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/b.mp4");
										}
										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											Chapter_detail.setVisibility(View.VISIBLE);
											ActivitySplashScreen.Av_Sucess2="";
										}
										Txt_Sucess2.setVisibility(View.GONE);
										img_delete2.setVisibility(View.GONE);
										sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								            SharedPreferences.Editor editor = sharedpreferences.edit();
								            editor.putBoolean("Sucess2", false);
								            editor.commit();
								            
								            
								            Chapter_Details_Value="abc";
										Ln_Ch2.setBackground(getResources().getDrawable(R.drawable.ch_bg));
										 
									}
						        }
						        else
						        {
									File filev;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/b.mp4");
									}else{
										filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/b.mp4");
									}
									deleted = filev.delete();
									if(deleted== true)
									{
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										Chapter_detail.setVisibility(View.VISIBLE);
										ActivitySplashScreen.Av_Sucess2="";
									}
									Txt_Sucess2.setVisibility(View.GONE);
									img_delete2.setVisibility(View.GONE);
									sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
									SharedPreferences.Editor editor = sharedpreferences.edit();
									editor.putBoolean("Sucess2", false);
									editor.commit();
									Chapter_Details_Value="abc";
									Ln_Ch2.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
				            }
				        });
				 
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
						
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}
              
                break;
           case R.id.Linear_Ch_3:
            	
            	
                Value=null;
             // get Internet status
				isInternetPresent = cd.isConnectingToInternet();

				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					
					
					
					if(Sucess3== false && ActivitySplashScreen.Av_Sucess3.isEmpty())
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/c.zip"); 
	                
	                Value="c.zip";

						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/c.zip";
							System.out.println("qqqqqq"+" "+Value);
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/c.zip";
							System.out.println("qqqqqq"+" "+Value);
						}

					}
					else{
						
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						// Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				        // Setting Icon to Dialog
				   //     alertDialog.setIcon(R.drawable.delete);
						// Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
								File dir;
				            // Write your code here to invoke YES event
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
									dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									dir = Environment.getExternalStorageDirectory();
								}
								//   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
								// System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/c.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										 file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "c.zip");
									}else{
										file1 = new File(Environment.getExternalStorageDirectory(), "c.zip");
									}
									boolean deleted1 = file1.delete();
									if(deleted1== true)
									{
										File filev;
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
											filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/c.mp4");
										}else{
											filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/c.mp4");
										}

										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											Chapter_detail.setVisibility(View.VISIBLE);
											ActivitySplashScreen.Av_Sucess3="";
										}
										Txt_Sucess3.setVisibility(View.GONE);
										img_delete3.setVisibility(View.GONE);
										sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
										SharedPreferences.Editor editor = sharedpreferences.edit();
										editor.putBoolean("Sucess3", false);
										editor.commit();
										Chapter_Details_Value="abc";
										Ln_Ch3.setBackground(getResources().getDrawable(R.drawable.ch_bg));
									}
						        }
						        else
						        {
									File filev;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/c.mp4");
									}else{
										filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/c.mp4");
									}

									deleted = filev.delete();
									if(deleted== true)
									{
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										Chapter_detail.setVisibility(View.VISIBLE);
										ActivitySplashScreen.Av_Sucess3="";
									}
									Txt_Sucess3.setVisibility(View.GONE);
									img_delete3.setVisibility(View.GONE);
									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
							            SharedPreferences.Editor editor = sharedpreferences.edit();
							            editor.putBoolean("Sucess3", false);
							            editor.commit();
							            Chapter_Details_Value="abc";
									Ln_Ch3.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
				            }
				        });
				 
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}
				break;
				case R.id.Linear_Ch_4:
					Value=null;
        		// get Internet status
				isInternetPresent = cd.isConnectingToInternet();
				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					if(Sucess4== false && ActivitySplashScreen.Av_Sucess4.isEmpty())
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/d.zip"); 
	                Value="d.zip";
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/d.zip";
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/d.zip";
						}
						System.out.println("qqqqqq"+" "+Value);
					}
					else{
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						// Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				        // Setting Icon to Dialog
				   //     alertDialog.setIcon(R.drawable.delete);
				 
				        // Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
				 
				            // Write your code here to invoke YES event
								File dir;
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
									dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									dir = Environment.getExternalStorageDirectory();
								}

						     //   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
						        
						       // System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/d.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "d.zip");
									}else{
										file1 = new File(Environment.getExternalStorageDirectory(), "d.zip");
									}

									boolean deleted1 = file1.delete();

									
									if(deleted1== true)
									{
										File filev;
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
											 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/d.mp4");
										}else{
											 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/d.mp4");
										}

										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											
											Chapter_detail.setVisibility(View.VISIBLE);
											ActivitySplashScreen.Av_Sucess4="";
										}
										Txt_Sucess4.setVisibility(View.GONE);
										img_delete4.setVisibility(View.GONE);
										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								            SharedPreferences.Editor editor = sharedpreferences.edit();
								            
								            editor.putBoolean("Sucess4", false);
								            editor.commit();
								            Chapter_Details_Value="abc";
								            Ln_Ch4.setBackground(getResources().getDrawable(R.drawable.ch_bg));
									}
						        }
						        else {
									File filev;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/d.mp4");
									}else{
										 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/d.mp4");
									}
									deleted = filev.delete();
									if(deleted== true) {
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										Chapter_detail.setVisibility(View.VISIBLE);
										ActivitySplashScreen.Av_Sucess4="";
									}
									Txt_Sucess4.setVisibility(View.GONE);
									img_delete4.setVisibility(View.GONE);
									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
							            SharedPreferences.Editor editor = sharedpreferences.edit();
							            editor.putBoolean("Sucess4", false);
							            editor.commit();
							            Chapter_Details_Value="abc";
									Ln_Ch4.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
				            }
				        });
				 
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
						
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}
             
      break;
         case R.id.Linear_Ch_5:
  	
        	  Value=null;
        	// get Internet status
				isInternetPresent = cd.isConnectingToInternet();

				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					
					
					
					if(Sucess5== false && ActivitySplashScreen.Av_Sucess5.isEmpty())
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/e.zip"); 
	                
	                Value="e.zip";
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/e.zip";
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/e.zip";
						}

	                System.out.println("qqqqqq"+" "+Value);
					}
					else{
						
						
						
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						 
				        // Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				 
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				 
				        // Setting Icon to Dialog
				   //     alertDialog.setIcon(R.drawable.delete);
				 
				        // Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
				 
				            // Write your code here to invoke YES event
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								File dir;
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
									dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									 dir = Environment.getExternalStorageDirectory();
								}

						     //   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
						        
						       // System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/e.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										 file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "e.zip");
									}else{
										 file1 = new File(Environment.getExternalStorageDirectory(), "e.zip");
									}

									boolean deleted1 = file1.delete();

									
									if(deleted1== true)
									{
										File filev;
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
											filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/e.mp4");
										}else{
											 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/e.mp4");
										}

										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											
											Chapter_detail.setVisibility(View.VISIBLE);
											
											ActivitySplashScreen.Av_Sucess5="";
										}
										Txt_Sucess5.setVisibility(View.GONE);
										img_delete5.setVisibility(View.GONE);
										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								            SharedPreferences.Editor editor = sharedpreferences.edit();
								            
								            editor.putBoolean("Sucess5", false);
								            
								            editor.commit();
								            
								            
								            Chapter_Details_Value="abc";
										Ln_Ch5.setBackground(getResources().getDrawable(R.drawable.ch_bg));
										 
									}
						        }
						        else
						        {
									File filev;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/e.mp4");
									}else{
										filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/e.mp4");
									}

									deleted = filev.delete();
									if(deleted== true)
									{
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										
										Chapter_detail.setVisibility(View.VISIBLE);
										
										ActivitySplashScreen.Av_Sucess5="";
									}
									Txt_Sucess5.setVisibility(View.GONE);
									img_delete5.setVisibility(View.GONE);
									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
							            SharedPreferences.Editor editor = sharedpreferences.edit();
							            
							            editor.putBoolean("Sucess5", false);
							            
							            editor.commit();
							            
							            
							            Chapter_Details_Value="abc";
									Ln_Ch5.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
						  
				            
				            
				            }
				        });
				 
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
						
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}
            
      break;
        case R.id.Linear_Ch_6:
  	
  	
        	  Value=null;
        	// get Internet status
				isInternetPresent = cd.isConnectingToInternet();

				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					
					
					
					if(Sucess6== false && ActivitySplashScreen.Av_Sucess6.isEmpty())
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/f.zip"); 
	                
	                Value="f.zip";
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/f.zip";
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/f.zip";
						}

	                System.out.println("qqqqqq"+" "+Value);
					}
					else{
						
						
						
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						 
				        // Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				 
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				 
				        // Setting Icon to Dialog
				   //     alertDialog.setIcon(R.drawable.delete);
				 
				        // Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
								File dir;
				            // Write your code here to invoke YES event
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
								dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									 dir = Environment.getExternalStorageDirectory();
								}

						     //   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
						        
						       // System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/f.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										 file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "f.zip");
									}else{
										 file1 = new File(Environment.getExternalStorageDirectory(), "f.zip");
									}

									boolean deleted1 = file1.delete();

									
									if(deleted1== true)
									{
										File filev;
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
											filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/f.mp4");
										}else{
											 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/f.mp4");
										}

										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											
											Chapter_detail.setVisibility(View.VISIBLE);
											
											ActivitySplashScreen.Av_Sucess6="";
										}
										Txt_Sucess6.setVisibility(View.GONE);
										img_delete6.setVisibility(View.GONE);
										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								            SharedPreferences.Editor editor = sharedpreferences.edit();
								            
								            editor.putBoolean("Sucess6", false);
								            
								            editor.commit();
								            
								            
								            Chapter_Details_Value="abc";
										Ln_Ch6.setBackground(getResources().getDrawable(R.drawable.ch_bg));
										 
									}
						        }
						        else
						        {
									File filev;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/f.mp4");
									}else{
										filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/f.mp4");
									}

									deleted = filev.delete();
									if(deleted== true)
									{
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										Chapter_detail.setVisibility(View.VISIBLE);
										ActivitySplashScreen.Av_Sucess6="";
									}
									Txt_Sucess6.setVisibility(View.GONE);
									img_delete6.setVisibility(View.GONE);
									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
							            SharedPreferences.Editor editor = sharedpreferences.edit();
							            editor.putBoolean("Sucess6", false);
							            editor.commit();
							            Chapter_Details_Value="abc";
									    Ln_Ch6.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
				            }
				        });
				 
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
						
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}
            
    
      break;
           case R.id.Linear_Ch_7:
  	
  	
        	   Value=null;
        	// get Internet status
				isInternetPresent = cd.isConnectingToInternet();

				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					
					
					
					if(Sucess7== false && ActivitySplashScreen.Av_Sucess7.isEmpty())
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/g.zip"); 
	                
	                Value="g.zip";
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/g.zip";
							System.out.println("qqqqqq"+" "+Value);
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/g.zip";
							System.out.println("qqqqqq"+" "+Value);
						}

					}
					else{

						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						// Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				        // Setting Icon to Dialog
				   //     alertDialog.setIcon(R.drawable.delete);
						// Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
								File dir;
				            // Write your code here to invoke YES event
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
								 dir =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									dir = Environment.getExternalStorageDirectory();
								}

						     //   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
						        
						       // System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/g.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "g.zip");
									}else{
										file1 = new File(Environment.getExternalStorageDirectory(), "g.zip");
									}

									boolean deleted1 = file1.delete();

									
									if(deleted1== true)
									{
										File filev;
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
											filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/g.mp4");
										}else{
											filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/g.mp4");
										}

										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											
											Chapter_detail.setVisibility(View.VISIBLE);
											
											ActivitySplashScreen.Av_Sucess7="";
										}
										Txt_Sucess7.setVisibility(View.GONE);
										img_delete7.setVisibility(View.GONE);
										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								            SharedPreferences.Editor editor = sharedpreferences.edit();
								            
								            editor.putBoolean("Sucess7", false);
								            
								            editor.commit();
								            
								            
								            Chapter_Details_Value="abc";
										Ln_Ch7.setBackground(getResources().getDrawable(R.drawable.ch_bg));
										 
									}
						        }
						        else
						        {
									File filev;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/g.mp4");
									}else{
										 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/g.mp4");
									}

									deleted = filev.delete();
									if(deleted== true)
									{
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										
										Chapter_detail.setVisibility(View.VISIBLE);
										
										ActivitySplashScreen.Av_Sucess7="";
									}
									Txt_Sucess7.setVisibility(View.GONE);
									img_delete7.setVisibility(View.GONE);
									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
							            SharedPreferences.Editor editor = sharedpreferences.edit();
							            editor.putBoolean("Sucess7", false);
							            editor.commit();
							            Chapter_Details_Value="abc";
							            Ln_Ch7.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
						  
				            
				            
				            }
				        });
				 
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
						
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}
             
      break;
        case R.id.Linear_Ch_8:
  	
  	
        	  Value=null;
        	// get Internet status
				isInternetPresent = cd.isConnectingToInternet();

				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					if(Sucess8== false && ActivitySplashScreen.Av_Sucess8.isEmpty())
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/h.zip"); 
	                
	                Value="h.zip";
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/h.zip";
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/h.zip";
						}

	                System.out.println("qqqqqq"+" "+Value);
					}
					else{
						
						
						
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						 
				        // Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				 
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				 
				        // Setting Icon to Dialog
				   //     alertDialog.setIcon(R.drawable.delete);
				 
				        // Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
								File dir;
				            // Write your code here to invoke YES event
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
									 dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									dir = Environment.getExternalStorageDirectory();
								}

								// File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
								//System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/h.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "h.zip");
									}else{
										file1 = new File(Environment.getExternalStorageDirectory(), "h.zip");
									}
									boolean deleted1 = file1.delete();

									
									if(deleted1== true)
									{
										File filev;
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
											 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/h.mp4");
										}else{
											 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/h.mp4");
										}
										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											
											Chapter_detail.setVisibility(View.VISIBLE);
											
											ActivitySplashScreen.Av_Sucess8="";
										}
										Txt_Sucess8.setVisibility(View.GONE);
										img_delete8.setVisibility(View.GONE);
										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								            SharedPreferences.Editor editor = sharedpreferences.edit();
								            
								            editor.putBoolean("Sucess8", false);
								            
								            editor.commit();
								            
								            
								            Chapter_Details_Value="abc";
										Ln_Ch8.setBackground(getResources().getDrawable(R.drawable.ch_bg));
										 
									}
						        }
						        else
						        {
									File filev;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										 filev= new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS ), ".Videoplayback/MathsBuzz1/h.mp4");
									}else{
										filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/h.mp4");
									}

									deleted = filev.delete();
									if(deleted== true)
									{
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										Chapter_detail.setVisibility(View.VISIBLE);
										ActivitySplashScreen.Av_Sucess8="";
									}
									Txt_Sucess8.setVisibility(View.GONE);
									img_delete8.setVisibility(View.GONE);
									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
							            SharedPreferences.Editor editor = sharedpreferences.edit();
							            
							            editor.putBoolean("Sucess8", false);
							            
							            editor.commit();
							            
							            
							            Chapter_Details_Value="abc";
									Ln_Ch8.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
						  
				            
				            
				            }
				        });
				 
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
						
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection", "You don't have internet connection.", false);
				}
            
      break;
            case R.id.Linear_Ch_9:
  	
            	// get Internet status
				isInternetPresent = cd.isConnectingToInternet();

				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					
					
					
					if(Sucess9== false && ActivitySplashScreen.Av_Sucess9.isEmpty())
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/i.zip"); 
	                
	                Value="i.zip";
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/i.zip";
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/i.zip";
						}

	                System.out.println("qqqqqq"+" "+Value);
					}
					else{
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						// Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				        // Setting Icon to Dialog
				   //     alertDialog.setIcon(R.drawable.delete);
				 
				        // Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
				 
				            // Write your code here to invoke YES event
								File dir;
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
									dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									dir = Environment.getExternalStorageDirectory();
								}
								//   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
								// System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/i.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
										file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "i.zip");
									}else{
										file1 = new File(Environment.getExternalStorageDirectory(), "i.zip");
									}
									boolean deleted1 = file1.delete();
									File filev;
									if(deleted1== true) {
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
											 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/i.mp4");
										}else{
											 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/i.mp4");
										}

										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											Chapter_detail.setVisibility(View.VISIBLE);
											ActivitySplashScreen.Av_Sucess9="";
										}
										Txt_Sucess9.setVisibility(View.GONE);
										img_delete9.setVisibility(View.GONE);
										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								            SharedPreferences.Editor editor = sharedpreferences.edit();
								            editor.putBoolean("Success9", false);
								            editor.commit();
								            Chapter_Details_Value="abc";
											Ln_Ch9.setBackground(getResources().getDrawable(R.drawable.ch_bg));
									}
						        }
						        else
						        {
									File filev;
									if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
										filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/i.mp4");
									}else{
										filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/i.mp4");
									}
									deleted = filev.delete();
									if(deleted== true)
									{
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										Chapter_detail.setVisibility(View.VISIBLE);
										ActivitySplashScreen.Av_Sucess9="";
									}
									Txt_Sucess9.setVisibility(View.GONE);
									img_delete9.setVisibility(View.GONE);
									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
							            SharedPreferences.Editor editor = sharedpreferences.edit();
							            editor.putBoolean("Sucess9", false);
							            editor.commit();
							            Chapter_Details_Value="abc";
									Ln_Ch9.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
				            }
				        });
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
						
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}
              
      break;
            case R.id.Linear_Ch_10:
  	
            	  Value=null;
            	// get Internet status
  				isInternetPresent = cd.isConnectingToInternet();

  				// check for Internet status
  				if (isInternetPresent) {
  					// Internet Connection is Present
  					
  					
  					
  					if(Sucess10== false && ActivitySplashScreen.Av_Sucess10.isEmpty())
  					{	DownloadMapAsync mew = new DownloadMapAsync();
  	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/j.zip"); 
  	                
  	                Value="j.zip";
						if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/j.zip";
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/j.zip";
						}

  	                System.out.println("qqqqqq"+" "+Value);
  					}
  					else{
  						
  						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
  						// Setting Dialog Title
  				        alertDialog.setTitle("Confirm Delete...");
  				        // Setting Dialog Message
  				        alertDialog.setMessage("Are you sure you want delete this?");
  				        // Setting Icon to Dialog
  				   //     alertDialog.setIcon(R.drawable.delete);
						// Setting Positive "Yes" Button
  				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
  				            public void onClick(DialogInterface dialog,int which) {
  				            	// Write your code here to invoke YES event
  				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								File dir;
								if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
									dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									dir = Environment.getExternalStorageDirectory();
								}
								//   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
								// System.out.println("aaaaaa"+"   "+yourFile);
  						        File file = new File(dir, "/j.zip" );
  						        if (file.exists()) {
  						          //Do action
									File file1;
									if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
										file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "j.zip");
									}else{
										file1 = new File(Environment.getExternalStorageDirectory(), "j.zip");
									}
									boolean deleted1 = file1.delete();
									if(deleted1== true) {
										File filev;
  										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
											filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/j.mp4");
										}else{
											filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/j.mp4");
										}

  										deleted = filev.delete();
  										if(deleted== true)
  										{
  											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
  											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
  											startActivity(refresh);
  											AboutScreen3.this.finish();
  											Chapter_detail.setVisibility(View.VISIBLE);
  											ActivitySplashScreen.Av_Sucess10="";
  										}
  										Txt_Sucess10.setVisibility(View.GONE);
  										img_delete10.setVisibility(View.GONE);
  										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
  								            SharedPreferences.Editor editor = sharedpreferences.edit();
  								            editor.putBoolean("Sucess10", false);
  								            editor.commit();
  								            Chapter_Details_Value="abc";
  								            Ln_Ch10.setBackground(getResources().getDrawable(R.drawable.ch_bg));
									}
  						        }
  						        else
  						        {
  						        	File filev;
									if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
										 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/j.mp4");
									}else{
										 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/j.mp4");
									}
									deleted = filev.delete();
  									if(deleted== true)
  									{
  										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
  										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
  										startActivity(refresh);
  										AboutScreen3.this.finish();
  										Chapter_detail.setVisibility(View.VISIBLE);
  										ActivitySplashScreen.Av_Sucess10="";
  									}
  									Txt_Sucess10.setVisibility(View.GONE);
  									img_delete10.setVisibility(View.GONE);
  									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
  							            SharedPreferences.Editor editor = sharedpreferences.edit();
  							            editor.putBoolean("Sucess10", false);
  							            editor.commit();
  							            Chapter_Details_Value="abc";
  									Ln_Ch10.setBackground(getResources().getDrawable(R.drawable.ch_bg));
  						        }
  				            }
  				        });
  				        // Setting Negative "NO" Button
  				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
  				            public void onClick(DialogInterface dialog, int which) {
  				            // Write your code here to invoke NO event
  				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
  				            dialog.cancel();
  				            }
  				        });
  				        // Showing Alert Message
  				        alertDialog.show();	
  					}
  				} else {
  					// Internet connection is not present
  					// Ask user to connect to Internet
  					showAlertDialog(AboutScreen3.this, "No Internet Connection",
  							"You don't have internet connection.", false);
  				}
                
     			 break;
                case R.id.Linear_Ch_11:
  	
                	// get Internet status
    				isInternetPresent = cd.isConnectingToInternet();
    				// check for Internet status
    				if (isInternetPresent) {
    					// Internet Connection is Present
						if(Sucess11== false && ActivitySplashScreen.Av_Sucess11.isEmpty()) {
							DownloadMapAsync mew = new DownloadMapAsync();
    	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/k.zip"); 
    	                Value="k.zip";
    	                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
								zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/k.zip";
							}else{
								zipFile =Environment.getExternalStorageDirectory() + "/k.zip";
							}
    	                	System.out.println("qqqqqq"+" "+Value);
    					}
    					else{
    						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
    						// Setting Dialog Title
    				        alertDialog.setTitle("Confirm Delete...");
    				        // Setting Dialog Message
    				        alertDialog.setMessage("Are you sure you want delete this?");
    				        // Setting Icon to Dialog
							//  alertDialog.setIcon(R.drawable.delete);
							// Setting Positive "Yes" Button
    				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
    				            public void onClick(DialogInterface dialog,int which) {
    				            	// Write your code here to invoke YES event
									File dir;
    				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
									if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
										dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
									}else{
										dir = Environment.getExternalStorageDirectory();
									}
									//   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
									// System.out.println("aaaaaa"+"   "+yourFile);
									File file = new File(dir, "/k.zip" );
    						        if (file.exists()) {
    						          //Do action
										File file1;
										if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
											file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "k.zip");
										}else{
											file1 = new File(Environment.getExternalStorageDirectory(), "k.zip");
										}
										boolean deleted1 = file1.delete();
										if(deleted1== true) {
											File filev;
    										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
											if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
												 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/k.mp4");
											}else{
												filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/k.mp4");
										}
											deleted = filev.delete();
    										if(deleted== true) {
    											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
    											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
    											startActivity(refresh);
    											AboutScreen3.this.finish();
    											Chapter_detail.setVisibility(View.VISIBLE);
    											ActivitySplashScreen.Av_Sucess11="";
    										}
    											Txt_Sucess11.setVisibility(View.GONE);
    											img_delete11.setVisibility(View.GONE);
    										 	sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    								            SharedPreferences.Editor editor = sharedpreferences.edit();
    								            editor.putBoolean("Sucess11", false);
    								            editor.commit();
    								            Chapter_Details_Value="abc";
    										Ln_Ch11.setBackground(getResources().getDrawable(R.drawable.ch_bg));
    									}
    						        }
    						        else {
										File filev;
										if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
											filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/k.mp4");
										}else{
											filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/k.mp4");
										}

    									deleted = filev.delete();
    									if(deleted== true) {
    										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
    										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
    										startActivity(refresh);
    										AboutScreen3.this.finish();
    										Chapter_detail.setVisibility(View.VISIBLE);
    										ActivitySplashScreen.Av_Sucess11="";
    									}
    									Txt_Sucess11.setVisibility(View.GONE);
    									img_delete11.setVisibility(View.GONE);
    									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    							            SharedPreferences.Editor editor = sharedpreferences.edit();
    							            editor.putBoolean("Sucess11", false);
    							            editor.commit();
    							            Chapter_Details_Value="abc";
    										Ln_Ch11.setBackground(getResources().getDrawable(R.drawable.ch_bg));
    						        }
    				            }
    				        });
    				        // Setting Negative "NO" Button
    				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
    				            public void onClick(DialogInterface dialog, int which) {
    				            // Write your code here to invoke NO event
    				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
    				            dialog.cancel();
    				            }
    				        });
    				        // Showing Alert Message
    				        alertDialog.show();	
    					}
    				} else {
		// Internet connection is not present
		// Ask user to connect to Internet
		showAlertDialog(AboutScreen3.this, "No Internet Connection",
				"You don't have internet connection.", false);
	}
    				break;
            case R.id.Linear_Ch_12:
  	
            	// get Internet status
				isInternetPresent = cd.isConnectingToInternet();

				// check for Internet status
				if (isInternetPresent) {
					// Internet Connection is Present
					if(Sucess12== false && ActivitySplashScreen.Av_Sucess12.isEmpty() )
					{	DownloadMapAsync mew = new DownloadMapAsync();
	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/l.zip"); 
	                Value="l.zip";
						if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
							zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/l.zip";
						}else{
							zipFile =Environment.getExternalStorageDirectory() + "/l.zip";
						}

	                System.out.println("qqqqqq"+" "+Value);
					}
					else{
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
						// Setting Dialog Title
				        alertDialog.setTitle("Confirm Delete...");
				        // Setting Dialog Message
				        alertDialog.setMessage("Are you sure you want delete this?");
				        // Setting Icon to Dialog
				    	// alertDialog.setIcon(R.drawable.delete);
						// Setting Positive "Yes" Button
				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
				 
				            // Write your code here to invoke YES event
				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
								File dir;
								if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
									dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
								}else{
									dir = Environment.getExternalStorageDirectory();
								}
								//File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
								// System.out.println("aaaaaa"+"   "+yourFile);
						        File file = new File(dir, "/l.zip" );
						        if (file.exists()) {
						          //Do action
									File file1;
									if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
										file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "l.zip");
									}else{
										file1 = new File(Environment.getExternalStorageDirectory(), "l.zip");
									}

									boolean deleted1 = file1.delete();

									
									if(deleted1== true)
									{
										File filev;
										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
										if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
											 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/l.mp4");
										}else{
											filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/l.mp4");
										}

										deleted = filev.delete();
										if(deleted== true)
										{
											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
											startActivity(refresh);
											AboutScreen3.this.finish();
											
											Chapter_detail.setVisibility(View.VISIBLE);
											ActivitySplashScreen.Av_Sucess12="";
										}
										Txt_Sucess12.setVisibility(View.GONE);
										img_delete12.setVisibility(View.GONE);
										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
								            SharedPreferences.Editor editor = sharedpreferences.edit();
								            editor.putBoolean("Sucess12", false);
								            editor.commit();
								            Chapter_Details_Value="abc";
										    Ln_Ch12.setBackground(getResources().getDrawable(R.drawable.ch_bg));
									}
						        }
						        else
						        {
									File filev;
									if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
										filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/l.mp4");
									}else{
										filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/l.mp4");
									}
									deleted = filev.delete();
									if(deleted== true)
									{
										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
										startActivity(refresh);
										AboutScreen3.this.finish();
										Chapter_detail.setVisibility(View.VISIBLE);
										ActivitySplashScreen.Av_Sucess12="";
									}
									Txt_Sucess12.setVisibility(View.GONE);
									img_delete12.setVisibility(View.GONE);
									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
							            SharedPreferences.Editor editor = sharedpreferences.edit();
							            editor.putBoolean("Sucess12", false);
							            editor.commit();
							            Chapter_Details_Value="abc";
									Ln_Ch12.setBackground(getResources().getDrawable(R.drawable.ch_bg));
						        }
				            }
				        });
				 
				        // Setting Negative "NO" Button
				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				            // Write your code here to invoke NO event
				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
				            dialog.cancel();
				            }
				        });
				 
				        // Showing Alert Message
				        alertDialog.show();	
						
					}
				} else {
					// Internet connection is not present
					// Ask user to connect to Internet
					showAlertDialog(AboutScreen3.this, "No Internet Connection",
							"You don't have internet connection.", false);
				}
				break;
                  case R.id.Linear_Ch_13:
                	  
                	  Value=null;
                	// get Internet status
      				isInternetPresent = cd.isConnectingToInternet();

      				// check for Internet status
      				if (isInternetPresent) {
      					// Internet Connection is Present
						if(Sucess13== false && ActivitySplashScreen.Av_Sucess13.isEmpty())
      					{	DownloadMapAsync mew = new DownloadMapAsync();
      	                mew.execute("http://gowebrachnasagar.com/videoplayback/Maths1/m.zip"); 
      	                Value="m.zip";
							if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
								zipFile =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/m.zip";
							}else{
								zipFile =Environment.getExternalStorageDirectory() + "/m.zip";
							}
							System.out.println("qqqqqq"+" "+Value);
      					}
      					else{

      						AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);
      						// Setting Dialog Title
      				        alertDialog.setTitle("Confirm Delete...");
      				        // Setting Dialog Message
      				        alertDialog.setMessage("Are you sure you want delete this?");
      				        // Setting Icon to Dialog
      				   //     alertDialog.setIcon(R.drawable.delete);
							// Setting Positive "Yes" Button
      				        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
      				            public void onClick(DialogInterface dialog,int which) {
      				 
      				            // Write your code here to invoke YES event
      				            Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
									File dir;
									if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
										dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
									}else{
										dir = Environment.getExternalStorageDirectory();
									}

      						     //   File yourFile = new File(dir, "/.VideoPlayBack/Maths1/a.mp4");
      						        
      						       // System.out.println("aaaaaa"+"   "+yourFile);
      						        File file = new File(dir, "/m.zip" );
      						        if (file.exists()) {
      						          //Do action
										File file1;
										if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
											file1 = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "m.zip");
										}else{
											file1 = new File(Environment.getExternalStorageDirectory(), "m.zip");
										}

      									boolean deleted1 = file1.delete();

      									
      									if(deleted1== true)
      									{
      										Toast.makeText(getApplicationContext(), "File Deleted ", Toast.LENGTH_SHORT).show();
											File filev;
											if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
												 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/m.mp4");
											}else{
												 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/m.mp4");
											}
											deleted = filev.delete();
      										if(deleted== true)
      										{
      											Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
      											Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
      											startActivity(refresh);
      											AboutScreen3.this.finish();
      											
      											Chapter_detail.setVisibility(View.VISIBLE);
      											ActivitySplashScreen.Av_Sucess13="";
      										}
      										Txt_Sucess13.setVisibility(View.GONE);
      										img_delete13.setVisibility(View.GONE);
      										 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
      								            SharedPreferences.Editor editor = sharedpreferences.edit();
      								            
      								            editor.putBoolean("Sucess13", false);
      								            
      								            editor.commit();
      								            
      								            
      								            Chapter_Details_Value="abc";
      										Ln_Ch13.setBackground(getResources().getDrawable(R.drawable.ch_bg));
      										 
      									}
      						        }
      						        else {
										File filev;
										if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
											 filev = new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), ".Videoplayback/MathsBuzz1/m.mp4");
										}else{
											 filev = new File(Environment.getExternalStorageDirectory(), ".Videoplayback/MathsBuzz1/m.mp4");
										}
										deleted = filev.delete();
      									if(deleted== true) {
      										Toast.makeText(getApplicationContext(), "File Deleted new ", Toast.LENGTH_SHORT).show();
      										Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
      										startActivity(refresh);
      										AboutScreen3.this.finish();
      										Chapter_detail.setVisibility(View.VISIBLE);
      										ActivitySplashScreen.Av_Sucess13="";
      									}
      									Txt_Sucess13.setVisibility(View.GONE);
      									img_delete13.setVisibility(View.GONE);
      									 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
      							            SharedPreferences.Editor editor = sharedpreferences.edit();
      							            editor.putBoolean("Sucess13", false);
      							            editor.commit();
      							            Chapter_Details_Value="abc";
      									Ln_Ch13.setBackground(getResources().getDrawable(R.drawable.ch_bg));
      						        }
      				            }
      				        });
      				 
      				        // Setting Negative "NO" Button
      				        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
      				            public void onClick(DialogInterface dialog, int which) {
      				            // Write your code here to invoke NO event
      				            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
      				            dialog.cancel();
      				            }
      				        });
      				 
      				        // Showing Alert Message
      				        alertDialog.show();	
      						
      					}
      				} else {
      					// Internet connection is not present
      					// Ask user to connect to Internet
      					showAlertDialog(AboutScreen3.this, "No Internet Connection",
      							"You don't have internet connection.", false);
      				}
                    
    
      break;
                      
 


case R.id.Ln_Delete_All:


	if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
		dirDeleteFolder= new File(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)+"/.VideoPlayBack/MathsBuzz1");
	}else{
		dirDeleteFolder= new File(Environment.getExternalStorageDirectory()+"/.VideoPlayBack/MathsBuzz1");
	}

	if (dirDeleteFolder.isDirectory()) 
	{
		
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(AboutScreen3.this);

	    alertDialog.setTitle("Confirm Delete...");

	    alertDialog.setMessage("Are you sure you want delete this?");

	   
	    alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog,int which) {
	        	
	        	
	        	
	        	
	        	  String[] children = dirDeleteFolder.list();
	        	  
	        	  
	        	  
	        	 dirDeleteFolder.delete();
	      	       
	      	     
	      	       
	      	       
	      	       Intent refresh = new Intent(AboutScreen3.this, AboutScreen3.class);
	      			startActivity(refresh);
	      			AboutScreen3.this.finish();
	      			
	      			
	      			
	        	
	      	    for (int i = 0; i < children.length; i++)
	      	    {
	      	    	
	      	    	
	      	    	
	      	    	
	      	       new File(dirDeleteFolder, children[i]).delete();
	      	       
	      	    
	      	       
	      			
	      			
	      			
	      			  ActivitySplashScreen.Av_Sucess1="";
	          	       ActivitySplashScreen.Av_Sucess2="";
	          	       ActivitySplashScreen.Av_Sucess3="";
	          	       ActivitySplashScreen.Av_Sucess4="";
	          	       ActivitySplashScreen.Av_Sucess5="";
	          	       ActivitySplashScreen.Av_Sucess6="";
	          	       ActivitySplashScreen.Av_Sucess7="";
	          	       ActivitySplashScreen.Av_Sucess8="";
	          	       ActivitySplashScreen.Av_Sucess9="";
	          	       ActivitySplashScreen.Av_Sucess10="";
	          	       ActivitySplashScreen.Av_Sucess11="";
	          	       ActivitySplashScreen.Av_Sucess12="";
	          	       ActivitySplashScreen.Av_Sucess13="";
	          	      
	          	       
	          	       sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
	      	            SharedPreferences.Editor editor = sharedpreferences.edit();
	      	            
	      	            editor.putBoolean("Sucess1", false);
	      	            editor.putBoolean("Sucess2", false);
	      	            editor.putBoolean("Sucess3", false);
	      	            editor.putBoolean("Sucess4", false);
	      	            editor.putBoolean("Sucess5", false);
	      	            editor.putBoolean("Sucess6", false);
	      	            editor.putBoolean("Sucess7", false);
	      	            editor.putBoolean("Sucess8", false);
	      	            editor.putBoolean("Sucess9", false);
	      	            editor.putBoolean("Sucess10", false);
	      	            editor.putBoolean("Sucess11", false);
	      	            editor.putBoolean("Sucess12", false);
	      	            editor.putBoolean("Sucess13", false);
	      	           
	      	            editor.commit();
	      	       Toast.makeText(getApplicationContext(), "Folder Deleted.... ", Toast.LENGTH_SHORT).show();
	      	    }	
	        	
	        	
	        	
	        }
	    });

	    // Setting Negative "NO" Button
	    alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) {
	        // Write your code here to invoke NO event
	        Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
	        dialog.cancel();
	        }
	    });

	    // Showing Alert Message
	    alertDialog.show();	
	       
	   
	}
	
	else{
		 Toast.makeText(getApplicationContext(), "NO folder available.... ", Toast.LENGTH_SHORT).show();
	}
	
	
	
			
			
break;
           
  
      
        }
    }
    
    
    class DownloadMapAsync extends AsyncTask<String, String, String> {
 	   String result ="";
 	@Override
 	protected void onPreExecute() {
 		super.onPreExecute();
 		mProgressDialog = new ProgressDialog(AboutScreen3.this);
 		mProgressDialog.setMessage("Downloading Zip File..");
 		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
 		mProgressDialog.setCancelable(false);
 		/*mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
            // Set a click listener for progress dialog cancel button
            @Override
            public void onClick(DialogInterface dialog, int which){
                // dismiss the progress dialog
            	mProgressDialog.dismiss();
                // Tell the system about cancellation
                isCanceled = true;
            }
        });*/
 		mProgressDialog.show();
 		
 	
 	}

 	@Override
 	protected String doInBackground(String... aurl) {
 		int count;

 	try {

 	
 	URL url = new URL(aurl[0]);
 	URLConnection conexion = url.openConnection();
 	conexion.connect();
 	int lenghtOfFile = conexion.getContentLength();
 	InputStream input = new BufferedInputStream(url.openStream());
 	OutputStream output = new FileOutputStream(zipFile);

 	if(lenghtOfFile == 0) {
 		showAlertDialog(AboutScreen3.this, "Error In Internet Connection",
				"You don't have proper internet connection.", false);
 	}

 	System.out.println("abcaaaaa"+" "+zipFile+" "+"===="+" "+lenghtOfFile);
		Log.d("pathvv3",""+zipFile);

 	byte data[] = new byte[1024];
 	long total = 0;

 		while ((count = input.read(data)) != -1) {
 			total += count;
 			publishProgress(""+(int)((total*100)/lenghtOfFile));
 			output.write(data, 0, count);
 		}
 		output.close();
 		input.close();
 		result = "true";
    
 	} catch (Exception e) {
 		
 		result = "false";
 	}
 	return null;

 	}
 	protected void onProgressUpdate(String... progress) {
 		 Log.d("ANDRO_ASYNC",progress[0]);
 		 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
 	}

 	@Override
 	protected void onPostExecute(String unused) {
 		mProgressDialog.dismiss();
 		if(result.equalsIgnoreCase("true")){
 		try {
 			unzip();
 			
 			
		
 			
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		}
 		else{
 			
 		}
 	}
 }
    
    public void unzip() throws IOException {
	    mProgressDialog = new ProgressDialog(AboutScreen3.this);
	    mProgressDialog.setMessage("Please Wait...Extracting zip file ... ");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(false);
		/*mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
            // Set a click listener for progress dialog cancel button
            @Override
            public void onClick(DialogInterface dialog, int which){
                // dismiss the progress dialog
            	mProgressDialog.dismiss();
                // Tell the system about cancellation
                isCanceled = true;
            }
        });*/
		mProgressDialog.show();
	    new UnZipTask().execute(zipFile, unzipLocation);
		 }
    

public class UnZipTask extends AsyncTask<String, Void, Boolean> {
  @SuppressWarnings("rawtypes")
  @Override
  protected Boolean doInBackground(String... params) {
      String filePath = params[0];
      String destinationPath = params[1];

      File archive = new File(filePath);
      try {
      	
      	
         ZipFile zipfile = new ZipFile(archive);
          for (Enumeration e = zipfile.entries(); e.hasMoreElements();) {
              ZipEntry entry = (ZipEntry) e.nextElement();
              unzipEntry(zipfile, entry, destinationPath);
          }
          
       
     	    UnzipUtil d = new UnzipUtil(zipFile, unzipLocation);
            d.unzip();
            
            
            
            
            //System.out.println("jhingalala"+"   "+zipFile);
          
      } catch (Exception e) {
       
          return false;
      }

      return true;
  }

  @Override
  protected void onPostExecute(Boolean result) {
	  mProgressDialog.dismiss();
	  Toast.makeText(getApplicationContext(), "Downloading videos completed..", Toast.LENGTH_SHORT).show();
	  System.out.println("jhingalala"+"   "+zipFile);
	  if(zipFile.equalsIgnoreCase("/storage/emulated/0/a.zip") || zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/a.zip"))
	  {
		  //--- 
		  Txt_Sucess1.setVisibility(View.VISIBLE);
		  img_delete1.setVisibility(View.VISIBLE);
		  Ln_Ch1.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		 SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean("Sucess1", true);
            editor.commit();
            Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
			Chapter_detail.setVisibility(View.VISIBLE);
	  }
	   if(zipFile.equalsIgnoreCase("/storage/emulated/0/b.zip") || zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/b.zip") ) {
		  //--- 
		  Txt_Sucess2.setVisibility(View.VISIBLE);
		  img_delete2.setVisibility(View.VISIBLE);
		
		Ln_Ch2.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		  
		 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess2", true);
        
        editor.commit();

        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  System.out.println("ggggggg"+" "+zipFile);
	  }
	  if(zipFile.equalsIgnoreCase("/storage/emulated/0/c.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/c.zip") )
	  {
		  //--- 
		  Txt_Sucess3.setVisibility(View.VISIBLE);
		  img_delete3.setVisibility(View.VISIBLE);
		
		Ln_Ch3.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
		 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            
            editor.putBoolean("Sucess3", true);
            
            editor.commit();

            Chapter_Details_Value="abc";
			  System.out.println("ggggggg"+" "+zipFile);
			  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
				startActivity(refresh);
				AboutScreen3.this.finish();
			  
				Chapter_detail.setVisibility(View.VISIBLE);
		  System.out.println("ggggggg"+" "+zipFile);
	  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/d.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/d.zip") )
  {
	  //--- 
	  Txt_Sucess4.setVisibility(View.VISIBLE);
	  img_delete4.setVisibility(View.VISIBLE);

		
		Ln_Ch4.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess4", true);
        
        editor.commit();
        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  
	  System.out.println("ggggggg"+" "+zipFile);
  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/e.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/e.zip") )
  {
	  //--- 
	  Txt_Sucess5.setVisibility(View.VISIBLE);
	  img_delete5.setVisibility(View.VISIBLE);

		
		Ln_Ch5.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess5", true);
        
        editor.commit();

        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  System.out.println("ggggggg"+" "+zipFile);
  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/f.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/f.zip"))
  {
	  //--- 
	  Txt_Sucess6.setVisibility(View.VISIBLE);
	  img_delete6.setVisibility(View.VISIBLE);
		
		Ln_Ch6.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess6", true);
        
        editor.commit();
        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  
	  System.out.println("ggggggg"+" "+zipFile);
  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/g.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/g.zip") )
  {
	  //--- 
	  Txt_Sucess7.setVisibility(View.VISIBLE);
	  img_delete7.setVisibility(View.VISIBLE);
		
		Ln_Ch7.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess7", true);
        
        editor.commit();

        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  System.out.println("ggggggg"+" "+zipFile);
  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/h.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/h.zip") )
  {
	  //--- 
	  Txt_Sucess8.setVisibility(View.VISIBLE);
	  img_delete8.setVisibility(View.VISIBLE);

		
		Ln_Ch8.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess8", true);
        
        editor.commit();
        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  
	  System.out.println("ggggggg"+" "+zipFile);
  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/i.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/i.zip") )
  {
	  //--- 
	  Txt_Sucess9.setVisibility(View.VISIBLE);
	  img_delete9.setVisibility(View.VISIBLE);
		
		Ln_Ch9.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess9", true);
        
        editor.commit();

        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  System.out.println("ggggggg"+" "+zipFile);
  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/j.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/j.zip") )
  {
	  //--- 
	  Txt_Sucess10.setVisibility(View.VISIBLE);
	  img_delete10.setVisibility(View.VISIBLE);

		
		Ln_Ch10.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess10", true);
        
        editor.commit();

        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  System.out.println("ggggggg"+" "+zipFile);
  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/k.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/k.zip") )
  {
	  //--- 
	  Txt_Sucess11.setVisibility(View.VISIBLE);
	  img_delete11.setVisibility(View.VISIBLE);

		
		Ln_Ch11.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess11", true);
        
        editor.commit();

        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  System.out.println("ggggggg"+" "+zipFile);
  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/l.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/l.zip") )
  {
	  //--- 
	  Txt_Sucess12.setVisibility(View.VISIBLE);
	  img_delete12.setVisibility(View.VISIBLE);

		
		Ln_Ch12.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess12", true);
        
        editor.commit();

        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  System.out.println("ggggggg"+" "+zipFile);
  }
	 if(zipFile.equalsIgnoreCase("/storage/emulated/0/m.zip")|| zipFile.equalsIgnoreCase("/storage/emulated/0/Android/data/foreverappar.com.foreverappar/files/Documents/m.zip") )
  {
	  //--- 
	  Txt_Sucess13.setVisibility(View.VISIBLE);
	  img_delete13.setVisibility(View.VISIBLE);

		
		Ln_Ch13.setBackground(getResources().getDrawable(R.drawable.ch_bg_dark));
	 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        
        editor.putBoolean("Sucess13", true);
        
        editor.commit();
        Chapter_Details_Value="abc";
		  System.out.println("ggggggg"+" "+zipFile);
		  Intent refresh = new Intent(getApplicationContext(), AboutScreen3.class);
			startActivity(refresh);
			AboutScreen3.this.finish();
		  
			Chapter_detail.setVisibility(View.VISIBLE);
	  
	  System.out.println("ggggggg"+" "+zipFile);
  }
	
	  
  }

  
  private void unzipEntry(ZipFile zipfile, ZipEntry entry,
          String outputDir) throws IOException {

      if (entry.isDirectory()) {
          createDir(new File(outputDir, entry.getName()));
          return;
      }

      File outputFile = new File(outputDir, entry.getName());
      if (!outputFile.getParentFile().exists()) {
          createDir(outputFile.getParentFile());
      }

     // Log.v("", "Extracting: " + entry);
      BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
      BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

      try {

      } finally {
      	outputStream.flush();
      	outputStream.close();
      	inputStream.close();
         
          
      }
  }

  private void createDir(File dir) {
      if (dir.exists()) {
          return;
      }
      if (!dir.mkdirs()) {
          throw new RuntimeException("Can not create dir " + dir);
      }
  }}


@SuppressWarnings("deprecation")
public void showAlertDialog(Context context, String title, String message, Boolean status) {
	AlertDialog alertDialog = new AlertDialog.Builder(context).create();

	// Setting Dialog Title
	alertDialog.setTitle(title);

	// Setting Dialog Message
	alertDialog.setMessage(message);
	
	// Setting alert dialog icon
	alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

	// Setting OK Button
	alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
		}
	});

	// Showing Alert Message
	alertDialog.show();
}


	private void GetAndroidPermission() {


		if(ActivityCompat.checkSelfPermission(AboutScreen3.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(AboutScreen3.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(AboutScreen3.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED){
			if(ActivityCompat.shouldShowRequestPermissionRationale(AboutScreen3.this,permissionsRequired[0])
					|| ActivityCompat.shouldShowRequestPermissionRationale(AboutScreen3.this,permissionsRequired[1])
					|| ActivityCompat.shouldShowRequestPermissionRationale(AboutScreen3.this,permissionsRequired[2])){
				//Show Information about why you need the permission
				AlertDialog.Builder builder = new AlertDialog.Builder(AboutScreen3.this);
				builder.setTitle("Need Multiple Permissions");
				builder.setMessage("This app needs Camera and Storage permissions.");
				builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						ActivityCompat.requestPermissions(AboutScreen3.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.show();
			} else if (permissionStatus.getBoolean(permissionsRequired[0],false)) {
				//Previously Permission Request was cancelled with 'Dont Ask Again',
				// Redirect to Settings after showing Information about why you need the permission
				AlertDialog.Builder builder = new AlertDialog.Builder(AboutScreen3.this);
				builder.setTitle("Need Multiple Permissions");
				builder.setMessage("This app needs Storage and Camera permissions.");
				builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						sentToSettings = true;
						Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
						Uri uri = Uri.fromParts("package", getPackageName(), null);
						intent.setData(uri);
						startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
						Toast.makeText(getBaseContext(), "Go to Permissions to Grant  Storage and Camera", Toast.LENGTH_LONG).show();
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.show();
			}  else {
				//just request the permission
				ActivityCompat.requestPermissions(AboutScreen3.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
			}



			SharedPreferences.Editor editor = permissionStatus.edit();
			editor.putBoolean(permissionsRequired[0],true);
			editor.commit();
		} else {
			//You already have the permission, just go ahead.
			proceedAfterPermission();
		}
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if(requestCode == PERMISSION_CALLBACK_CONSTANT){
			//check if all permissions are granted
			boolean allgranted = false;
			for(int i=0;i<grantResults.length;i++){
				if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
					allgranted = true;
				} else {
					allgranted = false;
					break;
				}
			}

			if(allgranted){
				proceedAfterPermission();
			} else if(ActivityCompat.shouldShowRequestPermissionRationale(AboutScreen3.this,permissionsRequired[0])
					|| ActivityCompat.shouldShowRequestPermissionRationale(AboutScreen3.this,permissionsRequired[1])
					|| ActivityCompat.shouldShowRequestPermissionRationale(AboutScreen3.this,permissionsRequired[2])){

			AlertDialog.Builder builder = new AlertDialog.Builder(AboutScreen3.this);
				builder.setTitle("Need Multiple Permissions");
				builder.setMessage("This app needs Storage and Camera permissions.");
				builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						ActivityCompat.requestPermissions(AboutScreen3.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
					}
				});
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
				builder.show();
			} else {
				// Toast.makeText(getBaseContext(),"Unable to get Permission",Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_PERMISSION_SETTING) {
			if (ActivityCompat.checkSelfPermission(AboutScreen3.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
				//Got Permission
				proceedAfterPermission();
			}
		}
	}

	private void proceedAfterPermission() {
		//Toast.makeText(getBaseContext(), "We got All Permissions", Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (sentToSettings) {
			if (ActivityCompat.checkSelfPermission(AboutScreen3.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
				//Got Permission
				proceedAfterPermission();
			}
		}
	}

	private void NotifyUpdate() {
/*	params.put("package", PACKAGE_NAME);
	params.put("version_name", sVersionName);
	params.put("version_code", Integer.toString(sVersionCode));
	params.put("Device_Id", Device_Id);*/
//	 Toast.makeText(getBaseContext(), PACKAGE_NAME+" "+sVersionName+" "+Integer.toString(sVersionCode)+" "+Device_Id, Toast.LENGTH_SHORT).show();

		// TODO Auto-generated method stub

		RequestQueue queue = Volley.newRequestQueue(this);
		String urlmanual = "https://www.app.rachnasagar.in/com.rsar.services/appNewVersion.php?";
		StringRequest postRequest = new StringRequest(Request.Method.POST, urlmanual,
				new Response.Listener<String>() {
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

								//	 Toast.makeText(getBaseContext(), "FFFFFFFF", Toast.LENGTH_SHORT).show();

								Service_Status=object.get("Update_Status").toString();

								Status_Notification=object.get("status").toString();

								Notify_Msg =object.get("msg").toString();

								System.out.println("dddeeeeee"+"  "+PACKAGE_NAME+" "+sVersionName+" "+Integer.toString(sVersionCode)+" "+Device_Id);
								System.out.println("ffffffffsdsss"+"  "+Service_Status+" ");
								if(Status_Notification.equalsIgnoreCase("true")){
									if(Service_Status.equalsIgnoreCase("true")){

										Str_Notify_Msg=object.get("Update_Message").toString();
										Str_Notify_Msg_Link=object.get("Update_Link").toString();
										System.out.println("kkkkkkkk"+"  "+PACKAGE_NAME+" "+sVersionName+" "+Integer.toString(sVersionCode)+" "+Device_Id);
										System.out.println("ffffffffsdsss"+"  "+Service_Status+" "+Str_Notify_Msg);
										final Dialog dialoga = new Dialog(AboutScreen3.this);
										dialoga.requestWindowFeature(Window.FEATURE_NO_TITLE);
										dialoga.setContentView(R.layout.dialog_msg_link);
										dialoga.setCancelable(false);

										// set the custom dialog components - text, image and button
										TextView text = (TextView) dialoga.findViewById(R.id.dia_error_msg);
										text.setText(Str_Notify_Msg);

										Button dialogButton = (Button) dialoga.findViewById(R.id.dia_b_error_button_link);
										// if button is clicked, close the custom dialog
										dialogButton.setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View v) {

												Intent i = new Intent(Intent.ACTION_VIEW);
												i.setData(Uri.parse(Str_Notify_Msg_Link));
												startActivity(i);

											}
										});

										Button dialogButtonCan = (Button) dialoga.findViewById(R.id.dia_b_error_button);
										// if button is clicked, close the custom dialog
										dialogButtonCan.setOnClickListener(new OnClickListener() {
											@Override
											public void onClick(View v) {

												dialoga.dismiss();

											}
										});

										dialoga.show();
									}
								}
								else
								{
									Toast.makeText(getBaseContext(), Notify_Msg, Toast.LENGTH_SHORT).show();
								}

								//  System.out.println("seeebaaa"+" "+notifyupdate);
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
				params.put("package", PACKAGE_NAME);
				params.put("version_name", sVersionName);
				params.put("version_code", Integer.toString(sVersionCode));
				params.put("Device_Id", Device_Id);

				return params;
			}
		};
		queue.add(postRequest);

    }
}