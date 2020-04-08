package com.example.finalproject.phong_tran;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.nasaearthyimage.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class EarthImageDetail extends Fragment {

    private String lonText, latText;
    private View imageDetail;
    private ProgressBar progressBar;
    private TextView lonView, latView, dateView;
    private ImageView earthImageView;
    private InformationPage parent;
    private Bundle getData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        imageDetail = inflater.inflate(R.layout.earth_image_detail, container, false);
        parent = (InformationPage) getActivity();

        progressBar = parent.findViewById(R.id.progressBar);
        lonView = imageDetail.findViewById(R.id.lonValue);
        latView = imageDetail.findViewById(R.id.latValue);
        dateView = imageDetail.findViewById(R.id.date);
        earthImageView = imageDetail.findViewById(R.id.earthImage);

        getData = getArguments();
        lonText = getData.getString(InformationPage.LONGITUDE);
        latText = getData.getString(InformationPage.LATITUDE);

        EarthImageQuery earthImageQuery = new EarthImageQuery();
        earthImageQuery.execute();

        return imageDetail;
    }

    public class EarthImageQuery extends AsyncTask<String, Integer, String> {
        String ret, lon, lat, date, queryUrl = String.format("https://dev.virtualearth.net/REST/V1/Imagery/Map/Birdseye/%s,%s/20?dir=180&ms=500,500&key=ArPnSmhLaKdjFK9Ac8C6mZEs0EmJNirA6EQpLe5OWAP2CxKTsb7Jfs3m-3Tt-asx", lonText, latText);
        Bitmap earthImage = null;

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... strings) {
            try{
                lon = lonText;
                publishProgress(25);
                lat = latText;
                publishProgress(50);

                date = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
                publishProgress(75);
                URL url = new URL(queryUrl);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    earthImage = BitmapFactory.decodeStream(urlConnection.getInputStream());
                }
                publishProgress(100);
            }
            catch(MalformedURLException mfe){ ret = "Malformed URL exception."; }
            catch(IOException ioe)          { ret = "IO exception. Is the Wifi connected?";}

            return ret;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            lonView.setText(String.format("%s %s.", parent.getText(R.string.lon), lon));
            latView.setText(String.format("%s %s.", parent.getText(R.string.lat), lat));
            dateView.setText(String.format("%s %s.", getText(R.string.date), date));
            try{
                earthImage.getWidth();
                earthImageView.setImageBitmap(earthImage);
                parent.checkButton(parent.findViewById(R.id.saveImage), true);
            }catch (Exception ex){
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(parent);
                alertBuilder.setTitle(parent.getText(R.string.findImageFail));
                alertBuilder.setMessage(parent.getText(R.string.findImageFailMessage));
                alertBuilder.create().show();
                parent.getFragmentManager().beginTransaction().remove(EarthImageDetail.this).commit();
                parent.checkButton(parent.findViewById(R.id.saveImage), false);
            }
            parent.setEarthImage(new EarthImage(lon, lat, date, earthImage));
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
