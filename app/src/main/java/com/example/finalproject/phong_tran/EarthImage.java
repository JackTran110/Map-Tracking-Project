package com.example.finalproject.phong_tran;

import android.graphics.Bitmap;

/**
 * This class represents the type "EarthImage" which stores information of an earth image.
 *
 * @author Phong Tran.
 * @version 1.0.0.
 */
public class EarthImage {

    /**
     * This integer field stores the value of the image's id.
     */
    private long id;

    /**
     * This String field stores the image's longitude.
     */
    private String lon;

    /**
     * This String field stores the image's latitude.
     */
    private String lat;

    /**
     * This String field stores the image's taken date.
     */
    private String date;

    /**
     * This Bitmap field stores the image's map to create itself.
     */
    private Bitmap earthImageBitmap;


    /**
     * This constructor is used to create an Earth Image Object without declare its id. Usually used to create Earth Image Objects that are not about to store in the database yet.
     *
     * @param lon the image's longitude.
     * @param lat the image's latitude.
     * @param date the image's date.
     * @param earthImageBitmap the image's Bitmap.
     */
    public EarthImage(String lon, String lat, String date, Bitmap earthImageBitmap){
        this(-1, lon, lat, date, earthImageBitmap);
    }

    /**
     * This constructor is used to create an full Earth Image Object. Usually used to store the images information that is taken from database.
     *
     * @param id the image's id in the database.
     * @param lon the image's longitude.
     * @param lat the image's latitude.
     * @param date the image's date.
     * @param earthImageBitmap the image's Bitmap.
     */
    public EarthImage(long id, String lon, String lat, String date, Bitmap earthImageBitmap){
        setId(id).setLon(lon).setLat(lat).setDate(date).setEarthImageBitmap(earthImageBitmap);
    }

    /**
     * This method is used to get the id of an Earth Image Object.
     *
     * @return the image's id.
     */
    public long getId() {
        return id;
    }

    /**
     * This method is used to get the longitude of an Earth Image Object.
     *
     * @return the image's longitude.
     */
    public String getLon() {
        return lon;
    }

    /**
     * This method is used to get the latitude of an Earth Image Object.
     *
     * @return the image's latitude.
     */
    public String getLat() {
        return lat;
    }

    /**
     * This method is used to get the taken date of an Earth Image Object.
     *
     * @return the image's taken date.
     */
    public String getDate() {
        return date;
    }

    /**
     * This method is used to get the Bitmap of an Earth Image Object.
     *
     * @return the image's Bitmap.
     */
    public Bitmap getEarthImageBitmap() {
        return earthImageBitmap;
    }

    /**
     * This method is used to set the id of an Earth Object.
     *
     * @param id the image's id.
     * @return the Earth Object that has been set the id.
     */
    public EarthImage setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * This method is used to set the longitude of an Earth Object.
     *
     * @param lon the image's longitude.
     * @return the Earth Object that has been set the longitude.
     */
    public EarthImage setLon(String lon) {
        this.lon = lon;
        return this;
    }

    /**
     * This method is used to set the latitude of an Earth Object.
     *
     * @param lat the image's latitude.
     * @return the Earth Object that has been set the latitude.
     */
    public EarthImage setLat(String lat) {
        this.lat = lat;
        return this;
    }

    /**
     * This method is used to set the taken date of an Earth Object.
     *
     * @param date the image's taken date.
     * @return the Earth Object that has been set the taken date.
     */
    public EarthImage setDate(String date) {
        this.date = date;
        return this;
    }

    /**
     * This method is used to set the Bitmap of an Earth Object.
     *
     * @param earthImageBitmap the image's Bitmap.
     * @return the Earth Object that has been set the Bitmap.
     */
    public EarthImage setEarthImageBitmap(Bitmap earthImageBitmap) {
        this.earthImageBitmap = earthImageBitmap;
        return this;
    }
}
