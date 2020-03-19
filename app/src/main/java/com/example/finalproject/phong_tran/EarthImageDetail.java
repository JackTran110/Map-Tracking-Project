package com.example.finalproject.phong_tran;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

public class EarthImageDetail extends Fragment {

    private String lonText, latText;
    private View imageDetail;
    private ProgressBar progressBar;
    private TextView lonView, latView, dateView;
    private ImageView earthImageView;
    private InformationPage parent;
    private CheckBox checkBox;
    private Bundle getData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        imageDetail = inflater.inflate(R.layout.earth_image_detail, container, false);
        parent = (InformationPage) getActivity();

        checkBox = imageDetail.findViewById(R.id.checkBox);
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

        checkBox.setVisibility(View.INVISIBLE);
        return imageDetail;
    }

    public class EarthImageQuery extends AsyncTask<String, Integer, String> {
        String ret, lon, lat, date, imageId, queryImageUrl, queryUrl = String.format("https://api.nasa.gov/planetary/earth/imagery/?lon=%s&lat=%s&date=2017-05-17&api_key=DEMO_KEY", lonText, latText);
        Bitmap earthImage;

        @Override
        protected String doInBackground(String... strings) {
            try{
                lon = lonText;
                publishProgress(25);
                lat = latText;
                publishProgress(50);

                URL url = new URL(queryUrl);
                HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = reader.readLine()) != null){
                    sb.append(line).append("\n");
                }
                String result = sb.toString();

                JSONObject jsonObject = new JSONObject(result);
                date = jsonObject.getString("date").substring(0, 10);
                publishProgress(75);
                imageId = jsonObject.getString("id");
                queryImageUrl = jsonObject.getString("url");

                if(imageId != null && !imageId.isEmpty() && fileExistence(imageId)){
                    FileInputStream fis = null;
                    try {    fis = parent.openFileInput(imageId);   }
                    catch (FileNotFoundException e) {    e.printStackTrace();  }
                    earthImage = BitmapFactory.decodeStream(fis);
                    Log.i(imageId, "File is already exists.");
                }
                else {
                    url = new URL(queryImageUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == 200) {
                        earthImage = BitmapFactory.decodeStream(urlConnection.getInputStream());
                    }
                }
                publishProgress(100);
            }
            catch(MalformedURLException mfe){ ret = "Malformed URL exception."; }
            catch(IOException ioe)          { ret = "IO exception. Is the Wifi connected?";}
            catch (JSONException e)         { ret = "Json exception. Can't get Json object.";}

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
            if(date == null || date.isEmpty() || earthImage == null || earthImage.equals("")){
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(parent);
                alertBuilder.setTitle(parent.getText(R.string.findImageFail));
                alertBuilder.setMessage(parent.getText(R.string.findImageFailMessage));
                alertBuilder.create().show();
                parent.getFragmentManager().beginTransaction().remove(EarthImageDetail.this).commit();
                parent.checkButton(parent.findViewById(R.id.saveImage), false);
            }
            else{
                dateView.setText(String.format("%s %s.", getText(R.string.date), date));
                earthImageView.setImageBitmap(earthImage);
            }
            parent.setEarthImage(new EarthImage(lon, lat, date, earthImage));
            parent.checkButton(parent.findViewById(R.id.saveImage), true);
            progressBar.setVisibility(View.INVISIBLE);
        }

        public boolean fileExistence(String name){
            File file;
            try {
                file = parent.getFileStreamPath(name);
                return file.exists();
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
    }
}