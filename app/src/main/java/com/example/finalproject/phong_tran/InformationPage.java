package com.example.finalproject.phong_tran;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.navigation.NavigationView;
import com.example.nasaearthyimage.R;

public class InformationPage extends AppCompatActivity {

    public static final String LATITUDE = "Latitude", LONGITUDE = "Longitude", CHECK_DATABASE = "Close after finish", IS_SAVE_BUTTON = "Is save button";
    private Intent toEarthImageList;
    private Bundle setData = new Bundle();
    private EarthImageDetail earthImageDetail = new EarthImageDetail();
    private static EarthImage currentEarthImage;

    private DrawerLayout drawerLayout;
    private EditText getLon, getLat;
    private Button saveImage, loadImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        Button getImage = findViewById(R.id.getImage);
        saveImage = findViewById(R.id.saveImage);
        loadImage = findViewById(R.id.loadImage);
        getLon = findViewById(R.id.lon);
        getLat = findViewById(R.id.lat);

        toEarthImageList = new Intent(InformationPage.this, EarthSavedImageList.class);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        SharedPreferences preferences =getPreferences(Context.MODE_PRIVATE);
        getLon.setText(preferences.getString(LONGITUDE, ""));
        getLat.setText(preferences.getString(LATITUDE, ""));

        checkDatabase();
        checkButton(saveImage, findViewById(R.id.frame).isShown());

        loadImage.setOnClickListener(v -> loadImage());
        saveImage.setOnClickListener(v -> saveImage());
        getImage.setOnClickListener(v -> getImage());
    }

    public void setEarthImage(EarthImage earthImage){
        currentEarthImage = earthImage;
    }

    public static EarthImage getEarthImage(){
        return currentEarthImage;
    }

    public void checkButton(Button button, boolean condition){
        if(!condition){
            button.setBackground(getDrawable(R.drawable.unclickable_btn));
            button.setEnabled(false);
        }
        else {
            button.setBackground(getDrawable(R.drawable.btn_round_background));
            button.setEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.earth_image_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        toolbarSetup(item.getItemId());
        return true;
    }

    private boolean onNavigationItemSelected(MenuItem item){
        toolbarSetup(item.getItemId());
        drawerLayout.closeDrawers();
        return true;
    }

    private void saveImage(){
        setData.putBoolean(IS_SAVE_BUTTON, true);
        setData.putBoolean(CHECK_DATABASE, false);
        toEarthImageList.putExtras(setData);
        startActivityForResult(toEarthImageList, 1);
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    }

    private void loadImage(){
        setData.putBoolean(IS_SAVE_BUTTON, false);
        setData.putBoolean(CHECK_DATABASE, false);
        toEarthImageList.putExtras(setData);
        startActivityForResult(toEarthImageList, 1);
    }

    private void toolbarSetup(int itemId){
        switch (itemId){
            case R.id.clear:
                getFragmentManager().beginTransaction().remove(earthImageDetail).commit();
                checkButton(saveImage, false);
                break;
            case R.id.save:
                saveImage();
                break;
            case R.id.load:
                loadImage();
                break;
            case R.id.help:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.helpTitle);
                builder.setMessage(R.string.helpMessage);
                builder.create().show();
                break;
            case R.id.back:
                finish();
        }
    }

    private void getImage(){
        EarthImageDetail earthImageDetail = new EarthImageDetail();

        if(getLon.getText().toString().isEmpty())
            Toast.makeText(this, R.string.emptyLon, Toast.LENGTH_SHORT).show();

        else if (getLat.getText().toString().isEmpty())
            Toast.makeText(this, R.string.emptyLat, Toast.LENGTH_SHORT).show();

        else {
            try{
                Double.parseDouble(getLon.getText().toString());
                Double.parseDouble(getLat.getText().toString());
                setData.putString(LONGITUDE, getLon.getText().toString());
                setData.putString(LATITUDE, getLat.getText().toString());

                earthImageDetail.setArguments(setData);
                getFragmentManager().beginTransaction().replace(R.id.frame, earthImageDetail).commit();
                this.earthImageDetail = earthImageDetail;
            }catch (Exception ex){
                Toast.makeText(this, R.string.wrongCoordinates, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.edit().putString(LONGITUDE, getLon.getText().toString()).apply();
        preferences.edit().putString(LATITUDE, getLat.getText().toString()).apply();
    }

    private void checkDatabase(){
        setData.putBoolean(CHECK_DATABASE, true);
        setData.putBoolean(IS_SAVE_BUTTON, false);
        toEarthImageList.putExtras(setData);
        startActivityForResult(toEarthImageList, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 0) checkButton(loadImage, true);
        if (requestCode == 1 && resultCode == 1) checkButton(loadImage, false);
    }
}
