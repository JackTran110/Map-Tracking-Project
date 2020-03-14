package com.example.nasaearthyimage.DataModel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface listDao {

    @Query("SELECT * FROM listDataModel")
    List<listDataModel> getAll();

    @Insert
    void insert(listDataModel image);

    @Delete
    void delete(listDataModel image);

    @Update
    void update(listDataModel image);

}
