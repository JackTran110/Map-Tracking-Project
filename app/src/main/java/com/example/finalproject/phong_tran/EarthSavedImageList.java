package com.example.finalproject.phong_tran;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.nasaearthyimage.R;

import java.util.ArrayList;

public class EarthSavedImageList extends AppCompatActivity {

    private ArrayList<EarthImage> list = new ArrayList();
    private ListView listView;
    private Button goBack;
    private EarthImageAdapter adapter;

    public EarthSavedImageList(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth_saved_image_list);

        listView = findViewById(R.id.earthImageList);
        goBack = findViewById(R.id.goBack);
        adapter = new EarthImageAdapter(this, R.layout.earth_image_detail, list);
        listView.setAdapter(adapter);

        if(getIntent().getExtras().getBoolean(InformationPage.FROM_SAVE_BUTTON)){
            list.add(InformationPage.getEarthImage());
            adapter.notifyDataSetChanged();
//            finish();
        }

            goBack.setOnClickListener(v -> finish());
    }
}