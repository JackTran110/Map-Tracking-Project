package com.example.finalproject.abraham_mansour.DataModel;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class listDao_Impl implements listDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOflistDataModel;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOflistDataModel;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOflistDataModel;

  public listDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOflistDataModel = new EntityInsertionAdapter<listDataModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `listDataModel`(`id`,`ImageDate`,`ImagePath`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, listDataModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getImageDate() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getImageDate());
        }
        if (value.getImagePath() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getImagePath());
        }
      }
    };
    this.__deletionAdapterOflistDataModel = new EntityDeletionOrUpdateAdapter<listDataModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `listDataModel` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, listDataModel value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOflistDataModel = new EntityDeletionOrUpdateAdapter<listDataModel>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `listDataModel` SET `id` = ?,`ImageDate` = ?,`ImagePath` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, listDataModel value) {
        stmt.bindLong(1, value.getId());
        if (value.getImageDate() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getImageDate());
        }
        if (value.getImagePath() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getImagePath());
        }
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public void insert(listDataModel image) {
    __db.beginTransaction();
    try {
      __insertionAdapterOflistDataModel.insert(image);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(listDataModel image) {
    __db.beginTransaction();
    try {
      __deletionAdapterOflistDataModel.handle(image);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(listDataModel image) {
    __db.beginTransaction();
    try {
      __updateAdapterOflistDataModel.handle(image);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<listDataModel> getAll() {
    final String _sql = "SELECT * FROM listDataModel";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfImageDate = _cursor.getColumnIndexOrThrow("ImageDate");
      final int _cursorIndexOfImagePath = _cursor.getColumnIndexOrThrow("ImagePath");
      final List<listDataModel> _result = new ArrayList<listDataModel>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final listDataModel _item;
        _item = new listDataModel();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpImageDate;
        _tmpImageDate = _cursor.getString(_cursorIndexOfImageDate);
        _item.setImageDate(_tmpImageDate);
        final String _tmpImagePath;
        _tmpImagePath = _cursor.getString(_cursorIndexOfImagePath);
        _item.setImagePath(_tmpImagePath);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
