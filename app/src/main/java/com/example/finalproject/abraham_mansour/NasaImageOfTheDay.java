package com.example.finalproject.abraham_mansour;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.finalproject.abraham_mansour.DataModel.NASAModelData;
import com.example.finalproject.abraham_mansour.DataModel.listDataModel;
import com.example.finalproject.abraham_mansour.DataModel.ApiInterface;
import com.example.finalproject.abraham_mansour.DataModel.DatabaseClient;
import com.example.nasaearthyimage.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NasaImageOfTheDay extends AppCompatActivity {
    TextView dateTextView, urlTextView, seeAllTextView;
    DatePickerDialog picker;
    EditText etDate;
    Button btnGet, hdImage;
    String hdUrl, dateTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_image_of_the_day);
        initListeners();

        if (ContextCompat.checkSelfPermission(NasaImageOfTheDay.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(NasaImageOfTheDay.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(NasaImageOfTheDay.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
            ActivityCompat.requestPermissions(NasaImageOfTheDay.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            Toast.makeText(NasaImageOfTheDay.this, "Need Permission to access storage for Downloading Image", Toast.LENGTH_SHORT).show();
        }

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(NasaImageOfTheDay.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                etDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                getNASAEarthyImage();
            }
        });

        hdImage.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(NasaImageOfTheDay.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(NasaImageOfTheDay.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(NasaImageOfTheDay.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
                    ActivityCompat.requestPermissions(NasaImageOfTheDay.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
                    Toast.makeText(NasaImageOfTheDay.this, "Need Permission to access storage for Downloading Image", Toast.LENGTH_SHORT).show();
                }
                else {
                    new DownloadsImage().execute(hdUrl);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(hdUrl));
                    startActivity(browserIntent);
                }
            }
        });

        seeAllTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            //On click function
            public void onClick(View view) {
                Intent intent = new Intent(NasaImageOfTheDay.this, PreviusRecord.class);
                startActivity(intent);
            }
        });
    }

    private void initListeners() {
        dateTextView=findViewById(R.id.date);
        urlTextView=findViewById(R.id.url);
        etDate=findViewById(R.id.etDate);
        btnGet=findViewById(R.id.btnGet);
        hdImage=findViewById(R.id.hdImage);
        seeAllTextView=findViewById(R.id.seeAll);
    }

    private void getNASAEarthyImage() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        dateTxt = etDate.getText().toString();

        Call<NASAModelData> call = api.getNasaData("DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d", dateTxt);
        call.enqueue(new Callback<NASAModelData>() {
            @Override
            public void onResponse(Call<NASAModelData> call, Response<NASAModelData> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(NasaImageOfTheDay.this, "Response Successfully", Toast.LENGTH_SHORT).show();
                    String date=response.body().getDate();
                    String url=response.body().getUrl();
                    dateTextView.setText(date);
                    urlTextView.setText(url);
                    hdUrl = response.body().getHdurl();
                }
            }

            @Override
            public void onFailure(Call<NASAModelData> call, Throwable t) {
                Toast.makeText(NasaImageOfTheDay.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class DownloadsImage extends AsyncTask<String, Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            URL url = null;
            Bitmap bm = null;

            try {
                url = new URL(strings[0]);
                bm =    BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Create Path to save Image
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES+ "/NasaImages");

            //Creates app specific folder
            if(!path.exists()) {
                path.mkdirs();
            }

            File imageFile = new File(path, String.valueOf(System.currentTimeMillis())+".jpg"); // Imagename.png
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(imageFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try{
                bm.compress(Bitmap.CompressFormat.PNG, 100, out); // Compress Image
                out.flush();
                out.close();

                listDataModel imageObj = new listDataModel();
                imageObj.setImageDate(dateTxt);
                imageObj.setImagePath(imageFile.getPath());

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .listDao()
                        .insert(imageObj);

                MediaScannerConnection.scanFile(NasaImageOfTheDay.this,new String[] { imageFile.getAbsolutePath() }, null,new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
            } catch(Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(NasaImageOfTheDay.this, "Image Saved", Toast.LENGTH_SHORT).show();
        }
    }
}