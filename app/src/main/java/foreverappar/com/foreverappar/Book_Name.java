package foreverappar.com.foreverappar;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import foreverappar.com.Common.GridViewAdapterBook;
import foreverappar.com.Common.GridViewAdapterChap1;
import foreverappar.com.Common.Misc;
import foreverappar.com.Common.Networking;
import foreverappar.com.Common.ProgressHUD;
import foreverappar.com.Common.SetterGetter_Sub_Chap;
import foreverappar.com.dynamics.ChapterList;
import foreverappar.database.DBHandlerBName;

public class Book_Name extends Activity {


    SQLiteDatabase sqLiteDatabaseBook;
    DBHandlerBName dbHandlerbook;
    Cursor cursor;
    ArrayList<String> Book_ID_Array;
    ArrayList<String> Book_NAME_Array;
    ArrayList<String> GridViewClickItemArray = new ArrayList<String>();

    GridView gv_bookcat;
    GridViewAdapterChap1 adapter1;



    ProgressHUD dialog;
    String message = "Please Wait....";
    ProgressDialog progressDialog;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;
    boolean doubleBackToExitPressedOnce;

    LinearLayout ln_bg;

    SetterGetter_Sub_Chap vinsgetter;
    ArrayList<SetterGetter_Sub_Chap> vinsquesarrayList;

    String Pref_Bg_Code,Pref_Top_Bg_Code,Pref_Button_Bg,Pref_School_UI,Pref_School_name,Pref_Restric_Id;

