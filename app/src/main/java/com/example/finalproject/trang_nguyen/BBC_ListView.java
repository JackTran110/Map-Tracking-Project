package com.example.finalproject.trang_nguyen;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.ProjectDatabase;
import com.example.nasaearthyimage.R;

import java.util.ArrayList;

public class BBC_ListView extends AppCompatActivity {
    private ListView listView;
    ProjectDatabase dbHelper;
    private Cursor cursor;
    private SQLiteDatabase sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbc_favorite_list);
        listView=findViewById(R.id.list_bbc_favorite);
        dbHelper= new ProjectDatabase(this,1);
        sql = dbHelper.getReadableDatabase();
        ArrayList<BBCNews> favoriteItem= new ArrayList<>();
        cursor = sql.rawQuery("SELECT * FROM BBC_NEWS", null);
         printCursor(cursor, sql);
         for (int i = 0; i < cursor.getCount(); i++) {
             favoriteItem.add(new BBCNews(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                     cursor.getString(3), cursor.getString(4)));
             cursor.moveToNext();
         }
       BBCAdapter_DB adapter=new BBCAdapter_DB(this,favoriteItem);
         listView.setAdapter(adapter);
         adapter.notifyDataSetChanged();
   listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id)->{
    BBCNews bbcItem = (BBCNews) parent.getAdapter().getItem(position);
       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
       alertDialogBuilder
               .setTitle(getString(R.string.fav_title)).setMessage(getString(R.string.fav_message))
               .setPositiveButton(getString(R.string.yes), (DialogInterface dialog, int which) -> {
                       sql.delete(ProjectDatabase.BBC_NEWS_TABLE, ProjectDatabase.ID_COL + "=" + bbcItem.getId(), null);
                       favoriteItem.remove(position);
                       adapter.notifyDataSetChanged();
                       Toast makeText1= Toast.makeText(getApplicationContext(), getResources().getText(R.string.fav_yes),Toast.LENGTH_SHORT);
                       makeText1.show();
               }).
               setNegativeButton( getString(R.string.no),(DialogInterface dialog, int which) -> {
                   Toast.makeText(this, getString(R.string.fav_no),Toast.LENGTH_SHORT).show();
           });
       AlertDialog alertDialog = alertDialogBuilder.create();
       alertDialog.show();
});
    }
    private void printCursor(Cursor c, SQLiteDatabase sql){
        c.moveToNext();
        String results = "";
        for(int i = 0; i < c.getCount(); i++){
            results = results + "Row " + (i+1) + ": id: " + c.getInt(0) +
                    ", Title: " + c.getString(1) + ", Description: " + c.getString(2)
                    + ",  Date: " + c.getString(3)+ ", Url: "+ c.getString(4) +".\n";
            c.moveToNext();
        }

        Log.i("Database_information", "\nDatabase version number: " + sql.getVersion()
                + ".\nNumber of columns: " + c.getColumnCount()+ ".\nColumns name: "
                + c.getColumnName(0) + ", " + c.getColumnName(1) + ", "
                + c.getColumnName(2) + c.getColumnName(3) + c.getColumnName(4) + ".\nResults:\n" + results);
        c.moveToFirst();
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
