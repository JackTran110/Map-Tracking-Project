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

import com.example.finalproject.abraham_mansour.NasaImageOfTheDay;
import com.example.finalproject.ny_nguyen.TheGuardianMainPage;
import com.example.finalproject.trang_nguyen.BBCNewsMain;
import com.google.android.material.navigation.NavigationView;
import com.example.nasaearthyimage.R;

/**
 * This class is the main activity of Earth Image.
 *
 * @author Phong Tran.
 * @version 1.0.0.
 */
public class InformationPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * This is the key of latitude in the Bundle Objects.
     */
    public static final String LATITUDE = "Latitude";

    /**
     * This is the key of longitude in the Bundle Objects.
     */
    public static final String LONGITUDE = "Longitude";

    /**
     * This is the key of boolean if the next activity is started for checking database or not in the Bundle Objects.
     */
    public static final String CHECK_DATABASE = "Close after finish";

    /**
     * This is the key of boolean if the next activity is started for saving image or not in the Bundle Objects.
     */
    public static final String IS_SAVE_BUTTON = "Is save button";

    /**
     * This intent will initialize later and will be used to start next activity.
     */
    private Intent toEarthImageList;

    /**
     * This Bundle stores data and send to the next activity.
     */
    private Bundle setData = new Bundle();

    /**
     * This field is the fragment class that will modify the current fragment.
     */
    private EarthImageDetail earthImageDetail = new EarthImageDetail();

    /**
     * This is the field stores the data of the current EarthImage.
     */
    private static EarthImage currentEarthImage;

    /**
     * This is the drawer layout of this class.
     */
    private DrawerLayout drawerLayout;

    /**
     * This is the edit text that get longitude from the user.
     */
    private EditText getLon;

    /**
     * This is the edit text that get latitude from the user.
     */
    private EditText getLat;

    /**
     * This is the save button of this class.
     */
    private Button saveImage;

    /**
     * This is the load button of this class.
     */
    private Button loadImage;

    /**
     * This method overrides its super class's method.
     * <p>
     *     In this class. The user can enter coordinates and take an image of the Earth.
     *     The user can save the image to the database.
     *     The user can load the favourite list by starting EarthSavedImageList activity.
     *     The user can go back to ApplicationHome activity.
     *     Data of SharedPreference is loaded to the edit texts.
     *     User can see information of this activity by selecting the "help" menu item.
     * </p>
     *
     * @param savedInstanceState a Bundle Object of saved instance state.
     */
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

    /**
     * This method overrides its super class's method. I set the load button base on result took from the next activity.
     *
     * @param requestCode the request code.
     * @param resultCode the response code.
     * @param data the extra data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 0) checkButton(loadImage, true);
        if (requestCode == 1 && resultCode == 1) checkButton(loadImage, false);
    }

    /**
     * This method overrides its super class's method. It stores data to SharedPreference after the user finished the activity.
     */
    @Override
    public void onPause(){
        super.onPause();
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        preferences.edit().putString(LONGITUDE, getLon.getText().toString()).apply();
        preferences.edit().putString(LATITUDE, getLat.getText().toString()).apply();
    }

    /**
     * This method overrides its super class's method. It sets menu for the action bar.
     *
     * @param menu teh action bar's menu.
     * @return a boolean if the menu is set successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        try{
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.earth_image_toolbar_menu, menu);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * This method overrides its super class's method. It setups menu for the actionbar.
     *
     * @param item menu item that was selected by the user.
     * @return a boolean if the action bar's menu is setup successfully.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        try{
            toolbarSetup(item.getItemId());
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * This method overrides interface onNavigationItemSelected's method. It setups menu for the navigation view.
     *
     * @param item menu item that was selected by the user.
     * @return a boolean if the navigation view's menu is setup successfully.
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        try {
            toolbarSetup(item.getItemId());
            drawerLayout.closeDrawers();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * This method is used to get the current EarthImage.
     *
     * @return the current EarthImage.
     */
    public static EarthImage getEarthImage(){
        return currentEarthImage;
    }

    /**
     * This method is used to set the current EarthImage
     *
     * @param earthImage the EarthImage that is set for the current one.
     */
    public void setEarthImage(EarthImage earthImage){
        currentEarthImage = earthImage;
    }

    /**
     * This method setups the given menu.
     *
     * @param itemId id of the menu item that was selected.
     */
    private void toolbarSetup(int itemId){
        Intent intent;
        switch (itemId){
            case R.id.clear:
                getFragmentManager().beginTransaction().remove(earthImageDetail).commit();
                checkButton(saveImage, false);
                break;
            case R.id.save:
                if(!saveImage.isEnabled()){
                    Toast.makeText(this, R.string.noImage, Toast.LENGTH_SHORT).show();
                    break;
                }
                saveImage();
                break;
            case R.id.load:
                if(!loadImage.isEnabled()){
                    Toast.makeText(this, R.string.noImageInList, Toast.LENGTH_SHORT).show();
                    break;
                }
                loadImage();
                break;
            case R.id.toGuardian:
                intent = new Intent(this, TheGuardianMainPage.class);
                startActivity(intent);
                break;
            case R.id.toDayImage:
                intent = new Intent(this, NasaImageOfTheDay.class);
                startActivity(intent);
                break;
            case R.id.toBBCNews:
                intent = new Intent(this, BBCNewsMain.class);
                startActivity(intent);
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

    /**
     * This method check if a condition is true or false then set the given button to disable or enable.
     *
     * @param button the button that about to be set.
     * @param condition the condition to set the button.
     */
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

    /**
     * This method check the database before starting this activity.
     */
    private void checkDatabase(){
        setData.putBoolean(CHECK_DATABASE, true);
        setData.putBoolean(IS_SAVE_BUTTON, false);
        toEarthImageList.putExtras(setData);
        startActivityForResult(toEarthImageList, 1);
    }

    /**
     * This method is used to get EarthImage of the given coordinates and set the current fragment view.
     */
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

    /**
     * This method saves the current EarthImage into database.
     */
    private void saveImage(){
        setData.putBoolean(IS_SAVE_BUTTON, true);
        setData.putBoolean(CHECK_DATABASE, false);
        toEarthImageList.putExtras(setData);
        startActivityForResult(toEarthImageList, 1);
        Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
    }

    /**
     * This method starts the next activity.
     */
    private void loadImage(){
        setData.putBoolean(IS_SAVE_BUTTON, false);
        setData.putBoolean(CHECK_DATABASE, false);
        toEarthImageList.putExtras(setData);
        startActivityForResult(toEarthImageList, 1);
    }
}