    String Str_Status,Str_Msg,Details,Book_Id,Book_Name,Class_Id,Subject_Id,Db_Subject_Id,Str_Restrict_SD,Practice_Paper,Exam_Paper,Trm
            ,Practice_Paper_Value,Exam_Paper_Value,Trm_Value,Rsar_Value,Diff_Play;
    //GridView gv_subcat;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    JSONArray Book_Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_book_name);




        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());


        preferences = getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);

        Pref_School_UI = preferences.getString("Rsar_School_UI", "");
        Pref_School_name = preferences.getString("Rsar_School_Name", "");
        Pref_Bg_Code = preferences.getString("Rsar_Bg_Code", "");
        Pref_Top_Bg_Code = preferences.getString("Rsar_Top_Bg_Code", "");
        Pref_Button_Bg = preferences.getString("Rsar_Button_Bg", "");
        Pref_Restric_Id = preferences.getString("Rsar_Restric_ID", "");

      // Class_Id = getIntent().getExtras().getString("Rsar_Class_Id");
      // Subject_Id= getIntent().getExtras().getString("Rsar_Subject_Id");


        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {

            Db_Subject_Id= getIntent().getExtras().getString("Rsar_Subject_Id");
            DBHandlerBName.TABLE_NAME="sub_"+Db_Subject_Id;
            System.out.println("Database baoa"+"  "+Db_Subject_Id);
            Call_Button();
            CallBookDetails();

        }
        else{
            Db_Subject_Id = getIntent().getExtras().getString("Rsar_Db_Sub_Id");
            DBHandlerBName.TABLE_NAME="sub_"+Db_Subject_Id;
            System.out.println("Database bafa"+"  "+Db_Subject_Id);
            Call_Button();
            CallBookDetails();
        }




        //DBHandlerBName.TABLE_NAME="class_1_table";

       // Call_Button();
       // CallBookDetails();

        TextView Sub_Title= (TextView)findViewById(R.id.Title_Sub_Chap) ;
        Sub_Title.setText("BOOKS NAME");

        // gv_subcat=(GridView)findViewById(R.id.Gv_subcat);

    }

    private void Call_Button()  {



       // ln_bg = (LinearLayout)findViewById(R.id.Lnr_Bg);


        gv_bookcat=(GridView)findViewById(R.id.Gv_bookcat);

       // ln_bg.setBackgroundColor(Color.parseColor(Pref_Bg_Code));

    }

    private void CallBookDetails() {

        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        // check for Internet status
        if (isInternetPresent) {


            SQLiteDataBaseSubBuild();

            SQLiteTableBuild();

            DeletePreviousData();
            Class_Id = getIntent().getExtras().getString("Rsar_Class_Id");
            Subject_Id= getIntent().getExtras().getString("Rsar_Subject_Id");

            GetBook_NameURl();

            String message = "Please Wait....";
            dialog = new ProgressHUD(Book_Name.this, R.style.ProgressHUD);
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
            dbHandlerbook = new DBHandlerBName(this);


            Book_ID_Array = new ArrayList<String>();

            Book_NAME_Array = new ArrayList<String>();

            ShowSQLiteDBdata() ;


            Misc.showAlertDialog(Book_Name.this, "No Internet Connection",
                    "You don't have internet connection.", false);

        }

    }

    private void GetBook_NameURl() {



            // TODO Auto-generated method stub
            RequestQueue queue = Volley.newRequestQueue(this);

            String urlmanual = Networking.url+"book.php?";
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

                                    if(Str_Status.equalsIgnoreCase("True"))

                                    {

                                        Book_Data=object.getJSONArray("BooksData");


                                        System.out.println("Book_Data"+"  "+Book_Data+"  "+object.get("BooksData").toString());

                                        for(int j=0; j<Book_Data.length(); j++) {
                                            JSONObject ObjectData;
                                            ObjectData = new JSONObject(Book_Data.getJSONObject(j).toString());


                                             String BookName = ObjectData.getString("Book_Name");

                                             String BookId = ObjectData.getString("Book_Id");





                                            vinsgetter = new SetterGetter_Sub_Chap();

                                            vinsgetter.setBook_Id(ObjectData.getString("Book_Id"));
                                            vinsgetter.setBookName(ObjectData.getString("Book_Name"));

                                            vinsquesarrayList.add(vinsgetter);


                                            GridViewAdapterBook adapter = new GridViewAdapterBook(Book_Name.this, vinsquesarrayList);

                                            gv_bookcat.setAdapter(adapter);


                                            String SQLiteDataBaseSubQueryHolder = "INSERT INTO "+ DBHandlerBName.TABLE_NAME+"" +
                                                    " (Str_Book_Name,Str_Book_Id) VALUES('"+BookName+"', '"+BookId+"')";



                                            sqLiteDatabaseBook.execSQL(SQLiteDataBaseSubQueryHolder);





                                            gv_bookcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view,
                                                                        int position, long id) {
                                                    // TODO Auto-generated method stub


                                                    try {
                                                        Details = Book_Data.get(position).toString();
                                                    } catch (JSONException e) {
                                                        // TODO Auto-generated catch block
                                                        e.printStackTrace();
                                                    }


                                                    try {
                                                        JSONObject myObject = new JSONObject(Details);

                                                        Book_Id = myObject.getString("Book_Id");
                                                        Book_Name = myObject.getString("Book_Name");

                                                        Diff_Play = myObject.getString("Different_Play");

                                                        Practice_Paper = myObject.getString("Practice_Paper");

                                                        Exam_Paper = myObject.getString("Examination_Paper");

                                                        Trm = myObject.getString("Teacher_Resource_Manual");

                                                        Practice_Paper_Value = myObject.getString("Practice_Paper_Value");

                                                        Exam_Paper_Value = myObject.getString("Examination_Paper_Value");

                                                        Trm_Value = myObject.getString("Teacher_Resource_Manual_Value");

                                                        Rsar_Value = myObject.getString("RSAR_Value");

                                                        preferences = getApplicationContext().getSharedPreferences("FOREVER_APP", Context.MODE_PRIVATE);
                                                        editor = preferences.edit();
                                                        editor.putString("Pref_Diff_Play",  Diff_Play);
                                                        editor.commit();
                                                        editor.apply();
                                                        // Intent intent = new Intent(Subject.this, Chapter_Activity.class);
                                                        Intent intent = new Intent(Book_Name.this, OptionActivity.class);
                                                        intent.putExtra("Rsar_Book_Id", Book_Id);
                                                        intent.putExtra("Rsar_Subject_Id", Subject_Id);
                                                        intent.putExtra("Rsar_Class_Id", Class_Id);
                                                        intent.putExtra("Rsar_Book_Name", Book_Name);
                                                        intent.putExtra("Rsar_Practice_Paper", Practice_Paper);
                                                        intent.putExtra("Rsar_Exam_Paper", Exam_Paper);
                                                        intent.putExtra("Rsar_TRM", Trm);
                                                        intent.putExtra("Rsar_Diff_Play", Diff_Play);
                                                        intent.putExtra("Rsar_Practice_Paper_Value", Practice_Paper_Value);
                                                        intent.putExtra("Rsar_Exam_Paper_Value", Exam_Paper_Value);
                                                        intent.putExtra("Rsar_TRM_Value", Trm_Value);
                                                        intent.putExtra("Rsar_RSAR_Value", Rsar_Value);
                                                   /* Intent intent = new Intent(Subject.this, AboutScreen3.class);
                                                    intent.putExtra("Subject_Id", Sub_Id);*///"Different_Play": "True"


                                                        startActivity(intent);

                    System.out.println("ONCLIACK" + "  " + Book_Id + "  " + Book_Name+" "+" "+Practice_Paper_Value+" "+Exam_Paper_Value+" "+Trm_Value+" "+Rsar_Value);
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
                                        final Dialog dialogss = new Dialog(Book_Name.this);
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
                    params.put("sId", Subject_Id);// Second one u can change
                    params.put("action", "book");
                    params.put("Restrict_SD", Pref_Restric_Id);


                    return params;
                }
            };
            queue.add(postRequest);


    }

    private void ShowSQLiteDBdata() {

        //  dbHandler = new DBHandler(this);

        sqLiteDatabaseBook = dbHandlerbook.getWritableDatabase();


        try {

            cursor = sqLiteDatabaseBook.rawQuery("SELECT * FROM " + DBHandlerBName.TABLE_NAME + "", null);
            Log.e("C", "" + cursor.getCount());

            Book_ID_Array.clear();
            Book_NAME_Array.clear();


            if (cursor.moveToFirst()) {
                do {

                    Book_ID_Array.add(cursor.getString(cursor.getColumnIndex(DBHandlerBName.KEY_RSAR_BOOK_ID)));

                    //Inserting Column Name into Array to Use at GridView Click Listener Method.
                    GridViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(DBHandlerBName.KEY_RSAR_BOOK_NAME)));

                    Book_NAME_Array.add(cursor.getString(cursor.getColumnIndex(DBHandlerBName.KEY_RSAR_BOOK_NAME)));


                } while (cursor.moveToNext());
            }

            adapter1 = new GridViewAdapterChap1(Book_Name.this,

                    Book_ID_Array,
                    Book_NAME_Array
            );

            gv_bookcat.setAdapter(adapter1);
            gv_bookcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                  //  Toast.makeText(Book_Name.this, GridViewClickItemArray.get(position), Toast.LENGTH_LONG).show();


                    System.out.println("DB_BOOK_NAme" + " " + GridViewClickItemArray.get(position));
                    Intent intent = new Intent(Book_Name.this, ChapterList.class);
                    intent.putExtra("Rsar_DB_Book_Name", GridViewClickItemArray.get(position));
                    startActivity(intent);
                }
            });
            cursor.close();
        }catch (Exception e){

            System.out.println("Table deatails"+ "doesn't exist :(((");
            final Dialog dialogss = new Dialog(Book_Name.this);
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
    public void SQLiteDataBaseSubBuild(){

        sqLiteDatabaseBook = openOrCreateDatabase(DBHandlerBName.DB_NAME, Context.MODE_PRIVATE, null);

    }

    public void SQLiteTableBuild(){

        sqLiteDatabaseBook.execSQL("CREATE TABLE IF NOT EXISTS "+ DBHandlerBName.TABLE_NAME+"("+DBHandlerBName.KEY_RSAR_BOOK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+DBHandlerBName.KEY_RSAR_BOOK_NAME+" Text);");

    }

    public void DeletePreviousData(){

        sqLiteDatabaseBook.execSQL("DELETE FROM "+ DBHandlerBName.TABLE_NAME+"");

    }


}
