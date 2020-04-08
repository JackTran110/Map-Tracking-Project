package com.example.finalproject.ny_nguyen;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.finalproject.ProjectDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Ny Nguyen
 */

public class TheGuardianArticle implements Serializable {
    /**
     * This class is used to put data from searching into database
     */
    private Long id;
    private String title;
    private String url;
    private String section;


    public TheGuardianArticle(Long id, String title, String url, String section) {
        /**
         * the constructor with 4 parameters
         * @param id,title,url,section
         */
        this.id = id;
        this.url = url;
        this.title = title;
        this.section = section;
    }

    public static List<TheGuardianArticle> getAll(ProjectDatabase db) {
        List<TheGuardianArticle> res = new ArrayList<>();
        // get data from searching
        Cursor cursor = db.getReadableDatabase().query(ProjectDatabase.GUARDIAN_NEWS_TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Long id = cursor.getLong(cursor.getColumnIndex(ProjectDatabase.ID_COL));
                String url = cursor.getString(cursor.getColumnIndex(ProjectDatabase.URL_COL));
                String title = cursor.getString(cursor.getColumnIndex(ProjectDatabase.TITLE_COL));
                String section = cursor.getString(cursor.getColumnIndex(ProjectDatabase.SECTION_COL));

                res.add(new TheGuardianArticle(id, title, url, section));

                cursor.moveToNext();
            }
        }

        return res;
    }
    // insert getting data into database
    public static Long insert(ProjectDatabase db, TheGuardianArticle article) {
        ContentValues newArticle = new ContentValues();
        newArticle.put(ProjectDatabase.URL_COL, article.getUrl());
        newArticle.put(ProjectDatabase.TITLE_COL, article.getTitle());
        newArticle.put(ProjectDatabase.SECTION_COL, article.getSection());
        return db.getWritableDatabase().insert(ProjectDatabase.GUARDIAN_NEWS_TABLE, null, newArticle);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public static int delete(ProjectDatabase db, Long id) {
        return db.getWritableDatabase().delete(ProjectDatabase.GUARDIAN_NEWS_TABLE, String.format("%s = ?", ProjectDatabase.ID_COL), new String[]{Long.toString(id)});
    }
}

