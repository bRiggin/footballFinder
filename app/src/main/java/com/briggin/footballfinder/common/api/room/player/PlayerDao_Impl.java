package com.briggin.footballfinder.common.api.room.player;

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
import java.lang.Integer;
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
public final class PlayerDao_Impl implements PlayerDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PlayerEntity> __insertionAdapterOfPlayerEntity;

  private final EntityDeletionOrUpdateAdapter<PlayerEntity> __updateAdapterOfPlayerEntity;

  public PlayerDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlayerEntity = new EntityInsertionAdapter<PlayerEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `players` (`id`,`firstName`,`secondName`,`nationality`,`age`,`club`,`isFavourite`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PlayerEntity value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getSecondName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSecondName());
        }
        if (value.getNationality() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNationality());
        }
        if (value.getAge() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getAge());
        }
        if (value.getClub() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getClub());
        }
        final int _tmp;
        _tmp = value.isFavourite() ? 1 : 0;
        stmt.bindLong(7, _tmp);
      }
    };
    this.__updateAdapterOfPlayerEntity = new EntityDeletionOrUpdateAdapter<PlayerEntity>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `players` SET `id` = ?,`firstName` = ?,`secondName` = ?,`nationality` = ?,`age` = ?,`club` = ?,`isFavourite` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PlayerEntity value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getFirstName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getFirstName());
        }
        if (value.getSecondName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSecondName());
        }
        if (value.getNationality() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getNationality());
        }
        if (value.getAge() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, value.getAge());
        }
        if (value.getClub() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getClub());
        }
        final int _tmp;
        _tmp = value.isFavourite() ? 1 : 0;
        stmt.bindLong(7, _tmp);
        if (value.getId() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getId());
        }
      }
    };
  }

  @Override
  public Object newPlayer(final PlayerEntity entity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfPlayerEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object updatePlayer(final PlayerEntity entity, final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPlayerEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object getPlayers(final Continuation<? super List<PlayerEntity>> p0) {
    final String _sql = "SELECT * FROM players";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.execute(__db, false, new Callable<List<PlayerEntity>>() {
      @Override
      public List<PlayerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
          final int _cursorIndexOfSecondName = CursorUtil.getColumnIndexOrThrow(_cursor, "secondName");
          final int _cursorIndexOfNationality = CursorUtil.getColumnIndexOrThrow(_cursor, "nationality");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfClub = CursorUtil.getColumnIndexOrThrow(_cursor, "club");
          final int _cursorIndexOfIsFavourite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavourite");
          final List<PlayerEntity> _result = new ArrayList<PlayerEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PlayerEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpFirstName;
            _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            final String _tmpSecondName;
            _tmpSecondName = _cursor.getString(_cursorIndexOfSecondName);
            final String _tmpNationality;
            _tmpNationality = _cursor.getString(_cursorIndexOfNationality);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            final String _tmpClub;
            _tmpClub = _cursor.getString(_cursorIndexOfClub);
            final boolean _tmpIsFavourite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavourite);
            _tmpIsFavourite = _tmp != 0;
            _item = new PlayerEntity(_tmpId,_tmpFirstName,_tmpSecondName,_tmpNationality,_tmpAge,_tmpClub,_tmpIsFavourite);
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
  public Object getFavouritePlayers(final Continuation<? super List<PlayerEntity>> p0) {
    final String _sql = "SELECT * FROM players WHERE isFavourite";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.execute(__db, false, new Callable<List<PlayerEntity>>() {
      @Override
      public List<PlayerEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
          final int _cursorIndexOfSecondName = CursorUtil.getColumnIndexOrThrow(_cursor, "secondName");
          final int _cursorIndexOfNationality = CursorUtil.getColumnIndexOrThrow(_cursor, "nationality");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfClub = CursorUtil.getColumnIndexOrThrow(_cursor, "club");
          final int _cursorIndexOfIsFavourite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavourite");
          final List<PlayerEntity> _result = new ArrayList<PlayerEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PlayerEntity _item;
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpFirstName;
            _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            final String _tmpSecondName;
            _tmpSecondName = _cursor.getString(_cursorIndexOfSecondName);
            final String _tmpNationality;
            _tmpNationality = _cursor.getString(_cursorIndexOfNationality);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            final String _tmpClub;
            _tmpClub = _cursor.getString(_cursorIndexOfClub);
            final boolean _tmpIsFavourite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavourite);
            _tmpIsFavourite = _tmp != 0;
            _item = new PlayerEntity(_tmpId,_tmpFirstName,_tmpSecondName,_tmpNationality,_tmpAge,_tmpClub,_tmpIsFavourite);
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
  public Object getPlayer(final String id, final Continuation<? super PlayerEntity> p1) {
    final String _sql = "SELECT * FROM players WHERE id=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    return CoroutinesRoom.execute(__db, false, new Callable<PlayerEntity>() {
      @Override
      public PlayerEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
          final int _cursorIndexOfSecondName = CursorUtil.getColumnIndexOrThrow(_cursor, "secondName");
          final int _cursorIndexOfNationality = CursorUtil.getColumnIndexOrThrow(_cursor, "nationality");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfClub = CursorUtil.getColumnIndexOrThrow(_cursor, "club");
          final int _cursorIndexOfIsFavourite = CursorUtil.getColumnIndexOrThrow(_cursor, "isFavourite");
          final PlayerEntity _result;
          if(_cursor.moveToFirst()) {
            final String _tmpId;
            _tmpId = _cursor.getString(_cursorIndexOfId);
            final String _tmpFirstName;
            _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
            final String _tmpSecondName;
            _tmpSecondName = _cursor.getString(_cursorIndexOfSecondName);
            final String _tmpNationality;
            _tmpNationality = _cursor.getString(_cursorIndexOfNationality);
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            final String _tmpClub;
            _tmpClub = _cursor.getString(_cursorIndexOfClub);
            final boolean _tmpIsFavourite;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsFavourite);
            _tmpIsFavourite = _tmp != 0;
            _result = new PlayerEntity(_tmpId,_tmpFirstName,_tmpSecondName,_tmpNationality,_tmpAge,_tmpClub,_tmpIsFavourite);
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
