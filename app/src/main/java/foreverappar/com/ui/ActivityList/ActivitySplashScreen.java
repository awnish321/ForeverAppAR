/*===============================================================================
Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of QUALCOMM Incorporated, registered in the United States 
and other countries. Trademarks of QUALCOMM Incorporated are used with permission.
===============================================================================*/

package foreverappar.com.ui.ActivityList;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import java.io.File;

import foreverappar.com.foreverappar.R;


public class ActivitySplashScreen extends Activity
{
		public static String Av_Sucess1="";public static String Av_Sucess2="";public static String Av_Sucess3="";public static String Av_Sucess4="";
	    public static String Av_Sucess5="";public static String Av_Sucess6="";public static String Av_Sucess7="";public static String Av_Sucess8="";
	    public static String Av_Sucess9="";public static String Av_Sucess10="";public static String Av_Sucess11="";public static String Av_Sucess12="";
	    public static String Av_Sucess13="";
    private static long SPLASH_MILLIS = 750;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
            R.layout.splash_screen, null, false);
        
        addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));
        
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            
            @Override
            public void run()
            {
                
            	SearchingFilesInMemory();
                Intent intent = new Intent(ActivitySplashScreen.this,
                    AboutScreen3.class);
                intent.putExtra("ACTIVITY_TO_LAUNCH",
                    "app.VideoPlayback.VideoPlayback");
                intent.putExtra("ABOUT_TEXT_TITLE", "Video Playback");
                intent.putExtra("ABOUT_TEXT", "VideoPlayback/VP_about.html");
                startActivity(intent);
            }
            
        }, SPLASH_MILLIS);
    }
    private void SearchingFilesInMemory() {
	 	
		  File dir ;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			dir =getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
		}else{
			dir = Environment.getExternalStorageDirectory();
		}
		
		         File file1 = new File(dir, "/.VideoPlayBack/MathsBuzz1/a.mp4" );
		        if (file1.exists()) {
		          //Do action
		        	Av_Sucess1="Exists";
		        }
		        File file2 = new File(dir, "/.VideoPlayBack/MathsBuzz1/b.mp4" );
		        if (file2.exists()) {
		          //Do action
		        	Av_Sucess2="Exists";
		        }
		         File file3 = new File(dir, "/.VideoPlayBack/MathsBuzz1/c.mp4" );
		        if (file3.exists()) {
		          //Do action
		        	Av_Sucess3="Exists";
		        }
		        File file4 = new File(dir, "/.VideoPlayBack/MathsBuzz1/d.mp4" );
		        if (file4.exists()) {
		          //Do action

		        	Av_Sucess4="Exists";
		        }
		         File file5 = new File(dir, "/.VideoPlayBack/MathsBuzz1/e.mp4" );
		        if (file5.exists()) {
		          //Do action
		        	Av_Sucess5="Exists";
		        }
		        File file6 = new File(dir, "/.VideoPlayBack/MathsBuzz1/f.mp4" );
		        if (file6.exists()) {
		          //Do action
		        	
		        
		        	Av_Sucess6="Exists";
		        
		        	
		        }
		         File file7 = new File(dir, "/.VideoPlayBack/MathsBuzz1/g.mp4" );
		        if (file7.exists()) {
		          //Do action
		        	
		        	Av_Sucess7="Exists";
			 			
		        }
		        File file8 = new File(dir, "/.VideoPlayBack/MathsBuzz1/h.mp4" );
		        if (file8.exists()) {
		          //Do action
		        	
		        
		        	Av_Sucess8="Exists";
		        
		        	
		        }
		         File file9 = new File(dir, "/.VideoPlayBack/MathsBuzz1/i.mp4" );
		        if (file9.exists()) {
		          //Do action
		        	
		        	Av_Sucess9="Exists";
		 			
			 			
		        }
		        File file10 = new File(dir, "/.VideoPlayBack/MathsBuzz1/j.mp4" );
		        if (file10.exists()) {
		          //Do action
		        	
		        	Av_Sucess10="Exists";
		        	
		        }
		         File file11 = new File(dir, "/.VideoPlayBack/MathsBuzz1/k.mp4" );
		        if (file11.exists()) {
		          //Do action
		        	Av_Sucess11="Exists";
		        
		        }
		        File file12 = new File(dir, "/.VideoPlayBack/MathsBuzz1/l.mp4" );
		        if (file12.exists()) {
		          //Do action
		        	
		        	Av_Sucess12="Exists";
		        
		        }
		         File file13 = new File(dir, "/.VideoPlayBack/MathsBuzz1/m.mp4" );
		        if (file13.exists()) {
		          //Do action
		        	
		        	Av_Sucess13="Exists";
			 			
		        }
		        
		
	}
}
