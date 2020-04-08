package com.example.finalproject.trang_nguyen;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.finalproject.ProjectDatabase;
import com.example.nasaearthyimage.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * @author Trang
 * Fragment class that take data from main class and pass it to a new layout
 * */
public class BBCNewsFragment extends Fragment {
    /**
     * No-args constructor
     */
    public BBCNewsFragment() {
    }
    private Bundle dataFromActivity;
    private AppCompatActivity parentActivity;
    ProjectDatabase mDatabaseHelper ;
    private Cursor cursor;
    private SQLiteDatabase sql;
    private ContentValues value = new ContentValues();
    public static final String ITEM_TITLE = "Title";
    public static final String ITEM_LINK = "Link";
    public static final String ITEM_DESCRIPTION = "Description";
    public static final String ITEM_DATE = "Date";
    /**
     * public method that draw user interface for the first time to draw a UI for a fragment
     * @param inflater set layout inflater
     * @param container container
     * @param savedInstanceState
     * @return
     */
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        View result =  inflater.inflate(R.layout.activity_bbcnews__detail, container, false);

         TextView bbc_title = (TextView)result.findViewById(R.id.title_bbc);
         bbc_title.setText(dataFromActivity.getString(BBCNewsMain.ITEM_TITLE));

         TextView bbc_date = (TextView)result.findViewById(R.id.date_bbc);
         bbc_date.setText(dataFromActivity.getString(BBCNewsMain.ITEM_DATE));

         TextView bbc_desc = (TextView)result.findViewById(R.id.description_bbc);
         bbc_desc.setText(dataFromActivity.getString(BBCNewsMain.ITEM_DESCRIPTION));
         TextView bbc_link = (TextView)result.findViewById(R.id.link_bbc);
         bbc_link.setText(dataFromActivity.getString(BBCNewsMain.ITEM_LINK));
         bbc_link.setMovementMethod(LinkMovementMethod.getInstance());
         ArrayList<BBCNews> arrayList = new ArrayList<>();
         mDatabaseHelper=new ProjectDatabase(parentActivity,1) ;
         sql=mDatabaseHelper.getWritableDatabase();

         Button finishButton = (Button)result.findViewById(R.id.button);

         finishButton.setOnClickListener( clk -> {

             AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(parentActivity);
             alertDialogBuilder
                     .setTitle(getString(R.string.item_alert_title)).setMessage(getString(R.string.item_alert_message))
                     .setPositiveButton(getString(R.string.yes), (DialogInterface dialog, int which) -> {
                         value.put(ProjectDatabase.TITLE_COL,bbc_title.getText().toString());
                         value.put(ProjectDatabase.DATE_COL,bbc_date.getText().toString());
                         value.put(ProjectDatabase.DESCRIPTION_COL,bbc_desc.getText().toString());
                         value.put(ProjectDatabase.URL_COL,bbc_link.getText().toString());
                         long id = sql.insert(ProjectDatabase.BBC_NEWS_TABLE,null,value);
                         Snackbar snackbar1= Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.snackbar_yes), Snackbar.LENGTH_SHORT);
                         snackbar1.show();
                     }).setNegativeButton(getString(R.string.no),(DialogInterface dialog, int which) -> {
                 Snackbar snackbar2= Snackbar.make(getActivity().findViewById(android.R.id.content),
                         getString(R.string.snackbar_no), Snackbar.LENGTH_SHORT);
                 snackbar2.show();});
             AlertDialog alertDialog = alertDialogBuilder.create();
             alertDialog.show();

         });
         Button viewButton=(Button)result.findViewById(R.id.view_list);
         viewButton.setOnClickListener( clk -> {
             Intent intent=new Intent(parentActivity, BBC_ListView.class);
             startActivity(intent);
         });
        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (AppCompatActivity)context;
    }
}
