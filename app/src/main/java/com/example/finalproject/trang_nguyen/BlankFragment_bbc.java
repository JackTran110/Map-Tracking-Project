package com.example.finalproject.trang_nguyen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nasaearthyimage.R;

public class BlankFragment_bbc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blank_bbc);
        Bundle dataToPass = getIntent().getExtras();

        BBCNewsFragment dFragment = new BBCNewsFragment();
        dFragment.setArguments( dataToPass );
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentBBC, dFragment)
                .commit();
    }
}
