package com.briggin.footballfinder.common.api.room;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.briggin.footballfinder.common.api.room.player.PlayerDao;
import com.briggin.footballfinder.common.api.room.player.PlayerDao_Impl;
import com.briggin.footballfinder.common.api.room.team.TeamsDao;
import com.briggin.footballfinder.common.api.room.team.TeamsDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FootballDatabase_Impl extends FootballDatabase {
  private volatile TeamsDao _teamsDao;

  private volatile PlayerDao _playerDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `teams` (`id` TEXT NOT NULL, `name` TEXT NOT NULL, `city` TEXT NOT NULL, `stadium` TEXT NOT NULL, `nationality` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `players` (`id` TEXT NOT NULL, `firstName` TEXT NOT NULL, `secondName` TEXT NOT NULL, `nationality` TEXT NOT NULL, `age` INTEGER, `club` TEXT NOT NULL, `isFavourite` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'e40722743dfda401d7e3fd478029aca3')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `teams`");
        _db.execSQL("DROP TABLE IF EXISTS `players`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
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
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTeams = new HashMap<String, TableInfo.Column>(5);
        _columnsTeams.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("city", new TableInfo.Column("city", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("stadium", new TableInfo.Column("stadium", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTeams.put("nationality", new TableInfo.Column("nationality", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTeams = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTeams = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTeams = new TableInfo("teams", _columnsTeams, _foreignKeysTeams, _indicesTeams);
        final TableInfo _existingTeams = TableInfo.read(_db, "teams");
        if (! _infoTeams.equals(_existingTeams)) {
          return new RoomOpenHelper.ValidationResult(false, "teams(com.briggin.footballfinder.common.api.room.team.TeamEntity).\n"
                  + " Expected:\n" + _infoTeams + "\n"
                  + " Found:\n" + _existingTeams);
        }
        final HashMap<String, TableInfo.Column> _columnsPlayers = new HashMap<String, TableInfo.Column>(7);
        _columnsPlayers.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("firstName", new TableInfo.Column("firstName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("secondName", new TableInfo.Column("secondName", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("nationality", new TableInfo.Column("nationality", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("age", new TableInfo.Column("age", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("club", new TableInfo.Column("club", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPlayers.put("isFavourite", new TableInfo.Column("isFavourite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlayers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlayers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlayers = new TableInfo("players", _columnsPlayers, _foreignKeysPlayers, _indicesPlayers);
        final TableInfo _existingPlayers = TableInfo.read(_db, "players");
        if (! _infoPlayers.equals(_existingPlayers)) {
          return new RoomOpenHelper.ValidationResult(false, "players(com.briggin.footballfinder.common.api.room.player.PlayerEntity).\n"
                  + " Expected:\n" + _infoPlayers + "\n"
                  + " Found:\n" + _existingPlayers);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "e40722743dfda401d7e3fd478029aca3", "a5478e133c815dd93766248c33cfbf2d");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "teams","players");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `teams`");
      _db.execSQL("DELETE FROM `players`");
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
  public TeamsDao teamsDao() {
    if (_teamsDao != null) {
      return _teamsDao;
    } else {
      synchronized(this) {
        if(_teamsDao == null) {
          _teamsDao = new TeamsDao_Impl(this);
        }
        return _teamsDao;
      }
    }
  }

  @Override
  public PlayerDao playersDao() {
    if (_playerDao != null) {
      return _playerDao;
    } else {
      synchronized(this) {
        if(_playerDao == null) {
          _playerDao = new PlayerDao_Impl(this);
        }
        return _playerDao;
      }
    }
  }
}
