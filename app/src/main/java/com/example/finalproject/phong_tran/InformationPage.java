package com.example.finalproject.phong_tran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nasaearthyimage.R;

public class InformationPage extends AppCompatActivity {

    private Button getImage, saveImage, loadImage;
    private EditText getLon, getLat;
    private Bundle setData = new Bundle();
    public static final String LATITUDE = "Latitude", LONGITUDE = "Longitude", FROM_SAVE_BUTTON = "From save button";
    private EarthImageDetail earthImageDetail = new EarthImageDetail();
    private EarthSavedImageList earthSavedImageList = new EarthSavedImageList();
    private static EarthImage currentEarthImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_page);

        getImage = findViewById(R.id.getImage);
        saveImage = findViewById(R.id.saveImage);
        loadImage = findViewById(R.id.loadImage);
        getLon = findViewById(R.id.lon);
        getLat = findViewById(R.id.lat);


        checkButton(saveImage, findViewById(R.id.frame).isShown());

        Intent toEarthImageList = new Intent(InformationPage.this, EarthSavedImageList.class);
        loadImage.setOnClickListener(v -> {
            setData.putBoolean(FROM_SAVE_BUTTON, false);
            toEarthImageList.putExtras(setData);
            startActivity(toEarthImageList);
        });
        saveImage.setOnClickListener(v -> {
            setData.putBoolean(FROM_SAVE_BUTTON, true);
            toEarthImageList.putExtras(setData);
            startActivity(toEarthImageList, setData);
        });

        getImage.setOnClickListener(v -> {
            if(getLon.getText().toString()==null || getLon.getText().toString().isEmpty())
                Toast.makeText(this, R.string.emptyLon, Toast.LENGTH_SHORT).show();

            else if (getLat.getText().toString()==null || getLat.getText().toString().isEmpty())
                Toast.makeText(this, R.string.emptyLat, Toast.LENGTH_SHORT).show();

            else {
                setData.putString(LONGITUDE, getLon.getText().toString());
                setData.putString(LATITUDE, getLat.getText().toString());

                earthImageDetail.setArguments(setData);
                getFragmentManager().beginTransaction().replace(R.id.frame, earthImageDetail).commit();
            }
        });
    }

    public void setEarthImage(EarthImage earthImage){
        this.currentEarthImage = earthImage;
    }

    public static EarthImage getEarthImage(){
        return currentEarthImage;
    }

    boolean checkButton(Button button, boolean condition){
        if(!condition){
            button.setBackground(getDrawable(R.drawable.unclickable_btn));
            button.setEnabled(false);
        }
        else {
            button.setBackground(getDrawable(R.drawable.btn_round_background));
            button.setEnabled(true);
        }
        return condition;
    }
}
