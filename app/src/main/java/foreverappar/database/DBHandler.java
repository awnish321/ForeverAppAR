package foreverappar.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import foreverappar.com.foreverappar.Class_Activity;

public class DBHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "rsarschool.db";
    public static final String TABLE_NAME = "rsar_school_table";

    public static final String KEY_RSAR_SCHOOL_UI ="Str_School_UI";
    public static final String KEY_RSAR_SCHOOL_NAME ="Str_School_name";
    public static final String KEY_RSAR_BG_CODE ="Str_Bg_Code";
    public static final String KEY_RSAR_BUTTON_BG ="Str_Button_Bg";
    public static final String KEY_RSAR_TOP_BG_CODE ="Str_Top_Bg_Code";
    public static final String KEY_RSAR_ACT_STATUS ="Str_Act_Status";
    public static final String KEY_RSAR_CLASS_NAME ="Str_Class_Name";
    public static final String KEY_RSAR_CLASS_ID ="Str_Class_Id";
  //  public static final String KEY_RSAR_SUBJECT_NAME ="Str_Subject_Name";
    public static final String KEY_RSAR_SUBJECT_ID ="Str_Subject_Id";


   /* String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_RSAR_SCHOOL_UI
            + " TEXT PRIMARY KEY," + KEY_RSAR_SCHOOL_NAME + " TEXT,"
            + KEY_RSAR_BG_CODE + " TEXT," + KEY_RSAR_BUTTON_BG + " TEXT,"
            + KEY_RSAR_TOP_BG_CODE + " TEXT," + KEY_RSAR_ACT_STATUS + " TEXT,"
            + KEY_RSAR_CLASS_NAME + " TEXT," + KEY_RSAR_CLASS_ID + " TEXT,"
            + KEY_RSAR_SUBJECT_NAME + " TEXT," + KEY_RSAR_SUBJECT_ID + " TEXT)";*/

    String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_RSAR_CLASS_NAME + " TEXT," + KEY_RSAR_CLASS_ID + " TEXT)";

    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHandler(Class_Activity context) {
        super((Context) context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
 try{
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }


}
