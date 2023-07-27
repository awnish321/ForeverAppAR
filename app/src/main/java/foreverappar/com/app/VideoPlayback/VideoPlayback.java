/*===============================================================================
Copyright (c) 2019 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package foreverappar.com.app.VideoPlayback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;
import com.vuforia.CameraDevice;
import com.vuforia.DataSet;
import com.vuforia.DeviceTracker;
import com.vuforia.ObjectTracker;
import com.vuforia.PositionalDeviceTracker;
import com.vuforia.STORAGE_TYPE;
import com.vuforia.State;
import com.vuforia.Trackable;
import com.vuforia.TrackableList;
import com.vuforia.Tracker;
import com.vuforia.TrackerManager;
import com.vuforia.Vuforia;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Vector;

import foreverappar.com.SampleApplication.SampleApplicationControl;
import foreverappar.com.SampleApplication.SampleApplicationException;
import foreverappar.com.SampleApplication.SampleApplicationSession;
import foreverappar.com.SampleApplication.utils.LoadingDialogHandler;
import foreverappar.com.SampleApplication.utils.SampleApplicationGLView;
import foreverappar.com.SampleApplication.utils.Texture;
import foreverappar.com.dynamics.ChapterModel;
import foreverappar.com.foreverappar.ImageModel;
import foreverappar.com.foreverappar.R;
import foreverappar.com.foreverappar.SlidingImage_Adapter;
import foreverappar.com.ui.SampleAppMenu.SampleAppMenu;
import foreverappar.com.ui.SampleAppMenu.SampleAppMenuGroup;
import foreverappar.com.ui.SampleAppMenu.SampleAppMenuInterface;


// The AR activity for the VideoPlayback sample.
public class VideoPlayback extends Activity implements
        SampleApplicationControl, SampleAppMenuInterface
{
    private static final String LOGTAG = "VideoPlayback";

    private SampleApplicationSession vuforiaAppSession;

    private WeakReference<Activity> mActivityRef;

    // Helpers to detect events such as double tapping:
    private GestureDetector mGestureDetector = null;
    private SimpleOnGestureListener gestureListener = null;

    // Movie for the Targets:
    public static  int NUM_TARGETS = 70;


    private DataSet mCurrentDataset;

    public static String Vpb_Diff_Play;
    private VideoPlayerHelper mVideoPlayerHelper[] = null;
    private int mSeekPosition[] = null;
    private boolean mWasPlaying[] = null;
    private String mMovieName[] = null;

    String Class_Name,Sub_Name,Book_Name,DataSet_Name,Pref_School_Fb_Name,Pref_Bg_Code,Different_Play;

    // A boolean to indicate whether we come from full screen:
    private boolean mReturningFromFullScreen = false;

    // Our OpenGL view:
    private SampleApplicationGLView mGlView;

    // Our renderer:
    private VideoPlaybackRenderer mRenderer;

    // The textures we will use for rendering:
    private Vector<Texture> mTextures;

    private DataSet dataSetStonesAndChips = null;

    private RelativeLayout mUILayout;

    private boolean mPlayFullscreenVideo = false;

    private SampleAppMenu mSampleAppMenu;

    private final LoadingDialogHandler loadingDialogHandler = new LoadingDialogHandler(
        this);

    // Alert Dialog used to display SDK errors
    private AlertDialog mErrorDialog;

    boolean mIsDroidDevice = false;
    boolean mIsInitialized = false;
    ArrayList<ChapterModel> myList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private boolean mSwitchDatasetAsap = false;
    private boolean mDeviceTracker = false;
    Button Cam_Butt;

    //-----------Slider--------------------------

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.img4
            ,R.drawable.img5};





    // Called when the activity first starts or the user navigates back
    // to an activity.
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.d(LOGTAG, "onCreate");
        super.onCreate(savedInstanceState);

        vuforiaAppSession = new SampleApplicationSession(this);

        mActivityRef = new WeakReference<Activity>(this);

        startLoadingAnimation();

        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();

        preferences = getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);
        Pref_School_Fb_Name= preferences.getString("Rsar_Fd_School_Name", "");
        Pref_Bg_Code = preferences.getString("Rsar_Bg_Code", "");
        Vpb_Diff_Play = preferences.getString("Pref_Diff_Play", "");

        Cam_Butt=(Button)findViewById(R.id.button) ;
        Cam_Butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VideoPlayback.this, "bbbbb", Toast.LENGTH_SHORT).show();
                Log.d("hihihihi","thisis");
                final Dialog dialogss = new Dialog(VideoPlayback.this);
                dialogss.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogss.setContentView(R.layout.alert_dialog);
                dialogss.setCancelable(true);

                // set the custom dialog components - text, image and button
                LinearLayout ln_outline=(LinearLayout)dialogss.findViewById(R.id.dia_ln_outline);
                View view=(View) dialogss.findViewById(R.id.dia_view);
                TextView Error_text = (TextView) dialogss.findViewById(R.id.dia_error_title);
                TextView text = (TextView) dialogss.findViewById(R.id.dia_error_msg);
                text.setText("Please Start your internet to load data and wait for some time");


                Button btn_yes = (Button) dialogss.findViewById(R.id.dia_b_yes);


                ln_outline.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                view.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                Error_text.setTextColor(Color.parseColor(Pref_Bg_Code));
                btn_yes.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        dialogss.dismiss();
                    }

                });


                dialogss.show();

            }
        });
        vuforiaAppSession
            .initAR(this, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Load any sample specific textures:
        mTextures = new Vector<>();
        loadTextures();


        try {
            myList = (ArrayList<ChapterModel>) getIntent().getSerializableExtra("chapterModels");
            NUM_TARGETS = myList.size();
            Log.e("myList", "llll  " + NUM_TARGETS);

        }catch (Exception e){



            final Dialog dialogss = new Dialog(VideoPlayback.this);
            dialogss.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogss.setContentView(R.layout.alert_dialog);
            dialogss.setCancelable(true);

            // set the custom dialog components - text, image and button
            LinearLayout ln_outline=(LinearLayout)dialogss.findViewById(R.id.dia_ln_outline);
            View view=(View) dialogss.findViewById(R.id.dia_view);
            TextView Error_text = (TextView) dialogss.findViewById(R.id.dia_error_title);
            TextView text = (TextView) dialogss.findViewById(R.id.dia_error_msg);
            text.setText("Please Start your internet to load data and wait for some time");


            Button btn_yes = (Button) dialogss.findViewById(R.id.dia_b_yes);


            ln_outline.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
            view.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
            Error_text.setTextColor(Color.parseColor(Pref_Bg_Code));
            btn_yes.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                    dialogss.dismiss();
                }

            });


            dialogss.show();


        }
        ChapterModel model = null;








        // Create the gesture detector that will handle the single and
        // double taps:
         gestureListener = new SimpleOnGestureListener();
        mGestureDetector = new GestureDetector(getApplicationContext(), gestureListener);

        mVideoPlayerHelper = new VideoPlayerHelper[NUM_TARGETS];
        mSeekPosition = new int[NUM_TARGETS];
        mWasPlaying = new boolean[NUM_TARGETS];
        mMovieName = new String[NUM_TARGETS];

        preferences = getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);
        Pref_School_Fb_Name= preferences.getString("Rsar_Fd_School_Name", "");


        // Create the video player helper that handles the playback of the movie
        // for the targets:
        int i;
        for ( i = 0; i < NUM_TARGETS; i++)
        {
            model=myList.get(i);
            mVideoPlayerHelper[i] = new VideoPlayerHelper();
            mVideoPlayerHelper[i].init();
            mVideoPlayerHelper[i].setActivity(this);

            Class_Name =model.getClass_Name();
            Sub_Name =model.getSubject_Name();
            Book_Name= model.getBook_Name();
            DataSet_Name=model.getDataSet_Name();

            mMovieName[i] = ".foreverappar/"+Pref_School_Fb_Name+"/"+model.getClass_Name()+"/"+model.getSubject_Name()+"/"+model.getBook_Name()
                    +"/"+model.getZip_Name()+"/"+"videos/"+model.getVideo_Name()+".mp4";


            System.out.println("Movieeeename"+"  "+mMovieName[i]);
            startLoadingAnimation();
            // mMovieName[i] = ".VideoPlayback/MathsBuzz1/"+model.getVideo_Name()+".mp4";
        }
       // System.out.println("Movieeeename"+"  "+mMovieName[i]);
      /*  mMovieName[STONES] = "VideoPlayback/SampleVideo.mp4";
        mMovieName[CHIPS] = "VideoPlayback/SampleVideo.mp4";*/

        // Set the double tap listener:
        mGestureDetector.setOnDoubleTapListener(new OnDoubleTapListener()
        {
            public boolean onDoubleTap(MotionEvent e)
            {
               // We do not react to this event
               return false;
            }


            public boolean onDoubleTapEvent(MotionEvent e)
            {
                // We do not react to this event
                return false;
            }


            // Handle the single tap
            public boolean onSingleTapConfirmed(MotionEvent e)
            {
                final Handler autofocusHandler = new Handler();
                // Do not react if the StartupScreen is being displayed
                for (int i = 0; i < NUM_TARGETS; i++)
                {
                    // Verify that the tap happened inside the target
                    if (mRenderer!= null && mRenderer.isTapOnScreenInsideTarget(i, e.getX(),
                        e.getY()))
                    {

                        //------ FUllScreen remove.... just uncomment  the below code
                       /* // Check if it is playable on texture
                        if (mVideoPlayerHelper[i].isPlayableOnTexture())
                        {
                            // We can play only if the movie was paused, ready
                            // or stopped
                            if ((mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.PAUSED)
                                || (mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.READY)
                                || (mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.STOPPED)
                                || (mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.REACHED_END))
                            {
                                // Pause all other media
                                pauseAll(i);

                                // If it has reached the end then rewind
                                if ((mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.REACHED_END))
                                    mSeekPosition[i] = 0;

                                mVideoPlayerHelper[i].play(mPlayFullscreenVideo,
                                    mSeekPosition[i]);
                                mSeekPosition[i] = VideoPlayerHelper.CURRENT_POSITION;
                            } else if (mVideoPlayerHelper[i].getStatus() == MEDIA_STATE.PLAYING)
                            {
                                // If it is playing then we pause it
                                mVideoPlayerHelper[i].pause();
                            }
                        } else*/ if (mVideoPlayerHelper[i].isPlayableFullscreen())
                        {
                            // If it isn't playable on texture
                            // Either because it wasn't requested or because it
                            // isn't supported then request playback fullscreen.
                            mVideoPlayerHelper[i].play(true,
                                VideoPlayerHelper.CURRENT_POSITION);
                        }

                        // Even though multiple videos can be loaded only one
                        // can be playing at any point in time. This break
                        // prevents that, say, overlapping videos trigger
                        // simultaneously playback.
                        break;
                    }
                    else
                    {
                        boolean result = CameraDevice.getInstance().setFocusMode(
                                CameraDevice.FOCUS_MODE.FOCUS_MODE_TRIGGERAUTO);
                        if (!result)
                            Log.e("SingleTapConfirmed", "Unable to trigger focus");

                        // Generates a Handler to trigger continuous auto-focus
                        // after 1 second
                        autofocusHandler.postDelayed(new Runnable()
                        {
                            public void run()
                            {
                                final boolean autofocusResult = CameraDevice.getInstance().setFocusMode(
                                        CameraDevice.FOCUS_MODE.FOCUS_MODE_CONTINUOUSAUTO);

                                if (!autofocusResult)
                                    Log.e("SingleTapConfirmed", "Unable to re-enable continuous auto-focus");
                            }
                        }, 1000L);
                    }
                }

                return true;
            }
        });
    }

    private ArrayList<ImageModel> populateList() {

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < 2; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }

    // We want to load specific textures from the APK, which we will later
    // use for rendering.
    private void loadTextures()
    {


        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));

        mTextures.add(Texture.loadTextureFromApk(
                "VideoPlayback/play.png", getAssets()));
        mTextures.add(Texture.loadTextureFromApk("VideoPlayback/play.png",
            getAssets()));
        mTextures.add(Texture.loadTextureFromApk("VideoPlayback/busy.png",
            getAssets()));
        mTextures.add(Texture.loadTextureFromApk("VideoPlayback/error.png",
            getAssets()));
    }


    // Called when the activity will start interacting with the user.
    protected void onResume()
    {
        Log.d(LOGTAG, "onResume");
        super.onResume();

        showProgressIndicator(true);
       // vuforiaAppSession.onResume();
        if (mIsDroidDevice)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        vuforiaAppSession.onResume();
        // Reload all the movies
        if (mRenderer != null)
        {
            for (int i = 0; i < NUM_TARGETS; i++)
            {
                if (!mReturningFromFullScreen)
                {
                    mRenderer.requestLoad(i, mMovieName[i], mSeekPosition[i],
                        false);
                } else
                {
                    mRenderer.requestLoad(i, mMovieName[i], mSeekPosition[i],
                        mWasPlaying[i]);
                }
            }
        }

        mReturningFromFullScreen = false;
    }


    // Called when returning from the full screen player
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1)
        {

            mActivityRef.get().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            if (resultCode == RESULT_OK)
            {
                // The following values are used to indicate the position in
                // which the video was being played and whether it was being
                // played or not:
                String movieBeingPlayed = data.getStringExtra("movieName");
                mReturningFromFullScreen = true;

                // Find the movie that was being played full screen
                for (int i = 0; i < NUM_TARGETS; i++)
                {
                    if (movieBeingPlayed.compareTo(mMovieName[i]) == 0)
                    {
                        mSeekPosition[i] = data.getIntExtra(
                            "currentSeekPosition", 0);
                        mWasPlaying[i] = false;
                    }
                }
            }
        }
    }


    public void onConfigurationChanged(Configuration config)
    {
        Log.d(LOGTAG, "onConfigurationChanged");
        super.onConfigurationChanged(config);

        vuforiaAppSession.onConfigurationChanged();
    }


    // Called when the system is about to start resuming a previous activity.
    protected void onPause()
    {
        Log.d(LOGTAG, "onPause");
        super.onPause();

        if (mGlView != null)
        {
            mGlView.setVisibility(View.INVISIBLE);
            mGlView.onPause();
        }

        // Store the playback state of the movies and unload them:
        for (int i = 0; i < NUM_TARGETS; i++)
        {
            // If the activity is paused we need to store the position in which
            // this was currently playing:


            if (mVideoPlayerHelper[i].isPlayableOnTexture())
            {
                mSeekPosition[i] = mVideoPlayerHelper[i].getCurrentPosition();
                mWasPlaying[i] = mVideoPlayerHelper[i].getStatus() == VideoPlayerHelper.MEDIA_STATE.PLAYING;
            }
            System.out.println("ggjjshhhh"+ mSeekPosition[i]+"  "+mWasPlaying[i]);
            // We also need to release the resources used by the helper, though
            // we don't need to destroy it:
            if (mVideoPlayerHelper[i] != null)
                mVideoPlayerHelper[i].unload();
        }

        mReturningFromFullScreen = false;

        vuforiaAppSession.pauseAR();
    }


    // The final call you receive before your activity is destroyed.
    protected void onDestroy()
    {
        Log.d(LOGTAG, "onDestroy");
        super.onDestroy();

        for (int i = 0; i < NUM_TARGETS; i++)
        {
            // If the activity is destroyed we need to release all resources:
            if (mVideoPlayerHelper[i] != null)
                mVideoPlayerHelper[i].deinit();
            mVideoPlayerHelper[i] = null;
        }

        try {
            vuforiaAppSession.stopAR();
        } catch (SampleApplicationException e)
        {
            Log.e(LOGTAG, e.getString());
        }
        mGestureDetector.setOnDoubleTapListener(null);
        // Unload texture:
        mTextures.clear();
        mTextures = null;
        System.gc();
    }
    // Pause all movies except one
    // if the value of 'except' is -1 then
    // do a blanket pause

    private void pauseAll(int except) {
        // And pause all the playing videos:
        for (int i = 0; i < NUM_TARGETS; i++) {
            // We can make one exception to the pause all calls:
            if (i != except) {
                // Check if the video is playable on texture
                if (mVideoPlayerHelper[i].isPlayableOnTexture()) {
                    // If it is playing then we pause it
                    mVideoPlayerHelper[i].pause();
                }
            }
        }
    }
    // Do not exit immediately and instead show the startup screen
    public void onBackPressed() {
        pauseAll(-1);
        super.onBackPressed();
    }

    public void startLoadingAnimation() {
        mUILayout = (RelativeLayout) View.inflate(getApplicationContext(), R.layout.camera_overlay, null);
        Cam_Butt=(Button)mUILayout.findViewById(R.id.button) ;
        Cam_Butt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("hihihihiiii","thisistttt");
                final Dialog dialogss = new Dialog(VideoPlayback.this);
                dialogss.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogss.setContentView(R.layout.popup_help);
                dialogss.setCancelable(true);
                // set the custom dialog components - text, image and button
                LinearLayout ln_outline=(LinearLayout)dialogss.findViewById(R.id.dia_ln_outline);
                mPager = (ViewPager) dialogss.findViewById(R.id.pager);
                mPager.setAdapter(new SlidingImage_Adapter(VideoPlayback.this,imageModelArrayList));
                CirclePageIndicator indicator = (CirclePageIndicator) dialogss.findViewById(R.id.indicator);
                indicator.setViewPager(mPager);
                final float density = getResources().getDisplayMetrics().density;
                //Set circle indicator radius
                indicator.setRadius(5 * density);
                NUM_PAGES =imageModelArrayList.size();
                // Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        mPager.setCurrentItem(currentPage++, true);
                    }
                };
              /*  Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 5000, 5000);
*/
                // Pager listener over indicator
                indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        currentPage = position;

                    }

                    @Override
                    public void onPageScrolled(int pos, float arg1, int arg2) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int pos) {

                    }
                });




                ln_outline.setBackgroundColor(Color.parseColor(Pref_Bg_Code));



                dialogss.show();

            }

        });
        mUILayout.setVisibility(View.VISIBLE);
        mUILayout.setBackgroundColor(Color.BLACK);

        // Gets a reference to the loading dialog
        loadingDialogHandler.mLoadingDialogContainer = mUILayout
            .findViewById(R.id.loading_indicator);

        // Shows the loading indicator at start
        loadingDialogHandler.sendEmptyMessage(LoadingDialogHandler.SHOW_LOADING_DIALOG);

        // Adds the inflated layout to the view
        addContentView(mUILayout, new LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT));
    }
    // Initializes AR application components.
    private void initApplicationAR() {
        // Create OpenGL ES view:
        int depthSize = 16;
        int stencilSize = 0;
        boolean translucent = Vuforia.requiresAlpha();

        mGlView = new SampleApplicationGLView(getApplicationContext());
        mGlView.init(translucent, depthSize, stencilSize);

        mRenderer = new VideoPlaybackRenderer(this, vuforiaAppSession, NUM_TARGETS,  myList);
        mRenderer.setTextures(mTextures);


        // The renderer comes has the OpenGL context, thus, loading to texture
        // must happen when the surface has been created. This means that we
        // can't load the movie from this thread (GUI) but instead we must
        // tell the GL thread to load it once the surface has been created.
        for (int i = 0; i < NUM_TARGETS; i++)
        {
            mRenderer.setVideoPlayerHelper(i, mVideoPlayerHelper[i]);
            mRenderer.requestLoad(i, mMovieName[i], 0, false);
        }

        mGlView.setRenderer(mRenderer);
        mGlView.setPreserveEGLContextOnPause(true);

        for (int i = 0; i < NUM_TARGETS; i++)
        {
            float[] temp = { 0f, 0f, 0f };
            mRenderer.targetPositiveDimensions[i].setData(temp);
            mRenderer.videoPlaybackTextureID[i] = -1;
        }
    }


    // We do not handle the touch event here, we just forward it to the
    // gesture detector
    public boolean onTouchEvent(MotionEvent event)
    {
        return ((mSampleAppMenu != null && mSampleAppMenu.processEvent(event))
                || mGestureDetector.onTouchEvent(event));
    }


    @Override
    public boolean doInitTrackers()
    {
        // Indicate if the trackers were initialized correctly
        boolean result = true;

        // Initialize the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        Tracker tracker = trackerManager.initTracker(ObjectTracker
            .getClassType());
        if (tracker == null)
        {
            Log.d(LOGTAG, "Failed to initialize ObjectTracker.");
            result = false;
        }

        // Initialize the Positional Device Tracker
        DeviceTracker deviceTracker = (PositionalDeviceTracker)
                trackerManager.initTracker(PositionalDeviceTracker.getClassType());

        if (deviceTracker != null)
        {
            Log.i(LOGTAG, "Successfully initialized Device Tracker");
        }
        else
        {
            Log.e(LOGTAG, "Failed to initialize Device Tracker");
        }

        return result;
    }


    @Override
    public boolean doLoadTrackersData() {
        // Get the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager
            .getTracker(ObjectTracker.getClassType());
        if (objectTracker == null)
        {
            Log.d(
                LOGTAG,
                "Failed to load tracking data set because the ObjectTracker has not been initialized.");
            return false;
        }

        if (dataSetStonesAndChips == null)
        {
            dataSetStonesAndChips = objectTracker.createDataSet();
        }
        // Create the data sets:
       // dataSetStonesAndChips = objectTracker.createDataSet();
        if (dataSetStonesAndChips == null)
        {
            Log.d(LOGTAG, "Failed to create a new tracking data.");
            return false;
        }
        // Load the data sets:
       /* if (!dataSetStonesAndChips.load("StonesAndChips.xml",
            STORAGE_TYPE.STORAGE_APPRESOURCE))*/
        File dir,file;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
            file = new File(dir, "/.foreverappar/"+Pref_School_Fb_Name+"/"+Class_Name+"/"+Sub_Name+"/"+Book_Name+"/"+"DataSet"+"/"+DataSet_Name+"/"+DataSet_Name+".xml" );

        }else{
            dir = Environment.getExternalStorageDirectory();
            file = new File(dir, "/.foreverappar/"+Pref_School_Fb_Name+"/"+Class_Name+"/"+Sub_Name+"/"+Book_Name+"/"+"DataSet"+"/"+DataSet_Name+"/"+DataSet_Name+".xml" );

        }

        System.out.println("fileDDABANG"+"  "+file);
        if (!dataSetStonesAndChips.load(String.valueOf(file),
                STORAGE_TYPE.STORAGE_ABSOLUTE))

       {
            Log.d(LOGTAG, "Failed to load data set.");
            return false;
        }

        // Activate the data set:
        if (!objectTracker.activateDataSet(dataSetStonesAndChips))
        {
            Log.d(LOGTAG, "Failed to activate data set.");
            return false;
        }

        TrackableList trackableList = dataSetStonesAndChips.getTrackables();
        for (Trackable trackable : trackableList)
        {
            String name = "Current Dataset : " + trackable.getName();
            trackable.setUserData(name);

            System.out.println("DDABANG"+"  "+name);
            Log.d(LOGTAG, "UserData:Set the following user data "
                    + trackable.getUserData());
        }

        Log.d(LOGTAG, "Successfully loaded and activated data set.");
        return true;
    }
    boolean isDeviceTrackingActive()
    {
        return mDeviceTracker;
    }

    @Override
    public boolean doStartTrackers()
    {
        // Indicate if the trackers were started correctly
        boolean result = true;
        Log.d("hihihihirer","thisis");
        TrackerManager trackerManager = TrackerManager.getInstance();
        Tracker objectTracker = trackerManager.getInstance().getTracker(
            ObjectTracker.getClassType());
        if (objectTracker != null && objectTracker.start())
        {
            objectTracker.start();
          //  Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 2);
        } else
            result = false;

        if (isDeviceTrackingActive())
        {
            PositionalDeviceTracker deviceTracker = (PositionalDeviceTracker) trackerManager
                    .getTracker(PositionalDeviceTracker.getClassType());

            if (deviceTracker != null && deviceTracker.start())
            {
                Log.i(LOGTAG, "Successfully started Device Tracker");
            }
            else
            {
                Log.e(LOGTAG, "Failed to start Device Tracker");
            }
        }

        return result;
    }
    @Override
    public boolean doStopTrackers() {
        // Indicate if the trackers were stopped correctly
        boolean result = true;
        TrackerManager trackerManager = TrackerManager.getInstance();
        Tracker objectTracker = trackerManager.getInstance().getTracker(
            ObjectTracker.getClassType());
        if (objectTracker != null)
            objectTracker.stop();
        else
            result = false;
        System.out.println("gigaabyte"+" "+objectTracker.getType());

        // Stop the device tracker
        if(isDeviceTrackingActive()) {

            Tracker deviceTracker = trackerManager.getTracker(PositionalDeviceTracker.getClassType());

            if (deviceTracker != null) {
                deviceTracker.stop();
                Log.i(LOGTAG, "Successfully stopped device tracker");
            } else {
                Log.e(LOGTAG, "Could not stop device tracker");
            }
        }
        return result;
    }

    @Override
    public boolean doUnloadTrackersData()
    {
        // Indicate if the trackers were unloaded correctly
        boolean result = true;

        // Get the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        ObjectTracker objectTracker = (ObjectTracker) trackerManager
            .getTracker(ObjectTracker.getClassType());
        if (objectTracker == null)
        {
            Log.d(
                LOGTAG,
                "Failed to destroy the tracking data set because the ObjectTracker has not been initialized.");
            return false;
        }

        if (dataSetStonesAndChips != null)
        {
            if (objectTracker.getActiveDataSets().at(0) == dataSetStonesAndChips
                && !objectTracker.deactivateDataSet(dataSetStonesAndChips))
            {
                Log.d(
                    LOGTAG,
                    "Failed to destroy the tracking data set StonesAndChips because the data set could not be deactivated.");
                result = false;
            } else if (!objectTracker.destroyDataSet(dataSetStonesAndChips))
            {
                Log.d(LOGTAG,
                    "Failed to destroy the tracking data set StonesAndChips.");
                result = false;
            }
            dataSetStonesAndChips = null;
        }
        return result;
    }

    @Override
    public boolean doDeinitTrackers()
    {
        // Deinit the image tracker:
        TrackerManager trackerManager = TrackerManager.getInstance();
        boolean result = trackerManager.deinitTracker(ObjectTracker.getClassType());
        trackerManager.deinitTracker(PositionalDeviceTracker.getClassType());
        return result;
    }
    @Override
    public void onInitARDone(SampleApplicationException exception) {
        if (exception == null) {
            initApplicationAR();
            mRenderer.setActive(true);
            // Now add the GL surface view. It is important
            // that the OpenGL ES surface view gets added
            // BEFORE the camera is started and video
            // background is configured.
            addContentView(mGlView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
            // Sets the UILayout to be drawn in front of the camera
            mUILayout.bringToFront();
            // Hides the Loading Dialog
            //loadingDialogHandler
            //.sendEmptyMessage(LoadingDialogHandler.HIDE_LOADING_DIALOG);
            // Sets the layout background to transparent
            mUILayout.setBackgroundColor(Color.TRANSPARENT);
            mSampleAppMenu = new SampleAppMenu(this, this, "Video Playback",
                mGlView, mUILayout, null);
            setSampleAppMenuSettings();
            mIsInitialized = true;
            vuforiaAppSession.startAR();
        }
        else
        {
            Log.e(LOGTAG, exception.getString());
            showInitializationErrorMessage(exception.getString());
        }
    }
    @Override
    public void onVuforiaResumed()
    {
        if (mGlView != null)
        {
            mGlView.setVisibility(View.VISIBLE);
            mGlView.onResume();
        }
    }

    @Override
    public void onVuforiaStarted()
    {
        mRenderer.updateRenderingPrimitives();

        // Set camera focus mode
        if(!CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_CONTINUOUSAUTO))
        {
            // If continuous autofocus mode fails, attempt to set to a different mode
            if(!CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_TRIGGERAUTO))
            {
                CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_NORMAL);
            }
        }

        showProgressIndicator(false);
    }


    private void showProgressIndicator(boolean show)
    {
        if (show)
        {
            loadingDialogHandler
                    .sendEmptyMessage(LoadingDialogHandler.SHOW_LOADING_DIALOG);
        }
        else
        {
            loadingDialogHandler
                    .sendEmptyMessage(LoadingDialogHandler.HIDE_LOADING_DIALOG);
        }
    }


    // Shows initialization error messages as System dialogs
    private void showInitializationErrorMessage(String message)
    {
        final String errorMessage = message;
        runOnUiThread(new Runnable()
        {
            public void run()
            {
                if (mErrorDialog != null)
                {
                    mErrorDialog.dismiss();
                }

                // Generates an Alert Dialog to show the error message
                AlertDialog.Builder builder = new AlertDialog.Builder(
                    VideoPlayback.this);
                builder
                    .setMessage(errorMessage)
                    .setTitle(getString(R.string.INIT_ERROR))
                    .setCancelable(false)
                    .setIcon(0)
                    .setPositiveButton("OK",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                finish();
                            }
                        });

                mErrorDialog = builder.create();
                mErrorDialog.show();
            }
        });
    }


    @Override
    public void onVuforiaUpdate(State state)
    {
        if (mSwitchDatasetAsap)
        {
            mSwitchDatasetAsap = false;
            TrackerManager tm = TrackerManager.getInstance();
            ObjectTracker ot = (ObjectTracker) tm.getTracker(ObjectTracker
                    .getClassType());
            if (ot == null || mCurrentDataset == null
                    || ot.getActiveDataSets().at(0) == null)
            {
                Log.d(LOGTAG, "Failed to swap datasets");
                return;
            }

            doUnloadTrackersData();
            doLoadTrackersData();
        }

    }

    final private static int CMD_BACK = -1;
    final private static int CMD_FULLSCREEN_VIDEO = 1;
    private final static int CMD_DEVICE_TRACKING = 2;
    private final static int CMD_AUTOFOCUS = 3;
    private final static int CMD_FLASH = 4;
    private final static int CMD_DATASET_START_INDEX = 5;

    // This method sets the menu's settings
    private void setSampleAppMenuSettings()
    {
        SampleAppMenuGroup group;

        group = mSampleAppMenu.addGroup("", false);
        group.addTextItem(getString(R.string.menu_back), -1);

        group = mSampleAppMenu.addGroup("", true);
        group.addSelectionItem(getString(R.string.menu_playFullscreenVideo),
            CMD_FULLSCREEN_VIDEO, mPlayFullscreenVideo);

        group = mSampleAppMenu.addGroup("", true);
        group.addSelectionItem(getString(R.string.menu_extended_tracking),
                CMD_DEVICE_TRACKING, false);

        mSampleAppMenu.attachMenu();
    }


    @Override
    public boolean menuProcess(int command)
    {

        boolean result = true;

        switch (command)
        {
            case CMD_BACK:
                finish();
                break;

            case CMD_FULLSCREEN_VIDEO:
                mPlayFullscreenVideo = !mPlayFullscreenVideo;

                for(int i = 0; i < mVideoPlayerHelper.length; i++)
                {
                    if (mVideoPlayerHelper[i].getStatus() == VideoPlayerHelper.MEDIA_STATE.PLAYING)
                    {
                        // If it is playing then we pause it
                        mVideoPlayerHelper[i].pause();

                        mVideoPlayerHelper[i].play(true,
                            mSeekPosition[i]);
                    }
                }
                break;

             case CMD_DEVICE_TRACKING:
                result = toggleDeviceTracker();

                break;
            default:
                result = false;
                break;
        }

        return result;
    }

    private boolean toggleDeviceTracker()
    {
        boolean result = true;
        TrackerManager trackerManager = TrackerManager.getInstance();
        PositionalDeviceTracker deviceTracker = (PositionalDeviceTracker)
                trackerManager.getTracker(PositionalDeviceTracker.getClassType());

        if (deviceTracker != null)
        {
            if (!mDeviceTracker)
            {
                if (!deviceTracker.start())
                {
                    Log.e(LOGTAG,"Failed to start device tracker");
                    result = false;
                }
                else
                {
                    Log.d(LOGTAG,"Successfully started device tracker");
                }
            }
            else
            {
                deviceTracker.stop();
                Log.d(LOGTAG, "Successfully stopped device tracker");

                clearSampleAppMessage();
            }
        }
        else
        {
            Log.e(LOGTAG, "Device tracker is null!");
            result = false;
        }

        if (result)
        {
            mDeviceTracker = !mDeviceTracker;
        }
        else
        {
            clearSampleAppMessage();
        }

        return result;
    }

    private void clearSampleAppMessage()
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {

            }
        });
    }


}
