package com.example.finalproject.DataModel;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {listDataModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract listDao listDao();
}
