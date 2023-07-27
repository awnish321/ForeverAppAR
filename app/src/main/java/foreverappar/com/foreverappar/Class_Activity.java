package foreverappar.com.foreverappar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import foreverappar.com.Common.ConnectionDetector;
import foreverappar.com.Common.GridViewAdapterChap;
import foreverappar.com.Common.GridViewAdapterChap1;
import foreverappar.com.Common.Misc;
import foreverappar.com.Common.Networking;
import foreverappar.com.Common.ProgressHUD;
import foreverappar.com.Common.SetterGetter_Sub_Chap;
import foreverappar.database.DBHandler;

import static foreverappar.database.DBHandler.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;

public class Class_Activity extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabase;
    DBHandler dbHandler;
    Cursor cursor;
    ArrayList<String> Class_ID_Array;
    ArrayList<String> Class_NAME_Array;
    ArrayList<String> GridViewClickItemArray = new ArrayList<String>();

    GridView gv_subcat;
    GridViewAdapterChap1 adapter1;

    ProgressHUD dialog;
    ProgressHUD dialogoo;
    String message = "Please Wait....";
    ProgressDialog progressDialog;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    LinearLayout ln_logo_bg,ln_bg,ln_top_bg;
    ImageView Img_Sch_Logo;
    TextView Txt_School_Name;

    EditText ettext;
    Dialog otpdialog;
    //  File logobg = new  File("/sdcard/.foreverappar/logo_bg.png");
    //File schoollogo = new  File("/sdcard/.foreverappar/school_logo.png");
    File schoollogo;
    File logobg;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String Pref_Bg_Code,Pref_Top_Bg_Code,Pref_Button_Bg,Pref_School_UI,Pref_School_name,
            Pref_Fd_School_name,Pref_Class_List,Pref_Restric_Id,Pref_Sch_Name_Color,Str_Notify_Msg_Link,Pref_Email,Pref_Mob;



    SetterGetter_Sub_Chap vinsgetter;
    ArrayList<SetterGetter_Sub_Chap> vinsquesarrayList;
    String Str_Status,Str_Msg,Details,Class_Name,Class_Id,Str_Otp_Value,Str_Otp;

    JSONArray Class_Data;

    boolean doubleBackToExitPressedOnce;

    String Device_Id,Mob_Id,Mob_Product,Mob_Brand,Mob_Manufacture,Mob_Model;


    PackageInfo pinfo;
    public static String PACKAGE_NAME;

    String sVersionName;

    int sVersionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.class_activity);



        preferences = getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);

        Pref_School_UI = preferences.getString("Rsar_School_UI", "");
        Pref_School_name = preferences.getString("Rsar_School_Name", "");
        Pref_Fd_School_name = preferences.getString("Rsar_Fd_School_Name", "");
        Pref_Bg_Code = preferences.getString("Rsar_Bg_Code", "");
        Pref_Top_Bg_Code = preferences.getString("Rsar_Top_Bg_Code", "");
        Pref_Button_Bg = preferences.getString("Rsar_Button_Bg", "");
        Pref_Class_List = preferences.getString("Rsar_Class_Arraylst", "");
        Pref_Restric_Id = preferences.getString("Rsar_Restric_ID", "");
        Pref_Sch_Name_Color = preferences.getString("Rsar_Sch_Bgcol_Name", "");
        Str_Otp_Value = preferences.getString("Rsar_Otp_Value", "");//Rsar_Otp_Value
        Pref_Email = preferences.getString("Rsar_Pref_Email", "");
        Pref_Mob= preferences.getString("Rsar_Pref_Mobile", "");

        System.out.println("Dooooo"+Str_Otp_Value);

        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());

      //  Pref_Top_Bg_Code ="#138ab2";
      //  Pref_Bg_Code="#053a5a";
       //GetAndroidPermission();

      /*  schoollogo= new  File("/sdcard/.foreverappar/"+Pref_Fd_School_name+"/images/"+"school_logo.png");
        logobg= new  File("/sdcard/.foreverappar/"+Pref_Fd_School_name+"/images/"+"logo_bg.png");*/


        GetDevicedetails();
        Call_Button();

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


        if(Str_Otp_Value.equalsIgnoreCase("True"))
        {
             otpdialog = new Dialog(Class_Activity.this);
             otpdialog .requestWindowFeature(Window.FEATURE_NO_TITLE);
            otpdialog.setContentView(R.layout.otp_dialog);
            otpdialog.setCancelable(false);

            // set the custom dialog components - text, image and button
            LinearLayout ln_outline=(LinearLayout)otpdialog.findViewById(R.id.dia_ln_outline);
            View view=(View) otpdialog.findViewById(R.id.dia_view);
            TextView Error_text = (TextView) otpdialog.findViewById(R.id.dia_error_title);
             ettext = (EditText) otpdialog.findViewById(R.id.dia_error_msg);




            Button btn_yes = (Button) otpdialog.findViewById(R.id.dia_b_yes);


            ln_outline.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
            view.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
            Error_text.setTextColor(Color.parseColor(Pref_Bg_Code));
            btn_yes.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Str_Otp= ettext.getText().toString().trim();
                    if (ettext.getText().toString().trim().isEmpty())
                    {
                        Toast.makeText(Class_Activity.this, "Please Enter OTP .", Toast.LENGTH_LONG).show();
                    }
                    else {

                        OtpVerify();

                        String message = "Please Wait....";
                        dialogoo = new ProgressHUD(Class_Activity.this, R.style.ProgressHUD);
                        dialogoo.setTitle("");
                        dialogoo.setContentView(R.layout.progress_hudd);
                        if (message == null || message.length() == 0) {
                            dialogoo.findViewById(R.id.message).setVisibility(View.GONE);
                        } else {
                            TextView txt = (TextView) dialogoo.findViewById(R.id.message);
                            txt.setText(message);
                        }
                        dialogoo.setCancelable(true);

                        dialogoo.getWindow().getAttributes().gravity = Gravity.CENTER;
                        WindowManager.LayoutParams lp = dialogoo.getWindow().getAttributes();
                        lp.dimAmount = 0.2f;
                        dialogoo.getWindow().setAttributes(lp);

                        dialogoo.show();
                    }
                    //dialogss.dismiss();
                }

            });
            otpdialog.show();
        }
        else
        {
            CallClassUrl();
        }









        //----- Device Details


       /* dbHandler = new DBHandler(this);


        Class_ID_Array = new ArrayList<String>();

        Class_NAME_Array = new ArrayList<String>();

        ShowSQLiteDBdata() ;


      Show_Gridview_Offline();*/
    }

    private void Show_Gridview_Offline() {

/*

        vinsquesarrayList=Pref_Class_List.toString()

        GridViewAdapterChap adapter = new GridViewAdapterChap(Class_Activity.this,Pref_Class_List );

                                    */
/*    GridViewAdapterChap adapter = new GridViewAdapterChap(Class_Activity.this,Class_ID_Array,
                                                Class_NAME_Array );*//*


        gv_subcat.setAdapter(adapter);
*/


    }

    private void GetDevicedetails() {



        Device_Id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Mob_Id = android.os.Build.ID;
        Mob_Product= android.os.Build.PRODUCT;
        Mob_Brand= android.os.Build.BRAND;
        Mob_Manufacture= android.os.Build.MANUFACTURER;
        Mob_Model = android.os.Build.MODEL;
    }
    private void Call_Button()  {


        /*ln_logo_bg = (LinearLayout)findViewById(R.id.Ln_Logo_Bg);
        Img_Sch_Logo=(ImageView)findViewById(R.id.Img_School_logo);
        ln_bg = (LinearLayout)findViewById(R.id.Lnr_Bg);
        ln_top_bg = (LinearLayout)findViewById(R.id.Lnr_Top_bg);
        Txt_School_Name=(TextView)findViewById(R.id.Txt_School_Name);*/
       // Txt_School_Name.setTextColor(Color.parseColor(Pref_Sch_Name_Color));

        gv_subcat=(GridView)findViewById(R.id.Gv_subcat);



       // ln_bg.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
       // ln_top_bg.setBackgroundColor(Color.parseColor(Pref_Top_Bg_Code));
       /* try {
            ln_top_bg.setBackgroundColor(Integer.parseInt(Pref_Top_Bg_Code));
        }catch (NumberFormatException e){
            System.out.println("not a number");
        }*/

//schoollogo
       /* Bitmap bitmap = BitmapFactory.decodeFile(logobg.getAbsolutePath());
        Bitmap bitsclogo = BitmapFactory.decodeFile(schoollogo.getAbsolutePath());

        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(logobg.getAbsoluteFile())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapDrawable background = new BitmapDrawable(bitmap);
       ln_logo_bg.setBackgroundDrawable(background);

        try {
            bitsclogo = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.fromFile(schoollogo.getAbsoluteFile())));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapDrawable Sclogo = new BitmapDrawable(bitsclogo);
        Img_Sch_Logo.setBackground(Sclogo);
       Txt_School_Name.setText(Pref_School_name);*/
    }

    private void CallClassUrl() {

        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {
            SQLiteDataBaseBuild();

            SQLiteTableBuild();

            DeletePreviousData();

            

            ClassUrl();

            NotifyUpdate();

            String message = "Please Wait....";
            dialog = new ProgressHUD(Class_Activity.this, R.style.ProgressHUD);
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

            dbHandler = new DBHandler(this);


            Class_ID_Array = new ArrayList<String>();

            Class_NAME_Array = new ArrayList<String>();

            ShowSQLiteDBdata() ;


            //Show_Gridview_Offline();
            Misc.showAlertDialog(Class_Activity.this, "No Internet Connection",
                    "You don't have internet connection.", false);

        }

    }

    private void OtpVerify() {

        // TODO Auto-generated method stub
        RequestQueue queue = Volley.newRequestQueue(this);

        String urlmanual = Networking.url+"checkOtp.php?";
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
                                System.out.println("messsssss"+"  "+Str_Msg);
                                if(Str_Status.equalsIgnoreCase("True"))
                                {
                                    preferences = getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);
                                    editor= preferences.edit();
                                     editor.putString("Rsar_Otp_Value", "False");
                                     editor.commit();

                                    CallClassUrl();
                                    otpdialog.dismiss();
                                }
                                else {
                                    Toast.makeText(Class_Activity.this, Str_Msg, Toast.LENGTH_LONG).show();
                                }
                                if (dialogoo.isShowing()) {
                                    dialogoo.dismiss();
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
                params.put("otp", Str_Otp);// Second one u can changePref_Restric_Id
                params.put("mobile", Pref_Mob);
                params.put("email", Pref_Email);
                return params;
            }
        };
        queue.add(postRequest);


    }{
    }

    private void ClassUrl() {

        // TODO Auto-generated method stub
        RequestQueue queue = Volley.newRequestQueue(this);

        String urlmanual = Networking.url+"class.php?";
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
                                System.out.println("messsssss"+"  "+Str_Msg);
                                if(Str_Status.equalsIgnoreCase("True"))

                                {

                                    Class_Data=object.getJSONArray("ClassData");


                                    System.out.println("dinchiaaa"+"  "+object.get("ClassData").toString());

                                    for(int j=0; j<Class_Data.length(); j++)
                                    {
                                        JSONObject ObjectData ;
                                        ObjectData= new JSONObject(Class_Data.getJSONObject(j).toString()) ;


                                        String ClassName = ObjectData.getString("Class_Name");

                                        String ClassId = ObjectData.getString("Class_Id");

                                        vinsgetter= new SetterGetter_Sub_Chap();


                                        vinsgetter.setClassName(ObjectData.getString("Class_Name"));
                                        vinsgetter.setClass_Id(ObjectData.getString("Class_Id"));


                                        vinsquesarrayList.add(vinsgetter);

                                     /*   preferences = getApplicationContext().getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);
                                        editor = preferences.edit();
                                        editor.putString("Rsar_Class_Arraylst",  vinsquesarrayList);

                                        editor.commit();
                                        editor.apply();*/





                                        String SQLiteDataBaseQueryHolder = "INSERT INTO "+ TABLE_NAME+"" +
                                                " (Str_Class_Name,Str_Class_Id) VALUES('"+ClassName+"', '"+ClassId+"')";



                                        sqLiteDatabase.execSQL(SQLiteDataBaseQueryHolder);





                                        //dbHandler = new DBHandler(this);



                                        GridViewAdapterChap adapter = new GridViewAdapterChap(Class_Activity.this,vinsquesarrayList );

                                    /*    GridViewAdapterChap adapter = new GridViewAdapterChap(Class_Activity.this,Class_ID_Array,
                                                Class_NAME_Array );*/

                                        gv_subcat.setAdapter(adapter);

                                        gv_subcat.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view,
                                                                    int position, long id) {
                                                // TODO Auto-generated method stub

                                               // Toast.makeText(Class_Activity.this, GridViewClickItemArray.get(position).toString(), Toast.LENGTH_LONG).show();

                                                try {
                                                    Details= Class_Data.get(position).toString();
                                                } catch (JSONException e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }




                                                try {
                                                    JSONObject myObject = new JSONObject(Details);


                                                    Class_Id= myObject.getString("Class_Id");

                                                    Intent intent = new Intent(Class_Activity.this,
                                                            Subject.class);

                                                    intent.putExtra("Rsar_Class_Id", Class_Id);

                                                    startActivity(intent);


                                                } catch (JSONException e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }



                                            }





                                        });


                                    }}
                                else
                                {
                                    final Dialog dialogss = new Dialog(Class_Activity.this);
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
                                sqLiteDatabase.close();

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
                params.put("action", "class");// Second one u can changePref_Restric_Id
                params.put("Restrict_SD", Pref_Restric_Id);
                return params;
            }
        };
        queue.add(postRequest);


    }

    private void ShowSQLiteDBdata() {

        //  dbHandler = new DBHandler(this);

        sqLiteDatabase = dbHandler.getReadableDatabase();


        try {

            cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + "", null);
            Log.e("a", "" + cursor.getCount());

            Class_ID_Array.clear();
            Class_NAME_Array.clear();


            if (cursor.moveToFirst()) {
                do {

                    Class_ID_Array.add(cursor.getString(cursor.getColumnIndex(DBHandler.KEY_RSAR_CLASS_ID)));

                    //Inserting Column Name into Array to Use at GridView Click Listener Method.
                    GridViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(DBHandler.KEY_RSAR_CLASS_ID)));

                    Class_NAME_Array.add(cursor.getString(cursor.getColumnIndex(DBHandler.KEY_RSAR_CLASS_NAME)));


                } while (cursor.moveToNext());
            }

            adapter1 = new GridViewAdapterChap1(Class_Activity.this,

                    Class_ID_Array,
                    Class_NAME_Array
            );

            gv_subcat.setAdapter(adapter1);
            gv_subcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 //   Toast.makeText(Class_Activity.this, GridViewClickItemArray.get(position), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Class_Activity.this, Subject.class);
                    intent.putExtra("Rsar_Db_Class_Id", GridViewClickItemArray.get(position));
                    startActivity(intent);

                }
            });
            cursor.close();
        }catch (Exception e){

            System.out.println("Table deatails"+ "doesn't exist :(((");
            final Dialog dialogss = new Dialog(Class_Activity.this);
            dialogss.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialogss.setContentView(R.layout.alert_dialog);
            dialogss.setCancelable(true);

            // set the custom dialog components - text, image and button
            LinearLayout ln_outline=(LinearLayout)dialogss.findViewById(R.id.dia_ln_outline);
            View view=(View) dialogss.findViewById(R.id.dia_view);
            TextView Error_text = (TextView) dialogss.findViewById(R.id.dia_error_title);
            TextView text = (TextView) dialogss.findViewById(R.id.dia_error_msg);


            text.setText("Please Start your internet to load data.");


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




        }

    public void SQLiteDataBaseBuild(){

        sqLiteDatabase = openOrCreateDatabase(DBHandler.DB_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild(){

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+"("+DBHandler.KEY_RSAR_CLASS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+DBHandler.KEY_RSAR_CLASS_NAME+" Text);");

    }

    public void DeletePreviousData(){

        sqLiteDatabase.execSQL("DELETE FROM "+ TABLE_NAME+"");

    }



    private void NotifyUpdate() {


        System.out.println("Notifyupdate" + " " + "aaaaa");
        // TODO Auto-generated method stub

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlmanual = Networking.url+"smNotifyUpdate.php?";
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

    			        		/* User_Name,User_Email_id,User_Mobile_No,User_Shipping_City,User_Shipping_State,User_Shipping_Country,User_Pin_Code,
    			   	          User_Land_Mark,User_Shipping_Address,;*/


                                int notifyupdate;
                                String Status=object.getString("Status");
                                String Msg=object.getString("Message");

                                if(Status.equalsIgnoreCase("True")) {

                                    notifyupdate = Integer.parseInt((String) object.get("Notify_Status"));


                                    if (notifyupdate == 1) {
                                        NotifyUpdateMsg();
                                    }
                                    if (notifyupdate == 2) {
                                        NotifyUpdateMsglink();
                                    }
                                    if (notifyupdate == 3) {
                                        NotifyUpdateMsgActivity();
                                    }
                                    if (notifyupdate == 4) {
                                        NotifyUpdateMsglink();
                                    }

                                    System.out.println("Notifyupdate" + " " + notifyupdate);

                                }else
                                {
                                    //Toast.makeText(Class_Activity.this, Msg, Toast.LENGTH_SHORT).show();
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
                params.put("notify", "notify");
                params.put("Device_Id", Device_Id);
                params.put("package", PACKAGE_NAME);
                params.put("version_name", sVersionName);
                params.put("version_code", Integer.toString(sVersionCode));
                params.put("School_UI", Pref_School_UI);

                return params;
            }
        };
        queue.add(postRequest);





    }




    private void NotifyUpdateMsg() {


        // TODO Auto-generated method stub

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlmanual = Networking.url+"smNotifyMessage.php?";
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

                                String Notify_Status= object.get("Status").toString();
                                String Msg=object.getString("Message");

                                if(Notify_Status.equalsIgnoreCase("True"))
                                {

                                    String	Str_Notify_Msg=object.get("Notify_Message").toString();


                                    System.out.println("Deegggg"+" "+object.get("Notify_Message").toString());




                                    final Dialog dialoga = new Dialog(Class_Activity.this);
                                    dialoga.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialoga.setContentView(R.layout.dialog);
                                    dialoga.setCancelable(false);

                                    LinearLayout ln_outline= dialoga.findViewById(R.id.dia_ln_outline);
                                    LinearLayout ln_bg_blur= dialoga.findViewById(R.id.dia_ln_bg);
                                    TextView Txt_Title = (TextView) dialoga.findViewById(R.id.dia_error_title);
                                    View v_bg_color = (View)dialoga.findViewById(R.id.dia_view) ;
                                    ln_outline.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                                     Txt_Title.setTextColor(Color.parseColor(Pref_Bg_Code));
                                    v_bg_color.setBackgroundColor(Color.parseColor(Pref_Bg_Code));

                                    TextView text = (TextView) dialoga.findViewById(R.id.dia_error_msg);
                                    Button dialogButton = (Button) dialoga.findViewById(R.id.dia_b_error_button);
                                    dialogButton.setBackgroundColor(Color.parseColor(Pref_Bg_Code));

                                    text.setText(Str_Notify_Msg);


                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {



                                            dialoga.dismiss();

                                        }
                                    });

                                    dialoga.show();


                                }else
                                {
                                    Toast.makeText(Class_Activity.this, Msg, Toast.LENGTH_SHORT).show();
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
                params.put("notifymsg", "notifymsg");
                params.put("Device_Id", Device_Id);
                params.put("package", PACKAGE_NAME);
                params.put("version_name", sVersionName);
                params.put("version_code", Integer.toString(sVersionCode));
                params.put("School_UI", Pref_School_UI);

                return params;
            }
        };
        queue.add(postRequest);





    }


    private void NotifyUpdateMsglink() {








        // TODO Auto-generated method stub

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlmanual = Networking.url+"smNotifyMessage.php?";
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

                                String Notify_Status= object.get("Status").toString();
                                String Msg=object.getString("Message");

                                if(Notify_Status.equalsIgnoreCase("True"))
                                {

                                    String	Str_Notify_Msg=object.get("Notify_Message").toString();

                                    Str_Notify_Msg_Link=object.get("Notify_Link").toString();


                                    System.out.println("Deegggg"+" "+object.get("Notify_Message").toString());




                                    final Dialog dialoga = new Dialog(Class_Activity.this);
                                    dialoga.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialoga.setContentView(R.layout.dialog_msg_link);
                                    dialoga.setCancelable(false);

                                    LinearLayout ln_outline= dialoga.findViewById(R.id.dia_ln_outline);
                                    LinearLayout ln_bg_blur= dialoga.findViewById(R.id.dia_ln_bg);
                                    TextView Txt_Title = (TextView) dialoga.findViewById(R.id.dia_error_title);
                                    View v_bg_color = (View)dialoga.findViewById(R.id.dia_view) ;
                                    ln_outline.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                                    Txt_Title.setTextColor(Color.parseColor(Pref_Bg_Code));
                                    v_bg_color.setBackgroundColor(Color.parseColor(Pref_Bg_Code));





                                    // set the custom dialog components - text, image and button
                                    TextView text = (TextView) dialoga.findViewById(R.id.dia_error_msg);
                                    text.setText(Str_Notify_Msg);



                                    Button dialogButton = (Button) dialoga.findViewById(R.id.dia_b_error_button_link);
                                    dialogButton.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {



                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(Str_Notify_Msg_Link));
                                            startActivity(i);

                                            dialoga.dismiss();


                                        }
                                    });

                                    Button dialogButtonCan = (Button) dialoga.findViewById(R.id.dia_b_error_button);
                                    dialogButtonCan.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                                    // if button is clicked, close the custom dialog
                                    dialogButtonCan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            dialoga.dismiss();

                                        }
                                    });

                                    dialoga.show();


                                }else
                                {
                                    Toast.makeText(Class_Activity.this, Msg, Toast.LENGTH_SHORT).show();
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
                params.put("notifymsg", "notifymsg");
                params.put("Device_Id", Device_Id);
                params.put("package", PACKAGE_NAME);
                params.put("version_name", sVersionName);
                params.put("version_code", Integer.toString(sVersionCode));
                params.put("School_UI", Pref_School_UI);

                return params;
            }
        };
        queue.add(postRequest);





    }


    private void NotifyUpdateMsgActivity() {




        // TODO Auto-generated method stub

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlmanual = Networking.url+"smNotifyMessage.php?";
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

                                String Notify_Status= object.get("Status").toString();
                                String Msg=object.getString("Message");

                                if(Notify_Status.equalsIgnoreCase("True"))
                                {

                                    String	Str_Notify_Msg=object.get("Notify_Message").toString();




                                    System.out.println("Deegggg"+" "+object.get("Notify_Message").toString());




                                    final Dialog dialoga = new Dialog(Class_Activity.this);
                                    dialoga.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialoga.setContentView(R.layout.dialog_msg_link);
                                    dialoga.setCancelable(false);

                                    LinearLayout ln_outline= dialoga.findViewById(R.id.dia_ln_outline);
                                    LinearLayout ln_bg_blur= dialoga.findViewById(R.id.dia_ln_bg);
                                    TextView Txt_Title = (TextView) dialoga.findViewById(R.id.dia_error_title);
                                    View v_bg_color = (View)dialoga.findViewById(R.id.dia_view) ;
                                    ln_outline.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                                    Txt_Title.setTextColor(Color.parseColor(Pref_Bg_Code));
                                    v_bg_color.setBackgroundColor(Color.parseColor(Pref_Bg_Code));



                                    // set the custom dialog components - text, image and button
                                    TextView text = (TextView) dialoga.findViewById(R.id.dia_error_msg);
                                    text.setText(Str_Notify_Msg);



                                    Button dialogButton = (Button) dialoga.findViewById(R.id.dia_b_error_button_link);
                                    dialogButton.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            preferences = getSharedPreferences("FOREVER_APP", 0);
                                            editor = preferences.edit();
                                            editor.clear();
                                            editor.commit();

                                            Intent intent = new Intent(Class_Activity.this,
                                                    MainActivity.class);

                                            startActivity(intent);

                                            finish();



                                        }
                                    });


                                    Button dialogButtonCan = (Button) dialoga.findViewById(R.id.dia_b_error_button);
                                    dialogButtonCan.setBackgroundColor(Color.parseColor(Pref_Bg_Code));
                                    // if button is clicked, close the custom dialog
                                    dialogButtonCan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            dialoga.dismiss();

                                        }
                                    });

                                    dialoga.show();


                                }else
                                {
                                    Toast.makeText(Class_Activity.this, Msg, Toast.LENGTH_SHORT).show();
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
                params.put("notifymsg", "notifymsg");
                params.put("Device_Id", Device_Id);
                params.put("package", PACKAGE_NAME);
                params.put("version_name", sVersionName);
                params.put("version_code", Integer.toString(sVersionCode));
                params.put("School_UI", Pref_School_UI);

                return params;
            }
        };
        queue.add(postRequest);







    }










    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

		    /*   MenuSlider menu= new MenuSlider(this);

		      menu.showMenu();*/



        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    }






