package com.example.finalproject.phong_tran;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.finalproject.ProjectDatabase;
import com.example.nasaearthyimage.R;
import com.google.android.material.snackbar.Snackbar;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the Activity that contains the List of favourite Earth Image.
 *
 * @author Phong Tran.
 * @version 1.0.0.
 */
public class EarthSavedImageList extends AppCompatActivity {

    /**
     * This ProjectDatabase field is used to get and interact with the project's database.
     */
    private ProjectDatabase helper = new ProjectDatabase(this, 1);

    /**
     * This field is a list of Earth Image.
     */
    private List<EarthImage> list = new ArrayList<>();

    /**
     * This field is an adapter that can adapt Earth Images information into the device's list view.
     */
    private EarthImageAdapter adapter;

    /**
     * This is the builder of Alert Dialogs.
     */
    private AlertDialog.Builder builder;

    /**
     * this is the database of the project.
     */
    private static SQLiteDatabase db;

    /**
     * This method overrides the AppCompatActivity's onCreate method.
     *<p>
     *     In this method, the fields are declared, the action bar of the activity is set.
     *     Put data of the EarthImage field of the InformationPage class in the database.
     *     Data from the database is loaded to the device's list view.
     *     The list's on click listener, on long click listener and the Go Back button's listener is set.
     *</p>
     *
     * @param savedInstanceState a Bundle variable that stores the saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_saved_image_list);

        ListView listView = findViewById(R.id.earthImageList);
        Toolbar toolbar = findViewById(R.id.saved_list_toolbar);
        Button goBack = findViewById(R.id.goBack);

        builder = new AlertDialog.Builder(this);
        adapter = new EarthImageAdapter(this, R.layout.earth_image_detail, list);
        listView.setAdapter(adapter);
        db = helper.getWritableDatabase();

        setSupportActionBar(toolbar);

        if(Objects.requireNonNull(getIntent().getExtras()).getBoolean(InformationPage.IS_SAVE_BUTTON)){
            EarthImage earthImage = InformationPage.getEarthImage();
            helper.add(db, ProjectDatabase.EARTH_IMAGE_TABLE, earthImage.getLon(), earthImage.getLat(), earthImage.getDate(), null, getBytes(earthImage.getEarthImageBitmap()));
            setResult(0);
            finish();
        }

        if(Objects.requireNonNull(getIntent().getExtras().getBoolean(InformationPage.CHECK_DATABASE))){
            if(getListCount()==0) setResult(1);
            else setResult(0);
            finish();
        }

        loadList();

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            EarthImage earthImage = (EarthImage) parent.getAdapter().getItem(position);

            builder.setTitle(R.string.imageInformation);
            builder.setMessage(String.format((String) getText(R.string.imageInformationMessage), earthImage.getId(), earthImage.getDate(), earthImage.getLon(), earthImage.getLat()));
            builder.create().show();
        });

        listView.setOnItemLongClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            EarthImage earthImage = (EarthImage) parent.getAdapter().getItem(position);

            builder.setTitle(R.string.delete);
            builder.setMessage(R.string.deleteMessage);
            builder.setNeutralButton(R.string.cancel, (DialogInterface dialog, int which) -> Toast.makeText(this, R.string.cancelMessage, Toast.LENGTH_SHORT).show());
            builder.setNegativeButton(R.string.no, (DialogInterface dialog, int which) -> Toast.makeText(this, R.string.cancelMessage, Toast.LENGTH_SHORT).show());

            builder.setPositiveButton(R.string.yes, (DialogInterface dialog, int which) -> {
                list.remove(earthImage);
                adapter.notifyDataSetChanged();

                Snackbar.make(listView, R.string.deleteConfirm, Snackbar.LENGTH_LONG)
                        .addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int event) {
                                if(event != 1) {
                                    helper.delete(db, ProjectDatabase.EARTH_IMAGE_TABLE, earthImage.getId());
                                    if(list.size() == 0){
                                        setResult(1);
                                        finish();
                                    }
                                }
                            }
                        })
                        .setAction(R.string.undo, click -> {
                            list.add(position, earthImage);
                            adapter.notifyDataSetChanged();
                        }).show();
            });

            builder.create().show();
            return parent.isLongClickable();
        });

        goBack.setOnClickListener(v -> finish());
    }

    /**
     * This method is used to load information from the database to the device's list view.
     */
    private void loadList(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + ProjectDatabase.EARTH_IMAGE_TABLE, null);
        cursor.moveToNext();
        for(int i = 0; i < cursor.getCount(); i++){
            list.add(new EarthImage(cursor.getLong(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), BitmapFactory.decodeByteArray(cursor.getBlob(4), 0, cursor.getBlob(4).length)));
            cursor.moveToNext();
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    /**
     * This method is use to generate a Bitmap Object into a byte[] Object
     *
     * @param bitmap the Bitmap Object that need to be generated
     * @return the byte[] Object that is generated from the Bitmap Object.
     */
    private static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    /**
     * This method is used to get count of images in the database.
     *
     * @return count of images in the database
     */
    private static int getListCount(){
        int listCount;
        Cursor cursor = db.rawQuery("SELECT * FROM " + ProjectDatabase.EARTH_IMAGE_TABLE, null);
        listCount = cursor.getCount();
        cursor.close();
        return listCount;
    }
}