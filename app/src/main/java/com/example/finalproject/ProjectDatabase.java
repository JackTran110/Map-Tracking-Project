package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ProjectDatabase extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "FINAL_PROJECT";
    public final static String ID_COL = "_id";
    public final static String TITLE_COL = "TITLE";
    public final static String DATE_COL = "DATE";
    public final static String URL_COL = "URL";
    public final static String CREATE_QUERY_4_COLUMN = "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)";
    public final static String CREATE_QUERY_3_COLUMN = "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)";


    public final static String EARTH_IMAGE_TABLE = "EARTH_IMAGE";
    public final static String LONGITUDE_COL = "LONGITUDE";
    public final static String LATITUDE_COL = "LATITUDE";
    public final static String BITMAP_COL = "BITMAP";

    public final static String BBC_NEWS_TABLE = "BBC_NEWS";
    public final static String DESCRIPTION_COL = "DESCRIPTION";

    public final static String GUARDIAN_NEWS_TABLE = "GUARDIAN_NEWS";
    public final static String SECTION_COL = "SECTION";

    private ContentValues cv = new ContentValues();


    public ProjectDatabase(@Nullable Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(CREATE_QUERY_4_COLUMN, EARTH_IMAGE_TABLE, ID_COL, LONGITUDE_COL, LATITUDE_COL, DATE_COL, BITMAP_COL));
        db.execSQL(String.format(CREATE_QUERY_4_COLUMN, BBC_NEWS_TABLE, ID_COL, TITLE_COL, DESCRIPTION_COL, DATE_COL, URL_COL));
        db.execSQL(String.format(CREATE_QUERY_3_COLUMN, GUARDIAN_NEWS_TABLE, ID_COL, TITLE_COL, URL_COL, SECTION_COL));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EARTH_IMAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BBC_NEWS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GUARDIAN_NEWS_TABLE);
        onCreate(db);
    }

    public long add(SQLiteDatabase db, String tableName, String data1, String data2, String data3, String data4){
        switch(tableName){
            case EARTH_IMAGE_TABLE:
                cv.put(LONGITUDE_COL, data1);
                cv.put(LATITUDE_COL, data2);
                cv.put(DATE_COL, data3);
                cv.put(BITMAP_COL, data4);
                return db.insert(tableName, null, cv);
            case BBC_NEWS_TABLE:
                cv.put(TITLE_COL, data1);
                cv.put(DESCRIPTION_COL, data2);
                cv.put(DATE_COL, data3);
                cv.put(URL_COL, data4);
                return db.insert(tableName, null, cv);
            case GUARDIAN_NEWS_TABLE:
                cv.put(TITLE_COL, data1);
                cv.put(URL_COL, data2);
                cv.put(SECTION_COL, data3);
                return db.insert(tableName, null, cv);
            default: return -1;
        }
    }

    public long delete(SQLiteDatabase db, String tableName, long id){
        try{
            db.delete(tableName, "? = ?", new String[] {ID_COL, Long.toString(id)});
            return 0;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }
}
