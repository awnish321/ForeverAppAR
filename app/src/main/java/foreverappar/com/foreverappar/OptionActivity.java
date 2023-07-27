package foreverappar.com.foreverappar;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

import foreverappar.com.dynamics.ChapterList;
import foreverappar.com.dynamics.ShowWebView;

public class OptionActivity extends Activity {

    String Pref_Bg_Code,Pref_Top_Bg_Code,Pref_Button_Bg,Pref_School_UI,Pref_School_name,Pref_Restric_Id;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    LinearLayout ln_bg,ln_foreverappar,ln_pp,ln_ep,ln_trm,ln_help;
    String Book_Id,Book_Name,Class_Id,Subject_Id,Practice_Paper,Exam_Paper,TRM,Practice_Paper_Value,Exam_Paper_Value,TRM_Value,Rsar_Value,Op_Diff_Play;

    //-----------Slider--------------------------

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ImageModel> imageModelArrayList;

    private int[] myImageList = new int[]{R.drawable.img1, R.drawable.img2,
            R.drawable.img3,R.drawable.img4
            ,R.drawable.img5};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_option);

        preferences = getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);

        Pref_School_UI = preferences.getString("Rsar_School_UI", "");
        Pref_School_name = preferences.getString("Rsar_School_Name", "");
        Pref_Bg_Code = preferences.getString("Rsar_Bg_Code", "");
        Pref_Top_Bg_Code = preferences.getString("Rsar_Top_Bg_Code", "");
        Pref_Button_Bg = preferences.getString("Rsar_Button_Bg", "");
        Pref_Restric_Id = preferences.getString("Rsar_Restric_ID", "");

        Class_Id = getIntent().getExtras().getString("Rsar_Class_Id");
        Subject_Id= getIntent().getExtras().getString("Rsar_Subject_Id");
        Book_Id = getIntent().getExtras().getString("Rsar_Book_Id");
        Book_Name= getIntent().getExtras().getString("Rsar_Book_Name");
        Op_Diff_Play = getIntent().getExtras().getString("Rsar_Diff_Play");

        Practice_Paper = getIntent().getExtras().getString("Rsar_Practice_Paper");
        Exam_Paper = getIntent().getExtras().getString("Rsar_Exam_Paper");
        TRM = getIntent().getExtras().getString("Rsar_TRM");
        Practice_Paper_Value = getIntent().getExtras().getString("Rsar_Practice_Paper_Value");
        Exam_Paper_Value = getIntent().getExtras().getString("Rsar_Exam_Paper_Value");
        TRM_Value = getIntent().getExtras().getString("Rsar_TRM_Value");
        Rsar_Value= getIntent().getExtras().getString("Rsar_RSAR_Value");

        System.out.println("sssssss"+"  "+Practice_Paper_Value+Exam_Paper_Value+TRM_Value);


        imageModelArrayList = new ArrayList<>();
        imageModelArrayList = populateList();
        ButtonsDetails();




    }
    private ArrayList<ImageModel> populateList(){

        ArrayList<ImageModel> list = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            ImageModel imageModel = new ImageModel();
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;
    }
    private void ButtonsDetails() {


      /*  ln_bg = (LinearLayout)findViewById(R.id.Lnr_Bg);
        ln_bg.setBackgroundColor(Color.parseColor(Pref_Bg_Code));*/
        Button btn_foreverappar=(Button)findViewById(R.id.Btn_foreverappar);
        Button btn_PP=(Button)findViewById(R.id.Btn_PP);
        Button btn_Ep=(Button)findViewById(R.id.Btn_Ep);
        Button btn_TRM=(Button)findViewById(R.id.Btn_TRM);
        Button btn_Help=(Button)findViewById(R.id.Btn_Help);
        ln_foreverappar = (LinearLayout)findViewById(R.id.Lnr_foreverappar);
        ln_pp = (LinearLayout)findViewById(R.id.Lnr_PP);
        ln_ep = (LinearLayout)findViewById(R.id.Lnr_EP);
        ln_trm = (LinearLayout)findViewById(R.id.Lnr_TRM);
        ln_help = (LinearLayout)findViewById(R.id.Lnr_Help);

        System.out.println("Value Check"+" "+Rsar_Value+" "+Practice_Paper_Value+" "+Exam_Paper_Value+ " "+TRM_Value);

        if(Rsar_Value.equalsIgnoreCase("True")){
            ln_foreverappar.setVisibility(View.VISIBLE);
        }else{
            ln_foreverappar.setVisibility(View.GONE);
        }

        if(Practice_Paper_Value.equalsIgnoreCase("True")){
            ln_pp.setVisibility(View.VISIBLE);
        }else{
            ln_pp.setVisibility(View.GONE);
        }

        if(Exam_Paper_Value.equalsIgnoreCase("True")){
            ln_ep.setVisibility(View.VISIBLE);
        }else{
            ln_ep.setVisibility(View.GONE);
        }

        if(TRM_Value.equalsIgnoreCase("True")){
            ln_trm.setVisibility(View.VISIBLE);
        }else{
            ln_trm.setVisibility(View.GONE);
        }



        btn_foreverappar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, ChapterList.class);
                intent.putExtra("Rsar_Book_Id", Book_Id);
                intent.putExtra("Rsar_Subject_Id", Subject_Id);
                intent.putExtra("Rsar_Class_Id", Class_Id);
                intent.putExtra("Rsar_Book_Name", Book_Name);
                intent.putExtra("Rsar_Op_Diff_Play", Op_Diff_Play);

                startActivity(intent);
            }
        });


        btn_Help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialogss = new Dialog(OptionActivity.this);
                dialogss.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogss.setContentView(R.layout.popup_help);
                dialogss.setCancelable(true);

                // set the custom dialog components - text, image and button
                LinearLayout ln_outline=(LinearLayout)dialogss.findViewById(R.id.dia_ln_outline);



                mPager = (ViewPager) dialogss.findViewById(R.id.pager);
                mPager.setAdapter(new SlidingImage_Adapter(OptionActivity.this,imageModelArrayList));

                CirclePageIndicator indicator = (CirclePageIndicator)
                        dialogss.findViewById(R.id.indicator);

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
               /* Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 5000, 5000);*/

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




        btn_PP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent .setClass(getApplicationContext(),  ShowWebView.class);
                intent .putExtra("Linkpass", Practice_Paper);
                startActivity(intent );
            }
        });

        btn_Ep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent .setClass(getApplicationContext(),  ShowWebView.class);
                intent .putExtra("Linkpass", Exam_Paper);
                startActivity(intent );
            }
        });
        btn_TRM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent .setClass(getApplicationContext(),  ShowWebView.class);
                intent .putExtra("Linkpass", TRM);
                startActivity(intent );
            }
        });
    }
}
