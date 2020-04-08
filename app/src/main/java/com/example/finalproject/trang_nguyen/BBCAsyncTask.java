package com.example.finalproject.trang_nguyen;

import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BBCAsyncTask extends AsyncTask<Object,Integer,Object>{
    HttpURLConnection connection;
    ArrayList<BBCNews> bbcList=new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            URL url = new URL("http://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream response = connection.getInputStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(response  , "UTF-8");

            BBCNews bbcNews = new BBCNews();
            String curText = "";
            int eventType;

            while(!((eventType = xpp.getEventType()) == XmlPullParser.START_TAG) || !xpp.getName().equals("item")){
                eventType = xpp.next();
                curText = xpp.getName();
            }
            while ((eventType = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    switch (xpp.getName()) {
                        case ("item"): {
                            if(bbcNews.getTitle()!=null && bbcNews.getData()!=null && bbcNews.getDate() != null && bbcNews.getLink()!=null)
                            bbcList.add(bbcNews);
                            bbcNews = new BBCNews();
                            break;
                        }
                        case ("title"): {
                            bbcNews.setTitle(xpp.nextText());
                            break;
                        }
                        case ("description"): {
                            bbcNews.setData(xpp.nextText());
                            break;
                        }
                        case ("pubDate"): {
                            bbcNews.setDate(xpp.nextText());
                            break;
                        }
                        case ("link"): {
                            bbcNews.setLink(xpp.nextText());
                            break;
                        }
                    }
                    xpp.next();
                }

                eventType = xpp.next(); //move to next element

            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bbcList;
    }

    public ArrayList<BBCNews> getBBCList(){
        return bbcList;
    }
}