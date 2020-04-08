package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

/**
 * This class is extended from SQLiteOpenHelper. It is used to interact with this project's database.
 *
 * @author Phong Tran
 * @version 1.0.0.
 */
public class ProjectDatabase extends SQLiteOpenHelper {

    /**
     * This is the database name.
     */
    public final static String DATABASE_NAME = "FINAL_PROJECT";

    /**
     * This is the ID column name.
     */
    public final static String ID_COL = "_id";

    /**
     * This is the title column name.
     */
    public final static String TITLE_COL = "TITLE";

    /**
     * This is the date column name.
     */
    public final static String DATE_COL = "DATE";

    /**
     * This is the URL column name.
     */
    public final static String URL_COL = "URL";

    /**
     * This is the query that used to create Earth Image table.
     */
    public final static String CREATE_QUERY_EARTH_IMAGE = "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s BLOB)";

    /**
     * This is the query that used to create BBC News table.
     */
    public final static String CREATE_QUERY_BBC_NEWS = "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)";

    /**
     * This is the query that used to create Guardian News table.
     */
    public final static String CREATE_QUERY_GUARDIAN_NEWS = "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)";

    /**
     * This is the Earth Image table name.
     */
    public final static String EARTH_IMAGE_TABLE = "EARTH_IMAGE";

    /**
     * This is the longitude column name.
     */
    public final static String LONGITUDE_COL = "LONGITUDE";

    /**
     * This is the latitude column name.
     */
    public final static String LATITUDE_COL = "LATITUDE";

    /**
     * This is the Bitmap column name.
     */
    public final static String BITMAP_COL = "BITMAP";

    /**
     * This is the BBC News table name.
     */
    public final static String BBC_NEWS_TABLE = "BBC_NEWS";

    /**
     * This is the description column name.
     */
    public final static String DESCRIPTION_COL = "DESCRIPTION";

    /**
     * This is the Guardian News table name.
     */
    public final static String GUARDIAN_NEWS_TABLE = "GUARDIAN_NEWS";

    /**
     * This is the section column name.
     */
    public final static String SECTION_COL = "SECTION";

    /**
     * The ContentValues field of this class.
     */
    private ContentValues cv = new ContentValues();

    /**
     * This constructor is used to create a ProjectDatabase Objects.
     *
     * @param context parent context.
     * @param version version of the database.
     */
    public ProjectDatabase(@Nullable Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    /**
     * This method overrides its super class's method. It creates a SQLiteDatabase for this project.
     *
     * @param db the SQLiteDatabase.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format(CREATE_QUERY_EARTH_IMAGE, EARTH_IMAGE_TABLE, ID_COL, LONGITUDE_COL, LATITUDE_COL, DATE_COL, BITMAP_COL));
        db.execSQL(String.format(CREATE_QUERY_BBC_NEWS, BBC_NEWS_TABLE, ID_COL, TITLE_COL, DESCRIPTION_COL, DATE_COL, URL_COL));
        db.execSQL(String.format(CREATE_QUERY_GUARDIAN_NEWS, GUARDIAN_NEWS_TABLE, ID_COL, TITLE_COL, URL_COL, SECTION_COL));
    }

    /**
     * This method overrides its super class's method. It upgrades the current database to a higher version.
     *
     * @param db the SQLiteDataBase
     * @param oldVersion the current version.
     * @param newVersion the upgraded version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EARTH_IMAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BBC_NEWS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GUARDIAN_NEWS_TABLE);
        onCreate(db);
    }

    /**
     * This method takes data and insert them into the database.
     *
     * @param db the SQLiteDatabase
     * @param tableName the table's name.
     * @param data1 String data1.
     * @param data2 String data2.
     * @param data3 String data3.
     * @param data4 String data4.
     * @param data5 byte[] data5 which is used to store data of a picture.
     * @return the id of the new item if it is inserted successfully, otherwise return -1.
     */
    public long add(SQLiteDatabase db, String tableName, String data1, String data2, String data3, String data4, byte[] data5){
        switch(tableName){
            case EARTH_IMAGE_TABLE:
                cv.put(LONGITUDE_COL, data1);
                cv.put(LATITUDE_COL, data2);
                cv.put(DATE_COL, data3);
                cv.put(BITMAP_COL, data5);
                try {
                    return db.insert(tableName, null, cv);
                }catch (Exception ex){
                    ex.printStackTrace();
                    return -1;
                }
            case BBC_NEWS_TABLE:
                cv.put(TITLE_COL, data1);
                cv.put(DESCRIPTION_COL, data2);
                cv.put(DATE_COL, data3);
                cv.put(URL_COL, data4);
                try {
                    return db.insert(tableName, null, cv);
                }catch (Exception ex){
                    ex.printStackTrace();
                    return -1;
                }
            case GUARDIAN_NEWS_TABLE:
                cv.put(TITLE_COL, data1);
                cv.put(URL_COL, data2);
                cv.put(SECTION_COL, data3);
                try {
                    return db.insert(tableName, null, cv);
                }catch (Exception ex){
                    ex.printStackTrace();
                    return -1;
                }
            default: return -1;
        }
    }

    /**
     * This method is used to delete a item from the database.
     *
     * @param db the SQLiteDatabase.
     * @param tableName the table's name.
     * @param id id of the item.
     * @return 0 if the item is deleted successfully, otherwise return -1.
     */
    public long delete(SQLiteDatabase db, String tableName, long id){
        try{
            db.delete(tableName, ID_COL + " = " + Long.toString(id), null);
            return 0;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }
}
