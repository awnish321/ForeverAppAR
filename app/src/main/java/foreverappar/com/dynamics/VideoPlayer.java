package foreverappar.com.dynamics;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import foreverappar.com.foreverappar.R;

import static android.content.ContentValues.TAG;

public class VideoPlayer extends Activity {

    ProgressBar progressBar = null;
    VideoView video_player_view;
String VideoUrl;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_video_player);


        VideoUrl = getIntent().getExtras().getString("VideoUrl");

        System.out.println("viidddd"+" "+VideoUrl);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setDimAmount(0.0f);
        
         video_player_view = (VideoView) findViewById(R.id.video_player_view);
        video_player_view .setMediaController(new MediaController(this));
        video_player_view .setVideoPath(VideoUrl);
        video_player_view .start();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        progressBar.setVisibility(View.VISIBLE);
        video_player_view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {

                mp.start();

                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {

                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1, int arg2) {
                        // TODO Auto-generated method stub
                        Log.e(TAG, "Changed");
                        progressBar.setVisibility(View.GONE);
                        mp.start();
                    }
                });


            }
        });

       video_player_view.setOnCompletionListener(
               new MediaPlayer.OnCompletionListener() {

                   @Override
                   public void onCompletion(MediaPlayer mp) {
                       // not playVideo
                       // playVideo();

                      // mp.start();

                       finish();
                   }
               }
       );



    }


}