package com.briggin.footballfinder.common.api.room.team;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TeamsDao_Impl implements TeamsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TeamEntity> __insertionAdapterOfTeamEntity;

  private final EntityDeletionOrUpdateAdapter<TeamEntity> __updateAdapterOfTeamEntity;

  public TeamsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTeamEntity = new EntityInsertionAdapter<TeamEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `teams` (`id`,`name`,`city`,`stadium`,`nationality`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TeamEntity value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getCity() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCity());
        }
        if (value.getStadium() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStadium());
        }
        if (value.getNationality() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getNationality());
        }
      }
    };
    this.__updateAdapterOfTeamEntity = new EntityDeletionOrUpdateAdapter<TeamEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `teams` SET `id` = ?,`name` = ?,`city` = ?,`stadium` = ?,`nationality` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, TeamEntity value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getCity() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getCity());
        }
        if (value.getStadium() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStadium());
        }
        if (value.getNationality() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getNationality());
        }
        if (value.getId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getId());
        }
      }
    };
  }

  @Override
  public Object newTeam(final TeamEntity entity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTeamEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object updateTeam(final TeamEntity entity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTeamEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object getTeams(final Continuation<? super List<TeamEntity>> p0) {
    final String _sql = "SELECT * FROM teams";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.execute(__db, false, new Callable<List<TeamEntity>>() {
      @Override
      public List<TeamEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfStadium = CursorUtil.getColumnIndexOrThrow(_cursor, "stadium");
          final int _cursorIndexOfNationality = CursorUtil.getColumnIndexOrThrow(_cursor, "nationality");
          final List<TeamEntity> _result = new ArrayList<TeamEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final TeamEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCity;
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
            final String _tmpStadium;
            _tmpStadium = _cursor.getString(_cursorIndexOfStadium);
            final String _tmpNationality;
            _tmpNationality = _cursor.getString(_cursorIndexOfNationality);
            _item = new TeamEntity(_tmpId,_tmpName,_tmpCity,_tmpStadium,_tmpNationality);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, p0);
  }

  @Override
  public Object getTeam(final String id, final Continuation<? super TeamEntity> p1) {
    final String _sql = "SELECT * FROM teams WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    return CoroutinesRoom.execute(__db, false, new Callable<TeamEntity>() {
      @Override
      public TeamEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfStadium = CursorUtil.getColumnIndexOrThrow(_cursor, "stadium");
          final int _cursorIndexOfNationality = CursorUtil.getColumnIndexOrThrow(_cursor, "nationality");
          final TeamEntity _result;
          if(_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpCity;
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
            final String _tmpStadium;
            _tmpStadium = _cursor.getString(_cursorIndexOfStadium);
            final String _tmpNationality;
            _tmpNationality = _cursor.getString(_cursorIndexOfNationality);
            _result = new TeamEntity(_tmpId,_tmpName,_tmpCity,_tmpStadium,_tmpNationality);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, p1);
  }
}
