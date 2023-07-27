package foreverappar.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import foreverappar.com.foreverappar.Subject;

public class DBHandlerSub extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "rsarschoolSub.db";
    public static  String TABLE_NAME ;
    public static final String KEY_RSAR_SUBJECT_NAME ="Str_Subject_Name";
    public static final String KEY_RSAR_SUBJECT_ID ="Str_Subject_Id";


    /* String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_RSAR_SUBJECT_NAME + " TEXT," + KEY_RSAR_SUBJECT_ID + " TEXT)";
    String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;*/

    public DBHandlerSub(Subject context) {
        super((Context) context, DB_NAME, null, DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + KEY_RSAR_SUBJECT_NAME + " TEXT," + KEY_RSAR_SUBJECT_ID + " TEXT)";
        try{
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
