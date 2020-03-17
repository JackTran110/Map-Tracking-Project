package com.example.finalproject.abraham_mansour.DataModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class listDataModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "ImageDate")
    private String ImageDate;

    @ColumnInfo(name = "ImagePath")
    private String ImagePath;

    /*
     * Getters and Setters
     * */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageDate() {
        return ImageDate;
    }

    public void setImageDate(String ImageDate) {
        this.ImageDate = ImageDate;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String Path) {
        this.ImagePath = Path;
    }
}
