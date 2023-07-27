package foreverappar.com.dynamics;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class RecordDatabase extends SQLiteOpenHelper {

        // Logcat tag
        private static final String LOG = "RecordDatabase";

        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "contactsManager";

    SQLiteDatabase mDatabase;

    /*save files details*/
    public static final String TABLE_SAVE = "save_table";
    public static final String Save_ID = "ID";
    public static final String Save_Class_ID = "Class_ID";
    public static final String Save_Class_Name = "Class_Name";
    public static final String Save_Subject_Name = "Subject_Name";
    public static final String Save_Book_Name = "BookName";
    public static final String Save_Chapter_Id = "Chapter_Id";
    public static final String Save_Chapter_Name = "Chapter_Name";
    public static final String Save_Video_Name = "Video_Name";
    public static final String Save_Download_Link = "Download_Link";
    public static final String Save_Download_Status = "Download_status";
    public static final String Save_Zip_Name = "Zip_Name";
    public static final String Save_Asses_Value = "Asses_Value";
    public static final String Save_DataSet_Name = "DataSet_Name";

    String CREATE_SAVE_TABLE = "create table " + TABLE_SAVE + "(" + Save_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Save_Class_ID + " TEXT NOT NULL, " + Save_Class_Name + " TEXT NOT NULL, "
            + Save_Subject_Name + " TEXT NOT NULL, " + Save_Book_Name + " TEXT NOT NULL, " + Save_Chapter_Id + " TEXT NOT NULL, " + Save_Chapter_Name + " TEXT NOT NULL, "
            + Save_Video_Name + " TEXT NOT NULL, " + Save_Download_Link + " TEXT NOT NULL, " + Save_Download_Status + " TEXT, "
            + Save_Zip_Name + " TEXT NOT NULL, " + Save_Asses_Value + " TEXT NOT NULL, " + Save_DataSet_Name + " TEXT NOT NULL)";



    public RecordDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SAVE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE);
            onCreate(db);
        }

        public SQLiteDatabase getWritable()
        {
            mDatabase = this.getWritableDatabase();
            return mDatabase;
        }


    public ArrayList<ChapterModel> getAllRecord(String BookName)
    {
        ArrayList<ChapterModel> chapterModels = new ArrayList<>();
        String name="";
        mDatabase=getWritable();
//        Cursor  cursor = mDatabase.rawQuery("select * from "+RecordDatabase.TABLE_SAVE,null);
        Cursor  cursor = mDatabase.rawQuery("select * from "+RecordDatabase.TABLE_SAVE +
                " where "+RecordDatabase.Save_Book_Name + " = '" +BookName+"'",null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                name = cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Download_Status));
                Log.e("getRecord", "name  "+name);


               /* chapterModels.add(new ChapterModel(className, Subject_Name,Book_Name, School_UI, Restrict_SD, Class_ID, Message,
                        chapterObject.getString("Chapter_Id"), chapterObject.getString("Chapter_Name"),
                        chapterObject.getString("Assessment_Name"), chapterObject.getString("Video_Name"),
                        chapterObject.getString("DB_Name"),
                        chapterObject.getString("Download_Link"), downloadStatus,DataSet_Name, ZipName));*/


                chapterModels.add(new ChapterModel(cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Class_Name)),
                        cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Subject_Name)),
                        cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Book_Name)),
                        "",
                        "",
                        cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Class_ID)),
                        "",
                        cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Chapter_Id)),
                        cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Chapter_Name)),
                        "",
                        cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Video_Name)),
                        "",
                        "",
                        cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Download_Status))
                        ,cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Zip_Name)),
                        cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Asses_Value)),
                        cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_DataSet_Name))
                ));
                // list.add(name);
                cursor.moveToNext();
            }
        }

        return chapterModels;
    }


    public String getRecord(String BookName, String Save_Chapter_Id)
        {
            String name="";
            mDatabase=getWritable();
            Cursor  cursor = mDatabase.rawQuery("select * from "+RecordDatabase.TABLE_SAVE +
                    " where "+RecordDatabase.Save_Book_Name + " = '" + BookName +"' AND "+RecordDatabase.Save_Chapter_Id + " = " + Save_Chapter_Id,null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    name = cursor.getString(cursor.getColumnIndex(RecordDatabase.Save_Download_Status));
                    Log.e("getRecord", "name  "+name);

                   // list.add(name);
                    cursor.moveToNext();
                }
            }

            return name;
        }

    public void insertRecord(ContentValues contentValues)
        {
            mDatabase=getWritable();
            long l = mDatabase.insertWithOnConflict(RecordDatabase.TABLE_SAVE, null, contentValues, SQLiteDatabase.CONFLICT_ABORT);
            Log.e("insertRecord", "insertRecord  "+l);
          //  mDatabase.execSQL();
        }

        public void  updateDownloadStatus(String BookName, String Save_Chapter_Id, String download_status)
        {
            String strWhereClause = RecordDatabase.Save_Book_Name + " = '" + BookName +"' AND "+RecordDatabase.Save_Chapter_Id + " = " + Save_Chapter_Id;
            ContentValues cv = new ContentValues();
            cv.put(RecordDatabase.Save_Download_Status, download_status);
            mDatabase=getWritable();

            Log.e("strWhereClause", strWhereClause);
            int i = mDatabase.update(TABLE_SAVE,
                    cv,
                    RecordDatabase.Save_Book_Name + " = ? AND " + RecordDatabase.Save_Chapter_Id + " = ?",
                    new String[]{BookName, Save_Chapter_Id});
            Log.e("updateDownloadStatus", "updateDownloadStatus  "+i);
        }


    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SAVE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public  boolean CheckIsDataAlreadyInDBorNot(String BookName, String Save_Chapter_Id) {
        mDatabase=getWritable();

      //  String Query = "Select * from " + TABLE_SAVE + " where " + RecordDatabase.Save_Chapter_Id + " = " + Save_Chapter_Id;
        String Query = "Select * from " + TABLE_SAVE + " where " +
                RecordDatabase.Save_Book_Name + " = '" + BookName +"' AND "+RecordDatabase.Save_Chapter_Id + " = " + Save_Chapter_Id;

        Cursor cursor = mDatabase.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void deleteAllRecord()
    {
        /*UPDATE table_name SET numerazione = 1 WHERE numerazione != 1

        String strWhereClause = RecordDatabase.Save_Subject_Name + " = '" + Subject_Name +"' AND "+RecordDatabase.Save_Chapter_Id + " = " + Save_Chapter_Id;
        ContentValues cv = new ContentValues();
        cv.put(RecordDatabase.Save_Download_Status, download_status);
        mDatabase=getWritable();

        Log.e("strWhereClause", strWhereClause);
        int i = mDatabase.update(TABLE_SAVE,
                cv,
                RecordDatabase.Save_Subject_Name + " = ? AND " + RecordDatabase.Save_Chapter_Id + " = ?",
                new String[]{Subject_Name, Save_Chapter_Id});
        Log.e("updateDownloadStatus", "updateDownloadStatus  "+i);
*/

    }




    public boolean isTableExists(String tableName, boolean openDb) {
        if(openDb) {
            if(mDatabase == null || !mDatabase.isOpen()) {
                mDatabase = getReadableDatabase();
            }

            if(!mDatabase.isReadOnly()) {
                mDatabase.close();
                mDatabase = getReadableDatabase();
            }
        }

        Cursor cursor = mDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    }