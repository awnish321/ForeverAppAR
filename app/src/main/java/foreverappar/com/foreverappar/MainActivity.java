package foreverappar.com.foreverappar;

import android.Manifest;
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
import android.os.Bundle;
import android.provider.Settings;


import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.HashMap;

import foreverappar.com.Common.ConnectionDetector;
import foreverappar.com.Common.Misc;
import foreverappar.com.Common.Networking;
import foreverappar.com.Common.ProgressHUD;
import foreverappar.com.Common.SetterGetter_Sub_Chap;
import foreverappar.database.DBHandler;

public class  MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission
            .CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission
            .INTERNET,Manifest.permission.ACCESS_NETWORK_STATE };

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    String Device_Id,Mob_Id,Mob_Product,Mob_Brand,Mob_Manufacture,Mob_Model,getemail;
    ProgressHUD dialog;
    String message = "Please Wait....";
    ProgressDialog progressDialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private DBHandler dbHandler;
    LinearLayout Lnr_Reg_Class;
    EditText Et_Activation_Code,Et_Stu_Name,Et_Email,Et_Mob_no,ettext;
    String Str_Stu_Name,Str_Mobile,Str_Email_Id,Str_Msg,Str_UserType,Str_ClassID,Str_School_UI,Str_Status,Str_School_name,Str_Fd_School_name,Str_School_Path,Str_Otp_Value;
    String Str_Bg_Code,Str_Top_Bg_Code,Str_Button_Bg,Str_Act_Status,Pref_School_UI,Str_Restric_ID,Str_Sch_Name_Color,Str_Otp_value,Pre_Email_Id,Pre_Mob_No;
    Button Btn_Submit;
    // flag for Internet connection status
    Boolean isInternetPresent = false;
    // Connection detector class
    ConnectionDetector cd;
    private ProgressDialog mProgressDialog;
    String unzipLocation ;
    String zipFile ;
    public static String Value;
    boolean deleted_zip;
    ArrayList<SetterGetter_Sub_Chap> vinsquesarrayList;
    JSONArray DropClass_Data;
    SetterGetter_Sub_Chap vinsgetter;
    Spinner Spin;
    String Details,DClass_ID,RadioUserDetail;
    private ArrayList<String> DClass_Name;
    private RadioGroup radioUserGroup;
    private RadioButton radioUserButton;
    PackageInfo pinfo;
    public static String PACKAGE_NAME;
    String sVersionName;
    ProgressHUD dialogoo;
    Dialog otpdialog;
    int sVersionCode;
    TextView text11,privacy,about,forget_details;
    String sname,semail,smob,s11,s12,s13;
    Intent getintents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        Et_Stu_Name=(EditText)findViewById(R.id.ET_Name);
        Et_Email=(EditText)findViewById(R.id.ET_Email);
        Et_Mob_no=(EditText)findViewById(R.id.ET_Mobile_No);
        sname=Et_Stu_Name.getText().toString();
        semail=Et_Email.getText().toString();
        smob=Et_Mob_no.getText().toString();

        // FirebaseApp.initializeApp(MainActivity.this);
        DClass_Name= new ArrayList<String>();
        permissionStatus = getSharedPreferences("permissionStatus",MODE_PRIVATE);
        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());
        //-- To take permission ------------------
        GetAndroidPermission();
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
        isInternetPresent = cd.isConnectingToInternet();
        // check for Internet status
        if (isInternetPresent) {
            DropDownService();
            String message = "Please Wait....";
            dialog = new ProgressHUD(MainActivity.this, R.style.ProgressHUD);
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_hudd);
            if (message == null || message.length() == 0) {
                dialog.findViewById(R.id.message).setVisibility(View.GONE);
            } else {
                TextView txt = (TextView) dialog.findViewById(R.id.message);
                txt.setText(message);
            }
            dialog.setCancelable(false);
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.dimAmount = 0.2f;
            dialog.getWindow().setAttributes(lp);
            dialog.show();
        }else {
            // Internet connection is not present
            // Ask user to connect to Internet
            Misc.showAlertDialog(MainActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
        ButtonsDetails();
        getintents=getIntent();
        s11=getintents.getStringExtra("pname");
        s12=getintents.getStringExtra("pemail");
        s13=getintents.getStringExtra("pmob");
        Et_Stu_Name.setText(s11);
        Et_Email.setText(s12);
        Et_Mob_no.setText(s13);
        GetDevicedetails();
        preferences = getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);
        Pref_School_UI = preferences.getString("Rsar_School_UI", "");
        Str_Otp_Value = preferences.getString("Rsar_Otp_Value", "");//Rsar_Otp_Value
        if(Pref_School_UI.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Enter the Details.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(MainActivity.this, Class_Activity.class);
            startActivity(intent);
        }
        privacy=findViewById(R.id.text34);
        about=findViewById(R.id.text35);
        forget_details=findViewById(R.id.forgets);
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, WebViewPrivacy.class);
                startActivity(i);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, WebViewAbout.class);
                startActivity(i);
            }
        });
        forget_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                otpdialog = new Dialog(MainActivity.this);
                otpdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                otpdialog.setContentView(R.layout.forget);
                otpdialog.setCancelable(false);
                LinearLayout ln_outline = (LinearLayout) otpdialog.findViewById(R.id.dia_ln_outline1);
                View view1 = (View) otpdialog.findViewById(R.id.dia_view1);
                TextView Error_text = (TextView) otpdialog.findViewById(R.id.dia_error_title1);
                text11 =  otpdialog.findViewById(R.id.textwrittn1);
                ettext = (EditText) otpdialog.findViewById(R.id.dia_error_msg1);
                getemail=ettext.getText().toString();
                Button btn_yes = (Button) otpdialog.findViewById(R.id.dia_b_yes1);
                Button cencel = (Button) otpdialog.findViewById(R.id.cancels1);
                cencel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        otpdialog.dismiss();
                    }
                });
                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Str_Otp= ettext.getText().toString().trim();
                        if (ettext.getText().toString().trim().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Please Enter EmailID", Toast.LENGTH_LONG).show();
                        } else {
                            getemail=ettext.getText().toString();
                            SendMsg();
                            String message = "Please Wait....";
                            dialogoo = new ProgressHUD(MainActivity.this, R.style.ProgressHUD);
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
        });

    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void SendMsg() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlmanual = Networking.url+"forgotDetails.php?";
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlmanual,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Responseeee", response);

                        //Toast.makeText(MainActivity.this, "Please Check your Email", Toast.LENGTH_LONG).show();
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
                                    text11.setText("Please Check your Email");
                                    Toast.makeText(MainActivity.this, "Please Check your Email", Toast.LENGTH_LONG).show();
                                    //CallSubject();
                                    // otpdialog.dismiss();
                                }
                                else {
                                    text11.setText("Not registered with us");
                                    otpdialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Not registered with us", Toast.LENGTH_LONG).show();
                                    Log.d("ttttt33",Str_Msg);
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
                new Response.ErrorListener() {
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
            protected HashMap<String, String> getParams() {

                HashMap<String, String>  params = new HashMap<String, String>();
                params.put("School_UI", "ODE=");
                params.put("email", getemail);
                params.put("action", "forgot");

                return params;
            }
        };
        queue.add(postRequest);
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

    private void ButtonsDetails() {
        Spin= findViewById(R.id.spinner);
         //Spin.setOnItemSelectedListener(this);
        Lnr_Reg_Class=(LinearLayout)findViewById(R.id.Reg_Class);

        Btn_Submit =(Button)findViewById(R.id.Btn_Submit);

        radioUserGroup = (RadioGroup) findViewById(R.id.radioUser);
        Et_Stu_Name=(EditText)findViewById(R.id.ET_Name);
        Et_Email=(EditText)findViewById(R.id.ET_Email);
        Et_Mob_no=(EditText)findViewById(R.id.ET_Mobile_No);
        sname=Et_Stu_Name.getText().toString();
        semail=Et_Email.getText().toString();
        smob=Et_Mob_no.getText().toString();
        Et_Stu_Name.setText(sname);
        Et_Email.setText(semail);
        Et_Mob_no.setText(smob);

        Btn_Submit.setOnClickListener(this);
        radioUserGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                System.out.println("csssd"+"    "+checkedId);

                switch(checkedId) {
                    case R.id.radioStudent:
                        // do operations specific to this selection
                        Spin.setEnabled(true);
                        Lnr_Reg_Class.setVisibility(View.VISIBLE);
                        System.out.println("ssssdf" + "    " + "Student");
                        break;
                    case R.id.radioTeacher:
                        // do operations specific to this selection
                        Spin.setEnabled(false);
                        Lnr_Reg_Class.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    private void DropDownService() {

        // TODO Auto-generated method stub
        RequestQueue queue = Volley.newRequestQueue(this);

        String urlmanual = Networking.url+"classDropdown.php?";
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlmanual,
                new Response.Listener<String>() {
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
                                if(Str_Status.equalsIgnoreCase("True")) {
                                    DropClass_Data=object.getJSONArray("ClassData");

                                    System.out.println("dinchiaaa"+"  "+object.get("ClassData").toString());
                                    for(int j=0; j<DropClass_Data.length(); j++) {
                                        JSONObject ObjectData ;
                                        ObjectData= new JSONObject(DropClass_Data.getJSONObject(j).toString()) ;
                                        String ClassName = ObjectData.getString("Class_Name");
                                        String ClassId = ObjectData.getString("Class_Id");
                                        vinsgetter= new SetterGetter_Sub_Chap();
                                        DClass_Name.add(ObjectData.getString("Class_Name"));
                                        vinsgetter.setDropclassName(ObjectData.getString("Class_Name"));
                                        vinsgetter.setDropclass_Id(ObjectData.getString("Class_Id"));
                                        vinsquesarrayList.add(vinsgetter);
                                        Spin.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, DClass_Name));

                                        /*ArrayAdapter adapter = new ArrayAdapter (getApplicationContext(), android.R.layout.simple_spinner_item, vinsquesarrayList);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        Spin.setAdapter(adapter);*/


           Spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                   try {
                       Details= DropClass_Data.get(position).toString();
                   } catch (JSONException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
                   }
                   try {
                       JSONObject myObject = new JSONObject(Details);
                       DClass_ID= myObject.getString("Class_Id");
                       // Toast.makeText(MainActivity.this,DClass_ID, Toast.LENGTH_LONG).show();

                   } catch (JSONException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
                   }
               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });

                                    }}
                            }
                            if (dialog.isShowing())
                                dialog.dismiss();

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    },
                new Response.ErrorListener() {
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
            protected HashMap<String, String> getParams() {

                HashMap<String, String>  params = new HashMap<String, String>();
                params.put("action", "classDropdown");// Second one u can changePref_Restric_Id

                return params;
            }
        };
        queue.add(postRequest);
    }
    @Override
    public void onClick(View v) {
        if(v == Btn_Submit) {
            int selectedId = radioUserGroup.getCheckedRadioButtonId();
            radioUserButton= (RadioButton) findViewById(selectedId);
            // Toast.makeText(this,radioUserButton.getText(),Toast.LENGTH_SHORT).show();
            RadioUserDetail=radioUserButton.getText().toString();
            if (Et_Stu_Name.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter the Name.", Toast.LENGTH_SHORT).show();
            }
            if (Et_Email.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter the Email Id.", Toast.LENGTH_SHORT).show();
            }
            if (Et_Mob_no.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Enter the Mobile No.", Toast.LENGTH_SHORT).show();
            }
            else {
                Str_Stu_Name = Et_Stu_Name.getText().toString().trim();
                Str_Mobile = Et_Mob_no.getText().toString().trim();
                Str_Email_Id = Et_Email.getText().toString().trim();
                isInternetPresent = cd.isConnectingToInternet();

                // check for Internet status
                if (isInternetPresent) {


                    ActivationCodeURL();


                    String message = "Please Wait....";
                    dialog = new ProgressHUD(MainActivity.this, R.style.ProgressHUD);
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
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    Misc.showAlertDialog(MainActivity.this, "No Internet Connection",
                            "You don't have internet connection.", false);
                }


            }
        }

    }

    private void ActivationCodeURL() {

        // TODO Auto-generated method stub

        RequestQueue queue = Volley.newRequestQueue(this);
        String urlmanual = Networking.url+"activation.php?";
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
                                Str_Status=object.getString("Status");
                                Str_Msg =object.getString("Message");
                                System.out.println("abbb"+"    "+Str_Status);
                                if(Str_Status.equalsIgnoreCase("True")) {
                                    Str_UserType = object.getString("User_Type");
                                    Str_ClassID = object.getString("Class_Id");
                                    Str_School_UI = object.getString("School_UI");
                                    Str_School_name = object.getString("School_Name");
                                    Str_Fd_School_name = object.getString("School_Folder_Name");
                                    Str_School_Path = object.getString("School_Folder_Path");

                                    Str_Bg_Code = object.getString("Bg_Code");
                                    Str_Top_Bg_Code = object.getString("Top_BgCode");
                                    Str_Button_Bg = object.getString("Button_Bg_Color");
                                    Str_Act_Status = object.getString("Activation_Status");
                                    Str_Restric_ID = object.getString("Restrict_SD");
                                    Str_Sch_Name_Color = object.getString("School_Name_Color");
                                    Str_Otp_value=object.getString("OTP_Popup");
                                    Pre_Email_Id = object.getString("Email");
                                    Pre_Mob_No= object.getString("Mobile");
                                    //System.out.println("fuckoff"+"    "+object.getString("School_UI"));

                                    preferences = getApplicationContext().getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);
                                    editor = preferences.edit();
                                    editor.putString("Rsar_UserType",  Str_UserType);
                                    editor.putString("Rsar_ClassID",  Str_ClassID);
                                    editor.putString("Rsar_School_UI",  Str_School_UI);
                                    editor.putString("Rsar_School_Name",  Str_School_name);
                                    editor.putString("Rsar_Fd_School_Name",  Str_Fd_School_name);
                                    editor.putString("Rsar_Bg_Code",  Str_Bg_Code);
                                    editor.putString("Rsar_Top_Bg_Code",  Str_Top_Bg_Code);
                                    editor.putString("Rsar_Button_Bg",  Str_Button_Bg);
                                    editor.putString("Rsar_Act_Status",  Str_Act_Status);
                                    editor.putString("Rsar_Restric_ID",  Str_Restric_ID);
                                    editor.putString("Rsar_Sch_Bgcol_Name",  Str_Sch_Name_Color);
                                    editor.putString("Rsar_Otp_Value",  Str_Otp_value);
                                    editor.putString("Rsar_Pref_Email",  Pre_Email_Id);
                                    editor.putString("Rsar_Pref_Mobile",  Pre_Mob_No);
                                    // Check in Splash screen the Value True
                                    editor.commit();
                                    editor.apply();

                                    System.out.println("fuckoff"+"    "+Str_UserType);
                                    sname=Et_Stu_Name.getText().toString();
                                    semail=Et_Email.getText().toString();
                                    smob=Et_Mob_no.getText().toString();
                                  if(Str_UserType.equalsIgnoreCase("Teacher"))
                                  {
                                      Intent intent = new Intent(MainActivity.this, Class_Activity.class);
                                      startActivity(intent);
                                      MainActivity.this.finish();
                                  }else
                                  {
                                      Intent intent = new Intent(MainActivity.this, Subject.class);
                                      intent.putExtra("Rsar_Class_Id", Str_ClassID);
                                      intent.putExtra("name", sname);
                                      intent.putExtra("email", semail);
                                      intent.putExtra("mobile", smob);
                                      startActivity(intent);
                                     // MainActivity.this.finish();
                                  }

                                }else
                                {
                                    final Dialog dialoga = new Dialog(MainActivity.this);
                                    dialoga.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    dialoga.setContentView(R.layout.newdialog);
                                    dialoga.setCancelable(true);
                                    // set the custom dialog components - text, image and button
                                    TextView text = (TextView) dialoga.findViewById(R.id.error_msg);
                                    text.setText(Str_Msg);
                                    Button dialogButton = (Button) dialoga.findViewById(R.id.b_error_button);
                                    // if button is clicked, close the custom dialog
                                    dialogButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialoga.dismiss();
                                        }
                                    });

                                    dialoga.show();
                                }
                            }

                            if (dialog.isShowing())
                                dialog.dismiss();
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
            protected HashMap<String, String> getParams() {

                HashMap<String, String>  params = new HashMap<String, String>();
                params.put("classId", DClass_ID);
                params.put("userType", radioUserButton.getText().toString());

                params.put("mobile", Str_Mobile);
                params.put("email", Str_Email_Id);
                params.put("name", Str_Stu_Name);
                params.put("mid", Mob_Id);
                params.put("mproduct", Mob_Product);
                params.put("mbrand", Mob_Brand);
                params.put("mmanufacture", Mob_Manufacture);
                params.put("mmodel", Mob_Model);
                params.put("mdid", Device_Id);
                params.put("package", PACKAGE_NAME);
                params.put("version_name", sVersionName);
                params.put("version_code", Integer.toString(sVersionCode));
                params.put("Restrict_SD", "");
                params.put("action", "rsarfb");
                return params;
            }
        };
        queue.add(postRequest);

    }

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


        if(ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[2]) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[2])){
                //Show Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Camera and Storage permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                ActivityCompat.requestPermissions(MainActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
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
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[1])
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissionsRequired[2])){

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Storage and Camera permissions.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions(MainActivity.this,permissionsRequired,PERMISSION_CALLBACK_CONSTANT);
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
            if (ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
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
            if (ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }
}
