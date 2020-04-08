package com.example.finalproject.phong_tran;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import com.example.nasaearthyimage.R;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * This class is used to take information from url, store it into an EarthImage field of parent class and update the fragment view.
 *
 * @author Phong Tran.
 * @version 1.0.0.
 */
public class EarthImageDetail extends Fragment {

    /**
     * This string store the longitude that is entered from the parent activity.
     */
    private String lonText;

    /**
     * This string store the latitude that is entered from the parent activity.
     */
    private String latText;

    /**
     * This is the progress bar of the parent activity.
     */
    private ProgressBar progressBar;

    /**
     * This is the text view that shows the longitude of the fragment.
     */
    private TextView lonView;

    /**
     * This is the text view that shows the latitude of the fragment.
     */
    private TextView latView;

    /**
     * This is the text view that shows the taken date of the fragment.
     */
    private TextView dateView;

    /**
     * This is the image view that shows the Earth Image of the fragment.
     */
    private ImageView earthImageView;

    /**
     * This is the parent activity.
     */
    private InformationPage parent;

    /**
     * This method overrides the onCreateView() of the super class. It will call the doInBackGround() of EarthImageQuery class to do it's work.
     *
     * @param inflater the inflater of the layout.
     * @param container the container of the view group.
     * @param savedInstanceState the Bundle contains saved instance state.
     * @return the fragment view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View imageDetail = inflater.inflate(R.layout.earth_image_detail, container, false);
        parent = (InformationPage) getActivity();

        progressBar = parent.findViewById(R.id.progressBar);
        lonView = imageDetail.findViewById(R.id.lonValue);
        latView = imageDetail.findViewById(R.id.latValue);
        dateView = imageDetail.findViewById(R.id.date);
        earthImageView = imageDetail.findViewById(R.id.earthImage);

        Bundle getData = getArguments();
        lonText = getData.getString(InformationPage.LONGITUDE);
        latText = getData.getString(InformationPage.LATITUDE);

        EarthImageQuery earthImageQuery = new EarthImageQuery();
        earthImageQuery.execute();

        return imageDetail;
    }

    /**
     * This is an inner class which is extended from AsyncTask().
     */
    public class EarthImageQuery extends AsyncTask<String, Integer, String> {
        /**
         * This string store the information of errors if occur.
         */
        String ret;

        /**
         * This string store the longitude of the outer class
         */
        String lon;

        /**
         * This string store the latitude of the outer class
         */
        String lat;

        /**
         * This string store the taken date of the outer class
         */
        String date;

        /**
         * This string is the url of the image which is base on longitude and latitude.
         */
        String queryUrl = String.format("https://dev.virtualearth.net/REST/V1/Imagery/Map/Birdseye/%s,%s/20?dir=180&ms=500,500&key=ArPnSmhLaKdjFK9Ac8C6mZEs0EmJNirA6EQpLe5OWAP2CxKTsb7Jfs3m-3Tt-asx", lonText, latText);

        /**
         * This is the bitmap generated from the image url.
         */
        Bitmap earthImage = null;

        /**
         * This method overrides the super class's method. It takes information from the given url and set the views of the fragment.
         *
         * @param strings Strings of the first generic type of the class.
         * @return the ret field of the class.
         */
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

        /**
         * This method overrides its super class's method. It updates the progress bar.
         *
         * @param values values of the progress bar.
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * This method overrides its super class's method. It executes the result of this class.
         *
         * @param s the last generic type of this class.
         */
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
