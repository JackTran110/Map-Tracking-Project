package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.finalproject.abraham_mansour.NasaImageOfTheDay;
import com.example.finalproject.phong_tran.InformationPage;
import com.example.nasaearthyimage.R;

public class ApplicationHome extends AppCompatActivity {
    private Button toImageOfTheDay, toEarthImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_home);

        toImageOfTheDay = (Button)findViewById(R.id.dayImageButton);
        toEarthImage = (Button)findViewById(R.id.earthImageButton);

        toImageOfTheDay.setOnClickListener(v -> startActivity(new Intent(ApplicationHome.this, NasaImageOfTheDay.class)));
        toEarthImage.setOnClickListener(v -> startActivity(new Intent(ApplicationHome.this, InformationPage.class)));
    }
}

