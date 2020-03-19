package com.example.finalproject.phong_tran;

import android.graphics.Bitmap;

public class EarthImage {
    private long id;
    private String lon, lat, date;
    private Bitmap earthImageBitmap;

    public EarthImage(String lon, String lat, String date, Bitmap earthImageBitmap){
        this(-1, lon, lat, date, earthImageBitmap);
    }

    public EarthImage(long id, String lon, String lat, String date, Bitmap earthImageBitmap){
        setId(id);
        setLon(lon);
        setLat(lat);
        setDate(date);
        setEarthImageBitmap(earthImageBitmap);
    }

    public long getId() {
        return id;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getEarthImageBitmap() {
        return earthImageBitmap;
    }

    public EarthImage setLon(String lon) {
        this.lon = lon;
        return this;
    }

    public EarthImage setLat(String lat) {
        this.lat = lat;
        return this;
    }

    public EarthImage setDate(String date) {
        this.date = date;
        return this;
    }

    public EarthImage setEarthImageBitmap(Bitmap earthImageBitmap) {
        this.earthImageBitmap = earthImageBitmap;
        return this;
    }

    public EarthImage setId(long id) {
        this.id = id;
        return this;
    }
}
