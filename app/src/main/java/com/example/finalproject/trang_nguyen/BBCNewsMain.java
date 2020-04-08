package com.example.finalproject.trang_nguyen;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.finalproject.abraham_mansour.NasaImageOfTheDay;
import com.example.finalproject.ny_nguyen.TheGuardianMainPage;
import com.example.finalproject.phong_tran.InformationPage;
import com.example.nasaearthyimage.R;
import com.google.android.material.navigation.NavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Trang
 * Main class that execute the list view and show item details */
public class BBCNewsMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView listView;
    public static final String ITEM_TITLE = "Title";
    public static final String ITEM_LINK = "Link";
    public static final String ITEM_DESCRIPTION = "Description";
    public static final String ITEM_DATE = "Date";
    private ProgressBar bar;
    private EditText searchText;

    //ArrayList<BBCNews> listBBCNews;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    SharedPreferences prefs;
    BBCAsyncTask getXml;

    /**
     * @param savedInstanceState main method that set up the layout and execute the list view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbc_main_list);

        this.listView = (ListView) findViewById(R.id.bbcListView);
        bar = findViewById(R.id.list_progressbar);
        bar.setVisibility(View.VISIBLE);
        getXml = new BBCAsyncTask();
        getXml.execute();

        listView.setTextFilterEnabled(true);
        searchText = findViewById(R.id.search_text);
        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("TypedText", "");
        searchText.setText(savedString);

        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) ->
        {
            saveSharedPrefs(searchText.getText().toString());
            Toast toast = Toast.makeText(getApplicationContext(),
                    getResources().getText(R.string.item_click), Toast.LENGTH_SHORT);
            toast.show();

            BBCNews bbcItem = (BBCNews) parent.getAdapter().getItem(position);
            Bundle dataToPass = new Bundle();
            dataToPass.putString(ITEM_TITLE, bbcItem.getTitle());
            dataToPass.putString(ITEM_DESCRIPTION, bbcItem.getData());
            dataToPass.putString(ITEM_DATE, bbcItem.getDate());
            dataToPass.putString(ITEM_LINK, bbcItem.getLink());
            Intent nextActivity = new Intent(this, BlankFragment_bbc.class);
            nextActivity.putExtras(dataToPass); //send data to next activity
            startActivity(nextActivity); //make the transition

        });
        Toolbar toolbar = findViewById(R.id.bbc_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.bbc_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        try {
            switch (item.getItemId()) {
                case R.id.bbc_author:
                    intent = new Intent(this, TheGuardianMainPage.class);
                    startActivity(intent);
                    break;
                case R.id.image_nasa:
                    intent = new Intent(this, NasaImageOfTheDay.class);
                    startActivity(intent);
                    break;
                case R.id.nasa_earth:
                    intent = new Intent(this, InformationPage.class);
                    startActivity(intent);
                    break;
                case R.id.reload:
                    finish();
                    startActivity(getIntent());
                    break;
                case R.id.help_bbc:
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder
                            .setTitle(getResources().getString(R.string.bbc_help_title)).setMessage(getResources().getString(R.string.bbc_help_message) + "\n" +
                            getString(R.string.bbc_help_message_2));
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    break;
                default:
                    return super.onOptionsItemSelected(item);
            }
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        try {
            switch (menuItem.getItemId()) {
                case R.id.bbc_author:
                    intent = new Intent(this, TheGuardianMainPage.class);
                    startActivity(intent);
                    break;
                case R.id.image_nasa:
                    intent = new Intent(this, NasaImageOfTheDay.class);
                    startActivity(intent);
                    break;
                case R.id.nasa_earth:
                    intent = new Intent(this, InformationPage.class);
                    startActivity(intent);
                    break;
                case R.id.reload:
                    finish();
                    startActivity(getIntent());
                    break;
                case R.id.help_bbc:
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder
                            .setTitle(getResources().getString(R.string.bbc_help_title)).setMessage(getResources().getString(R.string.bbc_help_message) + "\n" +
                            getString(R.string.bbc_help_message_2));
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    break;
                default:
                    return super.onOptionsItemSelected(menuItem);
            }
            mDrawerLayout.closeDrawers();
            return true;
        }catch (Exception ex){
            return false;
        }
    }

    private void saveSharedPrefs(String stringToSave) {
        SharedPreferences prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("TypedText", stringToSave);
        editor.commit();

    }

    /**
     * AsyncTask class that used to extract data from an xml file.
     */
    public class BBCAsyncTask extends AsyncTask<Object, Integer, Object> {
        HttpURLConnection connection;
        ArrayList<BBCNews> bbcList = new ArrayList<>();
        ListView listView;
        BBCNews bbcNews;



        /**
         * Override method for executing after invoked UI thread
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        /**
         * Override method that do all the work in background, invoked
         * immediately after onPreExecute(), it performs all the computation that can take some time.
         *
         * @param objects
         * @return array list of BBCNews
         */
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                URL url = new URL("https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream response = connection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                bbcNews = new BBCNews();
                int eventType;

                while (!((eventType = xpp.getEventType()) == XmlPullParser.START_TAG) || !xpp.getName().equals("item")) {
                    eventType = xpp.next();
                    xpp.getName();
                }
                while ((eventType = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        switch (xpp.getName()) {
                            case ("item"): {
                                if (bbcNews.getTitle() != null && bbcNews.getData() != null && bbcNews.getDate() != null && bbcNews.getLink() != null)
                                    bbcList.add(bbcNews);
                                bbcNews = new BBCNews();
                                publishProgress(50);
                                Thread.sleep(100);
                                break;
                            }
                            case ("title"): {
                                bbcNews.setTitle(xpp.nextText());
                                publishProgress(50);
                                break;
                            }
                            case ("description"): {
                                bbcNews.setData(xpp.nextText());
                                publishProgress(50);
                                break;
                            }
                            case ("pubDate"): {
                                bbcNews.setDate(xpp.nextText());
                                publishProgress(50);
                                break;
                            }
                            case ("link"): {
                                bbcNews.setLink(xpp.nextText());
                                publishProgress(75);
                                break;
                            }
                        }
                        xpp.next();
                    }

                    eventType = xpp.next(); //move to next element

                }

                publishProgress(100);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return bbcList;
        }

        /**
         * Override method that invoked on the UI thread after a call to
         * publishProgress() that set on background
         *
         * @param value
         */
        @Override
        public void onProgressUpdate(Integer... value) {
            if (bar != null) {
                bar.setVisibility(View.VISIBLE);
                bar.setProgress(value[0]);
            }
        }

        /**
         * Override method invoked on the UI thread after the background computations finishes.
         *
         * @param o background computation, list of BBCNews
         */
        @Override
        protected void onPostExecute(Object o) {
            bar.setVisibility(View.INVISIBLE);
            listView = findViewById(R.id.bbcListView);

            BBCNewsAdapter adapter = new BBCNewsAdapter(BBCNewsMain.this, bbcList);
            listView.setAdapter(adapter);
            searchText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    s = s.toString().toLowerCase(Locale.getDefault());
                    ArrayList<BBCNews> aList = new ArrayList<>();
                    ArrayList<BBCNews> bList = new ArrayList<>();
                    bList.addAll(bbcList);
                    if (s.toString().length() != 0) {
                        for (BBCNews newItem : bbcList) {
                            if (!newItem.getTitle().toLowerCase().contains(s.toString())) {
                                aList.add(newItem);
                            }
                        }
                        bList.removeAll(aList);
                        adapter.setNewsList(bList);
                        adapter.notifyDataSetChanged();
                        aList.clear();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            adapter.notifyDataSetChanged();

        }

    }

}


