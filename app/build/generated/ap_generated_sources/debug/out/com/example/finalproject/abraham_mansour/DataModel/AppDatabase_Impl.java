package com.example.finalproject.abraham_mansour.DataModel;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public final class AppDatabase_Impl extends AppDatabase {
  private volatile listDao _listDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `listDataModel` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `ImageDate` TEXT, `ImagePath` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"6e4fd6856f35e101bde081cdd5f2d7cb\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `listDataModel`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsListDataModel = new HashMap<String, TableInfo.Column>(3);
        _columnsListDataModel.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsListDataModel.put("ImageDate", new TableInfo.Column("ImageDate", "TEXT", false, 0));
        _columnsListDataModel.put("ImagePath", new TableInfo.Column("ImagePath", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysListDataModel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesListDataModel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoListDataModel = new TableInfo("listDataModel", _columnsListDataModel, _foreignKeysListDataModel, _indicesListDataModel);
        final TableInfo _existingListDataModel = TableInfo.read(_db, "listDataModel");
        if (! _infoListDataModel.equals(_existingListDataModel)) {
          throw new IllegalStateException("Migration didn't properly handle listDataModel(com.example.finalproject.abraham_mansour.DataModel.listDataModel).\n"
                  + " Expected:\n" + _infoListDataModel + "\n"
                  + " Found:\n" + _existingListDataModel);
        }
      }
    }, "6e4fd6856f35e101bde081cdd5f2d7cb", "24b4f8249df8c04668555c586ff9a954");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "listDataModel");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `listDataModel`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public listDao listDao() {
    if (_listDao != null) {
      return _listDao;
    } else {
      synchronized(this) {
        if(_listDao == null) {
          _listDao = new listDao_Impl(this);
        }
        return _listDao;
      }
    }
  }
}
