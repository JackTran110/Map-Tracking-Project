package com.example.finalproject.ny_nguyen;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/**
 *
 * @author Ny Nguyen
 */

public class TheGuardianGet extends AsyncTask<Object, Integer, Object> {
    /**
     * This class is executed tasks asynchronously in the background.
     */

    private Context ctx;
    private ProgressBar progressBar;
    private View container;
    private String keywords;
    private ArrayList<TheGuardianArticle> listArticle = new ArrayList<>();

    public TheGuardianGet(Context ctx, ProgressBar progressBar, View container, String keywords) {
        this.ctx = ctx;
        this.progressBar = progressBar;
        this.container = container;
        this.keywords = keywords;
    }

    /**
     * the method contains the codes which needs to be executed in background,
     * Its results can use for the activities.
      */

    @Override
    protected String doInBackground(Object... objs) {
        try {
            publishProgress(0);
            URL url = new URL(String.format("https://content.guardianapis.com/search?api-key=1fb36b70-1588-4259-b703-2570ea1fac6a&q=%s", keywords));
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream response = urlConnection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            String result = sb.toString();
            JSONObject json = new JSONObject(result);
            JSONArray arr = json.getJSONObject("response").getJSONArray("results");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                TheGuardianArticle newArti = new TheGuardianArticle(null, obj.getString("webTitle"), obj.getString("webUrl"), obj.getString("sectionName"));
                listArticle.add(newArti);
            }

            publishProgress(50);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
    // using the results above to update the UI
    @Override
    protected void onPostExecute(Object obj) {
        super.onPostExecute(obj);
        progressBar.setProgress(100);
        progressBar.setVisibility(View.INVISIBLE);
        container.setAlpha(1);
        container.setClickable(Boolean.TRUE);

        Intent intent = new Intent(this.ctx, TheGuardianArticleList.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(TheGuardianSearchFragment.ARTICLE_LIST, this.listArticle);
        intent.putExtras(bundle);
        this.ctx.startActivity(intent);
    }
    // operate progress of asynchronous
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(values[0]);
        container.setAlpha(0.2f);
        container.setClickable(Boolean.FALSE);
    }
}
