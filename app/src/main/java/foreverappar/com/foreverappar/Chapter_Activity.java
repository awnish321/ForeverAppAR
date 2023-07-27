package foreverappar.com.foreverappar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import foreverappar.com.Common.ConnectionDetector;
import foreverappar.com.Common.ListViewAdapterSub;
import foreverappar.com.Common.Misc;
import foreverappar.com.Common.Networking;
import foreverappar.com.Common.ProgressHUD;
import foreverappar.com.Common.SetterGetter_Sub_Chap;

public class Chapter_Activity extends AppCompatActivity {
    ProgressHUD dialog;
    String message = "Please Wait....";
    ProgressDialog progressDialog;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;
    boolean doubleBackToExitPressedOnce;
    String Pref_Bg_Code,Pref_Top_Bg_Code,Pref_Button_Bg,Pref_School_UI,Pref_School_name;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    JSONArray Chap_Data;

    LinearLayout ln_bg;
    ListView lv_subcat;

    String Str_Status,Str_Msg,Details,Str_Chap_Id,Str_Chap_Name,Class_Id,Sub_Id,Str_Restrict_SD;;

    SetterGetter_Sub_Chap vinsgetter;
    ArrayList<SetterGetter_Sub_Chap> vinsquesarrayList;
    Button Scan_Btn;
    private static String mClassToLaunch;
    private static String mClassToLaunchPackage;
    public static String ACTIVITY_TO_LAUNCH="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_chapter);



        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());


        preferences = getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);

        Pref_School_UI = preferences.getString("Rsar_School_UI", "");
        Pref_School_name = preferences.getString("Rsar_School_Name", "");
        Pref_Bg_Code = preferences.getString("Rsar_Bg_Code", "");
        Pref_Top_Bg_Code = preferences.getString("Rsar_Top_Bg_Code", "");
        Pref_Button_Bg = preferences.getString("Rsar_Button_Bg", "");


        TextView Sub_Title= (TextView)findViewById(R.id.about_text_title) ;
        Sub_Title.setText("CHAPTERS");

        try{
            Bundle extras = getIntent().getExtras();
            String webText = extras.getString("ABOUT_TEXT");
          //  ACTIVITY_TO_LAUNCH = extras.getString("ACTIVITY_TO_LAUNCH");
            mClassToLaunchPackage = getPackageName();
          /*  mClassToLaunch = mClassToLaunchPackage + "."
                    + "app.VideoPlayback.VideoPlayback";*///foreverappar.com.app.VideoPlayback.VideoPlayback

                    mClassToLaunch = "foreverappar.com.app.VideoPlayback.VideoPlayback";
            System.out.println("asasasa"+"   "+mClassToLaunch+"   "+mClassToLaunchPackage);
        }catch(Exception e)
        {
            System.out.println("jaggggg"+"   "+e);
        }

        Call_Button();
        GetChapterDetails();
    }


    private void Call_Button()  {

        Scan_Btn=(Button)findViewById(R.id.button_start);
        ln_bg = (LinearLayout)findViewById(R.id.Lnr_Bg);
        lv_subcat=(ListView) findViewById(R.id.Lv_View);
        lv_subcat.setClickable(true);
       // ln_bg.setBackgroundColor(Color.parseColor(Pref_Bg_Code));

        Scan_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startARActivity();
            }
        });

    }


    // Starts the chosen activity
    private void startARActivity()
    {

        try{
            Intent i = new Intent();
            i.setClassName(mClassToLaunchPackage, mClassToLaunch);
           /* i.putExtra("Vdo_School_name", Book_Id);
            i.putExtra("Vdo_Class_name", Book_Id);
            i.putExtra("Vdo_Subject_name", Book_Id);
            i.putExtra("Vdo_Book_name", Book_Id);*/
            Chapter_Activity.this.startActivity(i);
            System.out.println("asasasa"+"   "+mClassToLaunch+"   "+mClassToLaunchPackage);
        }
        catch(Exception e){
            System.out.println("gfgfgf"+"    "+e);
        }
    }



    private void GetChapterDetails() {

        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {


           /* SQLiteDataBaseSubBuild();

            SQLiteTableBuild();

            DeletePreviousData();*/
            Class_Id = getIntent().getExtras().getString("Rsar_Class_Id");
            Sub_Id = getIntent().getExtras().getString("Rsar_Subject_Id");

            GetChapterURl();

            String message = "Please Wait....";
            dialog = new ProgressHUD(Chapter_Activity.this, R.style.ProgressHUD);
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_hudd);
            if (message == null || message.length() == 0) {
                dialog.findViewById(R.id.message).setVisibility(View.GONE);
            } else {
                TextView txt = (TextView) dialog.findViewById(R.id.message);
                txt.setText(message);
            }
            dialog.setCancelable(true);

            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);

            dialog.show();



        } else {
           /* dbHandlersub = new DBHandlerSub(this);


            Sub_ID_Array = new ArrayList<String>();

            Sub_NAME_Array = new ArrayList<String>();

            ShowSQLiteDBdata() ;*/


            Misc.showAlertDialog(Chapter_Activity.this, "No Internet Connection",
                    "You don't have internet connection.", false);

        }
    }

    private void GetChapterURl() {


        // TODO Auto-generated method stub
        RequestQueue queue = Volley.newRequestQueue(this);

        String urlmanual = Networking.url+"chapter.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlmanual,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);


                        try {
                            vinsquesarrayList = new ArrayList<SetterGetter_Sub_Chap>();
                            JSONArray array;	array = new JSONArray(response);
                            JSONObject object = new JSONObject();
                            for (int i = 0; i < array.length(); i++) {
                                object = array.getJSONObject(i);


                                Str_Status= object.get("Status").toString();

                                Str_Msg = object.get("Message").toString();

                                Str_Restrict_SD = object.get("Restrict_SD").toString();

                              //  vinsgetter.setChapSubName(object.get("Subject_Name").toString());

                                if(Str_Status.equalsIgnoreCase("True"))

                                {
                                  /*  vinsgetter.setClassName(object.getString("Class_Name"));
                                    vinsgetter.setSubjectName(object.getString("Subject_Name"));
                                    vinsgetter.setBookName(object.getString("Book_Name"));*/

                                    Chap_Data=object.getJSONArray("ChapterData");


                                    System.out.println("fucking data"+"  "+Chap_Data+"  "+object.get("ChapterData").toString());

                                    for(int j=0; j<Chap_Data.length(); j++) {
                                        JSONObject ObjectData;
                                        ObjectData = new JSONObject(Chap_Data.getJSONObject(j).toString());


                                        String ChapName = ObjectData.getString("Chapter_Name");
                                        String ChapId = ObjectData.getString("Chapter_Id");
                                        String Chap_Dwnld_Lnk= ObjectData.getString("Download_Link");


                                        vinsgetter = new SetterGetter_Sub_Chap();



                                        vinsgetter.setChap_Id(ObjectData.getString("Chapter_Id"));
                                        vinsgetter.setChapName(ObjectData.getString("Chapter_Name"));
                                        vinsgetter.setDownload_Link(ObjectData.getString("Download_Link"));
                                        vinsgetter.setZip_Name(ObjectData.getString("Zip_Name"));

                                        vinsquesarrayList.add(vinsgetter);


                                      /*  String SQLiteDataBaseSubQueryHolder = "INSERT INTO "+ DBHandlerSub.TABLE_NAME+"" +
                                                " (Str_Subject_Name,Str_Subject_Id) VALUES('"+SubName+"', '"+SubId+"')";



                                        sqLiteDatabaseSub.execSQL(SQLiteDataBaseSubQueryHolder);*/



                                        ListViewAdapterSub adapter = new ListViewAdapterSub(Chapter_Activity.this, vinsquesarrayList);

                                        lv_subcat.setAdapter(adapter);


                                        lv_subcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                System.out.println("ONCLIACKChap" + "  " + Str_Chap_Id + "  " + Str_Chap_Name);

                                            }
                                        });

                                        lv_subcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view,
                                                                    int position, long id) {
                                                // TODO Auto-generated method stub
                                                System.out.println("ONCLIACKChap" + "  " + Str_Chap_Id + "  " + Str_Chap_Name);

                                                try {
                                                    Details = Chap_Data.get(position).toString();
                                                } catch (JSONException e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }


                                                try {
                                                    JSONObject myObject = new JSONObject(Details);

                                                    Str_Chap_Id = myObject.getString("Chapter_Id");
                                                    Str_Chap_Name = myObject.getString("Chapter_Name");


                                                    /*Intent intent = new Intent(Subject.this, Chapter_Activity.class);
                                                    intent.putExtra("Subject_Id", Sub_Id);*/

                                                   /* Intent intent = new Intent(Subject.this, AboutScreen3.class);
                                                    intent.putExtra("Subject_Id", Sub_Id);


                                                    startActivity(intent);*/

                                                    System.out.println("ONCLIACKChap" + "  " + Str_Chap_Id + "  " + Str_Chap_Name);
                                                } catch (JSONException e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }


                                            }


                                        });
                                    }


                                }
                                else
                                {
                                    final Dialog dialogss = new Dialog(Chapter_Activity.this);
                                    dialogss.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialogss.setContentView(R.layout.alert_dialog);
                                    dialogss.setCancelable(true);

                                    // set the custom dialog components - text, image and button
                                    LinearLayout ln_outline=(LinearLayout)dialogss.findViewById(R.id.dia_ln_outline);
                                    View view=(View) dialogss.findViewById(R.id.dia_view);
                                    TextView Error_text = (TextView) dialogss.findViewById(R.id.dia_error_title);
                                    TextView text = (TextView) dialogss.findViewById(R.id.dia_error_msg);
                                    text.setText(Str_Msg);


                                    Button btn_yes = (Button) dialogss.findViewById(R.id.dia_b_yes);


                                    ln_outline.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                                    view.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                                    Error_text.setTextColor(Color.parseColor(Pref_Bg_Code));
                                    btn_yes.setBackgroundColor(Color.parseColor(Pref_Bg_Code));

                                    btn_yes.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {



                                            dialogss.dismiss();
                                        }


                                    });
                                    dialogss.show();
                                }

                                if (dialog.isShowing())
                                    dialog.dismiss();
                               // sqLiteDatabaseSub.close();
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

                        try {
                            Log.d("Error.Response", error.getMessage());

                        } catch (Exception e) {
                            // TODO: handle exception
                        }


                    }
                }
        ) {
            @Override
            protected HashMap<String, String> getParams()
            {

                HashMap<String, String>  params = new HashMap<String, String>();
                params.put("School_UI", Pref_School_UI);
                params.put("cId", Class_Id);
                params.put("sId", Sub_Id);// Second one u can change
                params.put("action", "chapter");



                return params;
            }
        };
        queue.add(postRequest);


    }



}
