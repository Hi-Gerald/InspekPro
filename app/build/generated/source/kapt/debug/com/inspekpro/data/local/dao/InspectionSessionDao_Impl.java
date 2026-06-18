package com.inspekpro.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.inspekpro.data.local.database.InspekProConverters;
import com.inspekpro.data.local.entity.InspectionSessionEntity;
import com.inspekpro.data.local.entity.SessionStatus;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class InspectionSessionDao_Impl implements InspectionSessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<InspectionSessionEntity> __insertionAdapterOfInspectionSessionEntity;

  private final InspekProConverters __inspekProConverters = new InspekProConverters();

  private final EntityDeletionOrUpdateAdapter<InspectionSessionEntity> __deletionAdapterOfInspectionSessionEntity;

  private final EntityDeletionOrUpdateAdapter<InspectionSessionEntity> __updateAdapterOfInspectionSessionEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSessionById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateSessionStatus;

  private final SharedSQLiteStatement __preparedStmtOfStartSession;

  private final SharedSQLiteStatement __preparedStmtOfCompleteSession;

  private final SharedSQLiteStatement __preparedStmtOfUpdateWeather;

  private final SharedSQLiteStatement __preparedStmtOfMarkAsSynced;

  public InspectionSessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInspectionSessionEntity = new EntityInsertionAdapter<InspectionSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `inspection_sessions` (`session_id`,`session_code`,`title`,`description`,`location_name`,`latitude`,`longitude`,`inspector_name`,`inspector_id`,`status`,`scheduled_date`,`start_time`,`end_time`,`weather_condition`,`weather_temp_celsius`,`weather_humidity`,`weather_wind_speed`,`weather_icon`,`total_items`,`passed_items`,`failed_items`,`notes`,`created_at`,`updated_at`,`is_synced`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InspectionSessionEntity entity) {
        statement.bindLong(1, entity.getSessionId());
        if (entity.getSessionCode() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getSessionCode());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        if (entity.getLocationName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLocationName());
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getLongitude());
        }
        if (entity.getInspectorName() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getInspectorName());
        }
        if (entity.getInspectorId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getInspectorId());
        }
        final String _tmp = __inspekProConverters.fromSessionStatus(entity.getStatus());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        statement.bindLong(11, entity.getScheduledDate());
        if (entity.getStartTime() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getStartTime());
        }
        if (entity.getEndTime() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getEndTime());
        }
        if (entity.getWeatherCondition() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getWeatherCondition());
        }
        if (entity.getWeatherTempCelsius() == null) {
          statement.bindNull(15);
        } else {
          statement.bindDouble(15, entity.getWeatherTempCelsius());
        }
        if (entity.getWeatherHumidity() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getWeatherHumidity());
        }
        if (entity.getWeatherWindSpeed() == null) {
          statement.bindNull(17);
        } else {
          statement.bindDouble(17, entity.getWeatherWindSpeed());
        }
        if (entity.getWeatherIcon() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getWeatherIcon());
        }
        statement.bindLong(19, entity.getTotalItems());
        statement.bindLong(20, entity.getPassedItems());
        statement.bindLong(21, entity.getFailedItems());
        if (entity.getNotes() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getNotes());
        }
        statement.bindLong(23, entity.getCreatedAt());
        statement.bindLong(24, entity.getUpdatedAt());
        final int _tmp_1 = entity.isSynced() ? 1 : 0;
        statement.bindLong(25, _tmp_1);
      }
    };
    this.__deletionAdapterOfInspectionSessionEntity = new EntityDeletionOrUpdateAdapter<InspectionSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `inspection_sessions` WHERE `session_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InspectionSessionEntity entity) {
        statement.bindLong(1, entity.getSessionId());
      }
    };
    this.__updateAdapterOfInspectionSessionEntity = new EntityDeletionOrUpdateAdapter<InspectionSessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `inspection_sessions` SET `session_id` = ?,`session_code` = ?,`title` = ?,`description` = ?,`location_name` = ?,`latitude` = ?,`longitude` = ?,`inspector_name` = ?,`inspector_id` = ?,`status` = ?,`scheduled_date` = ?,`start_time` = ?,`end_time` = ?,`weather_condition` = ?,`weather_temp_celsius` = ?,`weather_humidity` = ?,`weather_wind_speed` = ?,`weather_icon` = ?,`total_items` = ?,`passed_items` = ?,`failed_items` = ?,`notes` = ?,`created_at` = ?,`updated_at` = ?,`is_synced` = ? WHERE `session_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InspectionSessionEntity entity) {
        statement.bindLong(1, entity.getSessionId());
        if (entity.getSessionCode() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getSessionCode());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getDescription());
        }
        if (entity.getLocationName() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getLocationName());
        }
        if (entity.getLatitude() == null) {
          statement.bindNull(6);
        } else {
          statement.bindDouble(6, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getLongitude());
        }
        if (entity.getInspectorName() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getInspectorName());
        }
        if (entity.getInspectorId() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getInspectorId());
        }
        final String _tmp = __inspekProConverters.fromSessionStatus(entity.getStatus());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        statement.bindLong(11, entity.getScheduledDate());
        if (entity.getStartTime() == null) {
          statement.bindNull(12);
        } else {
          statement.bindLong(12, entity.getStartTime());
        }
        if (entity.getEndTime() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getEndTime());
        }
        if (entity.getWeatherCondition() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getWeatherCondition());
        }
        if (entity.getWeatherTempCelsius() == null) {
          statement.bindNull(15);
        } else {
          statement.bindDouble(15, entity.getWeatherTempCelsius());
        }
        if (entity.getWeatherHumidity() == null) {
          statement.bindNull(16);
        } else {
          statement.bindLong(16, entity.getWeatherHumidity());
        }
        if (entity.getWeatherWindSpeed() == null) {
          statement.bindNull(17);
        } else {
          statement.bindDouble(17, entity.getWeatherWindSpeed());
        }
        if (entity.getWeatherIcon() == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, entity.getWeatherIcon());
        }
        statement.bindLong(19, entity.getTotalItems());
        statement.bindLong(20, entity.getPassedItems());
        statement.bindLong(21, entity.getFailedItems());
        if (entity.getNotes() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getNotes());
        }
        statement.bindLong(23, entity.getCreatedAt());
        statement.bindLong(24, entity.getUpdatedAt());
        final int _tmp_1 = entity.isSynced() ? 1 : 0;
        statement.bindLong(25, _tmp_1);
        statement.bindLong(26, entity.getSessionId());
      }
    };
    this.__preparedStmtOfDeleteSessionById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM inspection_sessions WHERE session_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateSessionStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE inspection_sessions SET status = ?, updated_at = ? WHERE session_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfStartSession = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE inspection_sessions SET start_time = ?, status = 'IN_PROGRESS', updated_at = ? WHERE session_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfCompleteSession = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE inspection_sessions SET end_time = ?, status = 'COMPLETED', updated_at = ? WHERE session_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateWeather = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE inspection_sessions SET \n"
                + "            weather_condition = ?,\n"
                + "            weather_temp_celsius = ?,\n"
                + "            weather_humidity = ?,\n"
                + "            weather_wind_speed = ?,\n"
                + "            weather_icon = ?,\n"
                + "            updated_at = ?\n"
                + "        WHERE session_id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfMarkAsSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE inspection_sessions SET is_synced = 1 WHERE session_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertSession(final InspectionSessionEntity session,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfInspectionSessionEntity.insertAndReturnId(session);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteSession(final InspectionSessionEntity session,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfInspectionSessionEntity.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateSession(final InspectionSessionEntity session,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfInspectionSessionEntity.handle(session);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteSessionById(final long sessionId, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSessionById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteSessionById.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object updateSessionStatus(final long sessionId, final SessionStatus status,
      final long updatedAt, final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateSessionStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __inspekProConverters.fromSessionStatus(status);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, updatedAt);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, sessionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateSessionStatus.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object startSession(final long sessionId, final long startTime, final long now,
      final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfStartSession.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, startTime);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, sessionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfStartSession.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object completeSession(final long sessionId, final long endTime, final long now,
      final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfCompleteSession.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, endTime);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, sessionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfCompleteSession.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object updateWeather(final long sessionId, final String condition, final double tempC,
      final int humidity, final double windSpeed, final String icon, final long now,
      final Continuation<? super Unit> arg7) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateWeather.acquire();
        int _argIndex = 1;
        if (condition == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, condition);
        }
        _argIndex = 2;
        _stmt.bindDouble(_argIndex, tempC);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, humidity);
        _argIndex = 4;
        _stmt.bindDouble(_argIndex, windSpeed);
        _argIndex = 5;
        if (icon == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, icon);
        }
        _argIndex = 6;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 7;
        _stmt.bindLong(_argIndex, sessionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateWeather.release(_stmt);
        }
      }
    }, arg7);
  }

  @Override
  public Object markAsSynced(final long sessionId, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkAsSynced.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, sessionId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkAsSynced.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<InspectionSessionEntity>> getAllSessions() {
    final String _sql = "SELECT * FROM inspection_sessions ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_sessions"}, new Callable<List<InspectionSessionEntity>>() {
      @Override
      @NonNull
      public List<InspectionSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSessionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "session_code");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "location_name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfInspectorName = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_name");
          final int _cursorIndexOfInspectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledDate = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduled_date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
          final int _cursorIndexOfWeatherCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_condition");
          final int _cursorIndexOfWeatherTempCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_temp_celsius");
          final int _cursorIndexOfWeatherHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_humidity");
          final int _cursorIndexOfWeatherWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_wind_speed");
          final int _cursorIndexOfWeatherIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_icon");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "total_items");
          final int _cursorIndexOfPassedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "passed_items");
          final int _cursorIndexOfFailedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "failed_items");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "is_synced");
          final List<InspectionSessionEntity> _result = new ArrayList<InspectionSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionSessionEntity _item;
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSessionCode;
            if (_cursor.isNull(_cursorIndexOfSessionCode)) {
              _tmpSessionCode = null;
            } else {
              _tmpSessionCode = _cursor.getString(_cursorIndexOfSessionCode);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpInspectorName;
            if (_cursor.isNull(_cursorIndexOfInspectorName)) {
              _tmpInspectorName = null;
            } else {
              _tmpInspectorName = _cursor.getString(_cursorIndexOfInspectorName);
            }
            final String _tmpInspectorId;
            if (_cursor.isNull(_cursorIndexOfInspectorId)) {
              _tmpInspectorId = null;
            } else {
              _tmpInspectorId = _cursor.getString(_cursorIndexOfInspectorId);
            }
            final SessionStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toSessionStatus(_tmp);
            final long _tmpScheduledDate;
            _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
            final Long _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final String _tmpWeatherCondition;
            if (_cursor.isNull(_cursorIndexOfWeatherCondition)) {
              _tmpWeatherCondition = null;
            } else {
              _tmpWeatherCondition = _cursor.getString(_cursorIndexOfWeatherCondition);
            }
            final Double _tmpWeatherTempCelsius;
            if (_cursor.isNull(_cursorIndexOfWeatherTempCelsius)) {
              _tmpWeatherTempCelsius = null;
            } else {
              _tmpWeatherTempCelsius = _cursor.getDouble(_cursorIndexOfWeatherTempCelsius);
            }
            final Integer _tmpWeatherHumidity;
            if (_cursor.isNull(_cursorIndexOfWeatherHumidity)) {
              _tmpWeatherHumidity = null;
            } else {
              _tmpWeatherHumidity = _cursor.getInt(_cursorIndexOfWeatherHumidity);
            }
            final Double _tmpWeatherWindSpeed;
            if (_cursor.isNull(_cursorIndexOfWeatherWindSpeed)) {
              _tmpWeatherWindSpeed = null;
            } else {
              _tmpWeatherWindSpeed = _cursor.getDouble(_cursorIndexOfWeatherWindSpeed);
            }
            final String _tmpWeatherIcon;
            if (_cursor.isNull(_cursorIndexOfWeatherIcon)) {
              _tmpWeatherIcon = null;
            } else {
              _tmpWeatherIcon = _cursor.getString(_cursorIndexOfWeatherIcon);
            }
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpPassedItems;
            _tmpPassedItems = _cursor.getInt(_cursorIndexOfPassedItems);
            final int _tmpFailedItems;
            _tmpFailedItems = _cursor.getInt(_cursorIndexOfFailedItems);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            _item = new InspectionSessionEntity(_tmpSessionId,_tmpSessionCode,_tmpTitle,_tmpDescription,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpInspectorName,_tmpInspectorId,_tmpStatus,_tmpScheduledDate,_tmpStartTime,_tmpEndTime,_tmpWeatherCondition,_tmpWeatherTempCelsius,_tmpWeatherHumidity,_tmpWeatherWindSpeed,_tmpWeatherIcon,_tmpTotalItems,_tmpPassedItems,_tmpFailedItems,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<InspectionSessionEntity> getSessionById(final long sessionId) {
    final String _sql = "SELECT * FROM inspection_sessions WHERE session_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_sessions"}, new Callable<InspectionSessionEntity>() {
      @Override
      @Nullable
      public InspectionSessionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSessionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "session_code");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "location_name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfInspectorName = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_name");
          final int _cursorIndexOfInspectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledDate = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduled_date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
          final int _cursorIndexOfWeatherCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_condition");
          final int _cursorIndexOfWeatherTempCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_temp_celsius");
          final int _cursorIndexOfWeatherHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_humidity");
          final int _cursorIndexOfWeatherWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_wind_speed");
          final int _cursorIndexOfWeatherIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_icon");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "total_items");
          final int _cursorIndexOfPassedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "passed_items");
          final int _cursorIndexOfFailedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "failed_items");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "is_synced");
          final InspectionSessionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSessionCode;
            if (_cursor.isNull(_cursorIndexOfSessionCode)) {
              _tmpSessionCode = null;
            } else {
              _tmpSessionCode = _cursor.getString(_cursorIndexOfSessionCode);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpInspectorName;
            if (_cursor.isNull(_cursorIndexOfInspectorName)) {
              _tmpInspectorName = null;
            } else {
              _tmpInspectorName = _cursor.getString(_cursorIndexOfInspectorName);
            }
            final String _tmpInspectorId;
            if (_cursor.isNull(_cursorIndexOfInspectorId)) {
              _tmpInspectorId = null;
            } else {
              _tmpInspectorId = _cursor.getString(_cursorIndexOfInspectorId);
            }
            final SessionStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toSessionStatus(_tmp);
            final long _tmpScheduledDate;
            _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
            final Long _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final String _tmpWeatherCondition;
            if (_cursor.isNull(_cursorIndexOfWeatherCondition)) {
              _tmpWeatherCondition = null;
            } else {
              _tmpWeatherCondition = _cursor.getString(_cursorIndexOfWeatherCondition);
            }
            final Double _tmpWeatherTempCelsius;
            if (_cursor.isNull(_cursorIndexOfWeatherTempCelsius)) {
              _tmpWeatherTempCelsius = null;
            } else {
              _tmpWeatherTempCelsius = _cursor.getDouble(_cursorIndexOfWeatherTempCelsius);
            }
            final Integer _tmpWeatherHumidity;
            if (_cursor.isNull(_cursorIndexOfWeatherHumidity)) {
              _tmpWeatherHumidity = null;
            } else {
              _tmpWeatherHumidity = _cursor.getInt(_cursorIndexOfWeatherHumidity);
            }
            final Double _tmpWeatherWindSpeed;
            if (_cursor.isNull(_cursorIndexOfWeatherWindSpeed)) {
              _tmpWeatherWindSpeed = null;
            } else {
              _tmpWeatherWindSpeed = _cursor.getDouble(_cursorIndexOfWeatherWindSpeed);
            }
            final String _tmpWeatherIcon;
            if (_cursor.isNull(_cursorIndexOfWeatherIcon)) {
              _tmpWeatherIcon = null;
            } else {
              _tmpWeatherIcon = _cursor.getString(_cursorIndexOfWeatherIcon);
            }
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpPassedItems;
            _tmpPassedItems = _cursor.getInt(_cursorIndexOfPassedItems);
            final int _tmpFailedItems;
            _tmpFailedItems = _cursor.getInt(_cursorIndexOfFailedItems);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            _result = new InspectionSessionEntity(_tmpSessionId,_tmpSessionCode,_tmpTitle,_tmpDescription,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpInspectorName,_tmpInspectorId,_tmpStatus,_tmpScheduledDate,_tmpStartTime,_tmpEndTime,_tmpWeatherCondition,_tmpWeatherTempCelsius,_tmpWeatherHumidity,_tmpWeatherWindSpeed,_tmpWeatherIcon,_tmpTotalItems,_tmpPassedItems,_tmpFailedItems,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getSessionByIdOnce(final long sessionId,
      final Continuation<? super InspectionSessionEntity> arg1) {
    final String _sql = "SELECT * FROM inspection_sessions WHERE session_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<InspectionSessionEntity>() {
      @Override
      @Nullable
      public InspectionSessionEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSessionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "session_code");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "location_name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfInspectorName = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_name");
          final int _cursorIndexOfInspectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledDate = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduled_date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
          final int _cursorIndexOfWeatherCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_condition");
          final int _cursorIndexOfWeatherTempCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_temp_celsius");
          final int _cursorIndexOfWeatherHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_humidity");
          final int _cursorIndexOfWeatherWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_wind_speed");
          final int _cursorIndexOfWeatherIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_icon");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "total_items");
          final int _cursorIndexOfPassedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "passed_items");
          final int _cursorIndexOfFailedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "failed_items");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "is_synced");
          final InspectionSessionEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSessionCode;
            if (_cursor.isNull(_cursorIndexOfSessionCode)) {
              _tmpSessionCode = null;
            } else {
              _tmpSessionCode = _cursor.getString(_cursorIndexOfSessionCode);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpInspectorName;
            if (_cursor.isNull(_cursorIndexOfInspectorName)) {
              _tmpInspectorName = null;
            } else {
              _tmpInspectorName = _cursor.getString(_cursorIndexOfInspectorName);
            }
            final String _tmpInspectorId;
            if (_cursor.isNull(_cursorIndexOfInspectorId)) {
              _tmpInspectorId = null;
            } else {
              _tmpInspectorId = _cursor.getString(_cursorIndexOfInspectorId);
            }
            final SessionStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toSessionStatus(_tmp);
            final long _tmpScheduledDate;
            _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
            final Long _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final String _tmpWeatherCondition;
            if (_cursor.isNull(_cursorIndexOfWeatherCondition)) {
              _tmpWeatherCondition = null;
            } else {
              _tmpWeatherCondition = _cursor.getString(_cursorIndexOfWeatherCondition);
            }
            final Double _tmpWeatherTempCelsius;
            if (_cursor.isNull(_cursorIndexOfWeatherTempCelsius)) {
              _tmpWeatherTempCelsius = null;
            } else {
              _tmpWeatherTempCelsius = _cursor.getDouble(_cursorIndexOfWeatherTempCelsius);
            }
            final Integer _tmpWeatherHumidity;
            if (_cursor.isNull(_cursorIndexOfWeatherHumidity)) {
              _tmpWeatherHumidity = null;
            } else {
              _tmpWeatherHumidity = _cursor.getInt(_cursorIndexOfWeatherHumidity);
            }
            final Double _tmpWeatherWindSpeed;
            if (_cursor.isNull(_cursorIndexOfWeatherWindSpeed)) {
              _tmpWeatherWindSpeed = null;
            } else {
              _tmpWeatherWindSpeed = _cursor.getDouble(_cursorIndexOfWeatherWindSpeed);
            }
            final String _tmpWeatherIcon;
            if (_cursor.isNull(_cursorIndexOfWeatherIcon)) {
              _tmpWeatherIcon = null;
            } else {
              _tmpWeatherIcon = _cursor.getString(_cursorIndexOfWeatherIcon);
            }
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpPassedItems;
            _tmpPassedItems = _cursor.getInt(_cursorIndexOfPassedItems);
            final int _tmpFailedItems;
            _tmpFailedItems = _cursor.getInt(_cursorIndexOfFailedItems);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            _result = new InspectionSessionEntity(_tmpSessionId,_tmpSessionCode,_tmpTitle,_tmpDescription,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpInspectorName,_tmpInspectorId,_tmpStatus,_tmpScheduledDate,_tmpStartTime,_tmpEndTime,_tmpWeatherCondition,_tmpWeatherTempCelsius,_tmpWeatherHumidity,_tmpWeatherWindSpeed,_tmpWeatherIcon,_tmpTotalItems,_tmpPassedItems,_tmpFailedItems,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg1);
  }

  @Override
  public Flow<List<InspectionSessionEntity>> getSessionsByStatus(final SessionStatus status) {
    final String _sql = "SELECT * FROM inspection_sessions WHERE status = ? ORDER BY scheduled_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __inspekProConverters.fromSessionStatus(status);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_sessions"}, new Callable<List<InspectionSessionEntity>>() {
      @Override
      @NonNull
      public List<InspectionSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSessionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "session_code");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "location_name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfInspectorName = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_name");
          final int _cursorIndexOfInspectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledDate = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduled_date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
          final int _cursorIndexOfWeatherCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_condition");
          final int _cursorIndexOfWeatherTempCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_temp_celsius");
          final int _cursorIndexOfWeatherHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_humidity");
          final int _cursorIndexOfWeatherWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_wind_speed");
          final int _cursorIndexOfWeatherIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_icon");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "total_items");
          final int _cursorIndexOfPassedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "passed_items");
          final int _cursorIndexOfFailedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "failed_items");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "is_synced");
          final List<InspectionSessionEntity> _result = new ArrayList<InspectionSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionSessionEntity _item;
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSessionCode;
            if (_cursor.isNull(_cursorIndexOfSessionCode)) {
              _tmpSessionCode = null;
            } else {
              _tmpSessionCode = _cursor.getString(_cursorIndexOfSessionCode);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpInspectorName;
            if (_cursor.isNull(_cursorIndexOfInspectorName)) {
              _tmpInspectorName = null;
            } else {
              _tmpInspectorName = _cursor.getString(_cursorIndexOfInspectorName);
            }
            final String _tmpInspectorId;
            if (_cursor.isNull(_cursorIndexOfInspectorId)) {
              _tmpInspectorId = null;
            } else {
              _tmpInspectorId = _cursor.getString(_cursorIndexOfInspectorId);
            }
            final SessionStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toSessionStatus(_tmp_1);
            final long _tmpScheduledDate;
            _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
            final Long _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final String _tmpWeatherCondition;
            if (_cursor.isNull(_cursorIndexOfWeatherCondition)) {
              _tmpWeatherCondition = null;
            } else {
              _tmpWeatherCondition = _cursor.getString(_cursorIndexOfWeatherCondition);
            }
            final Double _tmpWeatherTempCelsius;
            if (_cursor.isNull(_cursorIndexOfWeatherTempCelsius)) {
              _tmpWeatherTempCelsius = null;
            } else {
              _tmpWeatherTempCelsius = _cursor.getDouble(_cursorIndexOfWeatherTempCelsius);
            }
            final Integer _tmpWeatherHumidity;
            if (_cursor.isNull(_cursorIndexOfWeatherHumidity)) {
              _tmpWeatherHumidity = null;
            } else {
              _tmpWeatherHumidity = _cursor.getInt(_cursorIndexOfWeatherHumidity);
            }
            final Double _tmpWeatherWindSpeed;
            if (_cursor.isNull(_cursorIndexOfWeatherWindSpeed)) {
              _tmpWeatherWindSpeed = null;
            } else {
              _tmpWeatherWindSpeed = _cursor.getDouble(_cursorIndexOfWeatherWindSpeed);
            }
            final String _tmpWeatherIcon;
            if (_cursor.isNull(_cursorIndexOfWeatherIcon)) {
              _tmpWeatherIcon = null;
            } else {
              _tmpWeatherIcon = _cursor.getString(_cursorIndexOfWeatherIcon);
            }
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpPassedItems;
            _tmpPassedItems = _cursor.getInt(_cursorIndexOfPassedItems);
            final int _tmpFailedItems;
            _tmpFailedItems = _cursor.getInt(_cursorIndexOfFailedItems);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_2 != 0;
            _item = new InspectionSessionEntity(_tmpSessionId,_tmpSessionCode,_tmpTitle,_tmpDescription,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpInspectorName,_tmpInspectorId,_tmpStatus,_tmpScheduledDate,_tmpStartTime,_tmpEndTime,_tmpWeatherCondition,_tmpWeatherTempCelsius,_tmpWeatherHumidity,_tmpWeatherWindSpeed,_tmpWeatherIcon,_tmpTotalItems,_tmpPassedItems,_tmpFailedItems,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<InspectionSessionEntity>> getSessionsByInspector(final String inspectorId) {
    final String _sql = "SELECT * FROM inspection_sessions WHERE inspector_id = ? ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (inspectorId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, inspectorId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_sessions"}, new Callable<List<InspectionSessionEntity>>() {
      @Override
      @NonNull
      public List<InspectionSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSessionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "session_code");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "location_name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfInspectorName = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_name");
          final int _cursorIndexOfInspectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledDate = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduled_date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
          final int _cursorIndexOfWeatherCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_condition");
          final int _cursorIndexOfWeatherTempCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_temp_celsius");
          final int _cursorIndexOfWeatherHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_humidity");
          final int _cursorIndexOfWeatherWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_wind_speed");
          final int _cursorIndexOfWeatherIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_icon");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "total_items");
          final int _cursorIndexOfPassedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "passed_items");
          final int _cursorIndexOfFailedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "failed_items");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "is_synced");
          final List<InspectionSessionEntity> _result = new ArrayList<InspectionSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionSessionEntity _item;
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSessionCode;
            if (_cursor.isNull(_cursorIndexOfSessionCode)) {
              _tmpSessionCode = null;
            } else {
              _tmpSessionCode = _cursor.getString(_cursorIndexOfSessionCode);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpInspectorName;
            if (_cursor.isNull(_cursorIndexOfInspectorName)) {
              _tmpInspectorName = null;
            } else {
              _tmpInspectorName = _cursor.getString(_cursorIndexOfInspectorName);
            }
            final String _tmpInspectorId;
            if (_cursor.isNull(_cursorIndexOfInspectorId)) {
              _tmpInspectorId = null;
            } else {
              _tmpInspectorId = _cursor.getString(_cursorIndexOfInspectorId);
            }
            final SessionStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toSessionStatus(_tmp);
            final long _tmpScheduledDate;
            _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
            final Long _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final String _tmpWeatherCondition;
            if (_cursor.isNull(_cursorIndexOfWeatherCondition)) {
              _tmpWeatherCondition = null;
            } else {
              _tmpWeatherCondition = _cursor.getString(_cursorIndexOfWeatherCondition);
            }
            final Double _tmpWeatherTempCelsius;
            if (_cursor.isNull(_cursorIndexOfWeatherTempCelsius)) {
              _tmpWeatherTempCelsius = null;
            } else {
              _tmpWeatherTempCelsius = _cursor.getDouble(_cursorIndexOfWeatherTempCelsius);
            }
            final Integer _tmpWeatherHumidity;
            if (_cursor.isNull(_cursorIndexOfWeatherHumidity)) {
              _tmpWeatherHumidity = null;
            } else {
              _tmpWeatherHumidity = _cursor.getInt(_cursorIndexOfWeatherHumidity);
            }
            final Double _tmpWeatherWindSpeed;
            if (_cursor.isNull(_cursorIndexOfWeatherWindSpeed)) {
              _tmpWeatherWindSpeed = null;
            } else {
              _tmpWeatherWindSpeed = _cursor.getDouble(_cursorIndexOfWeatherWindSpeed);
            }
            final String _tmpWeatherIcon;
            if (_cursor.isNull(_cursorIndexOfWeatherIcon)) {
              _tmpWeatherIcon = null;
            } else {
              _tmpWeatherIcon = _cursor.getString(_cursorIndexOfWeatherIcon);
            }
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpPassedItems;
            _tmpPassedItems = _cursor.getInt(_cursorIndexOfPassedItems);
            final int _tmpFailedItems;
            _tmpFailedItems = _cursor.getInt(_cursorIndexOfFailedItems);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            _item = new InspectionSessionEntity(_tmpSessionId,_tmpSessionCode,_tmpTitle,_tmpDescription,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpInspectorName,_tmpInspectorId,_tmpStatus,_tmpScheduledDate,_tmpStartTime,_tmpEndTime,_tmpWeatherCondition,_tmpWeatherTempCelsius,_tmpWeatherHumidity,_tmpWeatherWindSpeed,_tmpWeatherIcon,_tmpTotalItems,_tmpPassedItems,_tmpFailedItems,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<InspectionSessionEntity>> getSessionsByDateRange(final long startDate,
      final long endDate) {
    final String _sql = "\n"
            + "        SELECT * FROM inspection_sessions \n"
            + "        WHERE scheduled_date BETWEEN ? AND ? \n"
            + "        ORDER BY scheduled_date ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startDate);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endDate);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_sessions"}, new Callable<List<InspectionSessionEntity>>() {
      @Override
      @NonNull
      public List<InspectionSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSessionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "session_code");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "location_name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfInspectorName = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_name");
          final int _cursorIndexOfInspectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledDate = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduled_date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
          final int _cursorIndexOfWeatherCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_condition");
          final int _cursorIndexOfWeatherTempCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_temp_celsius");
          final int _cursorIndexOfWeatherHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_humidity");
          final int _cursorIndexOfWeatherWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_wind_speed");
          final int _cursorIndexOfWeatherIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_icon");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "total_items");
          final int _cursorIndexOfPassedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "passed_items");
          final int _cursorIndexOfFailedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "failed_items");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "is_synced");
          final List<InspectionSessionEntity> _result = new ArrayList<InspectionSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionSessionEntity _item;
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSessionCode;
            if (_cursor.isNull(_cursorIndexOfSessionCode)) {
              _tmpSessionCode = null;
            } else {
              _tmpSessionCode = _cursor.getString(_cursorIndexOfSessionCode);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpInspectorName;
            if (_cursor.isNull(_cursorIndexOfInspectorName)) {
              _tmpInspectorName = null;
            } else {
              _tmpInspectorName = _cursor.getString(_cursorIndexOfInspectorName);
            }
            final String _tmpInspectorId;
            if (_cursor.isNull(_cursorIndexOfInspectorId)) {
              _tmpInspectorId = null;
            } else {
              _tmpInspectorId = _cursor.getString(_cursorIndexOfInspectorId);
            }
            final SessionStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toSessionStatus(_tmp);
            final long _tmpScheduledDate;
            _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
            final Long _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final String _tmpWeatherCondition;
            if (_cursor.isNull(_cursorIndexOfWeatherCondition)) {
              _tmpWeatherCondition = null;
            } else {
              _tmpWeatherCondition = _cursor.getString(_cursorIndexOfWeatherCondition);
            }
            final Double _tmpWeatherTempCelsius;
            if (_cursor.isNull(_cursorIndexOfWeatherTempCelsius)) {
              _tmpWeatherTempCelsius = null;
            } else {
              _tmpWeatherTempCelsius = _cursor.getDouble(_cursorIndexOfWeatherTempCelsius);
            }
            final Integer _tmpWeatherHumidity;
            if (_cursor.isNull(_cursorIndexOfWeatherHumidity)) {
              _tmpWeatherHumidity = null;
            } else {
              _tmpWeatherHumidity = _cursor.getInt(_cursorIndexOfWeatherHumidity);
            }
            final Double _tmpWeatherWindSpeed;
            if (_cursor.isNull(_cursorIndexOfWeatherWindSpeed)) {
              _tmpWeatherWindSpeed = null;
            } else {
              _tmpWeatherWindSpeed = _cursor.getDouble(_cursorIndexOfWeatherWindSpeed);
            }
            final String _tmpWeatherIcon;
            if (_cursor.isNull(_cursorIndexOfWeatherIcon)) {
              _tmpWeatherIcon = null;
            } else {
              _tmpWeatherIcon = _cursor.getString(_cursorIndexOfWeatherIcon);
            }
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpPassedItems;
            _tmpPassedItems = _cursor.getInt(_cursorIndexOfPassedItems);
            final int _tmpFailedItems;
            _tmpFailedItems = _cursor.getInt(_cursorIndexOfFailedItems);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            _item = new InspectionSessionEntity(_tmpSessionId,_tmpSessionCode,_tmpTitle,_tmpDescription,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpInspectorName,_tmpInspectorId,_tmpStatus,_tmpScheduledDate,_tmpStartTime,_tmpEndTime,_tmpWeatherCondition,_tmpWeatherTempCelsius,_tmpWeatherHumidity,_tmpWeatherWindSpeed,_tmpWeatherIcon,_tmpTotalItems,_tmpPassedItems,_tmpFailedItems,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<InspectionSessionEntity>> searchSessions(final String query) {
    final String _sql = "\n"
            + "        SELECT * FROM inspection_sessions \n"
            + "        WHERE title LIKE '%' || ? || '%' \n"
            + "           OR location_name LIKE '%' || ? || '%'\n"
            + "           OR session_code LIKE '%' || ? || '%'\n"
            + "        ORDER BY created_at DESC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 3;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_sessions"}, new Callable<List<InspectionSessionEntity>>() {
      @Override
      @NonNull
      public List<InspectionSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSessionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "session_code");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "location_name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfInspectorName = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_name");
          final int _cursorIndexOfInspectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledDate = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduled_date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
          final int _cursorIndexOfWeatherCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_condition");
          final int _cursorIndexOfWeatherTempCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_temp_celsius");
          final int _cursorIndexOfWeatherHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_humidity");
          final int _cursorIndexOfWeatherWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_wind_speed");
          final int _cursorIndexOfWeatherIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_icon");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "total_items");
          final int _cursorIndexOfPassedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "passed_items");
          final int _cursorIndexOfFailedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "failed_items");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "is_synced");
          final List<InspectionSessionEntity> _result = new ArrayList<InspectionSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionSessionEntity _item;
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSessionCode;
            if (_cursor.isNull(_cursorIndexOfSessionCode)) {
              _tmpSessionCode = null;
            } else {
              _tmpSessionCode = _cursor.getString(_cursorIndexOfSessionCode);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpInspectorName;
            if (_cursor.isNull(_cursorIndexOfInspectorName)) {
              _tmpInspectorName = null;
            } else {
              _tmpInspectorName = _cursor.getString(_cursorIndexOfInspectorName);
            }
            final String _tmpInspectorId;
            if (_cursor.isNull(_cursorIndexOfInspectorId)) {
              _tmpInspectorId = null;
            } else {
              _tmpInspectorId = _cursor.getString(_cursorIndexOfInspectorId);
            }
            final SessionStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toSessionStatus(_tmp);
            final long _tmpScheduledDate;
            _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
            final Long _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final String _tmpWeatherCondition;
            if (_cursor.isNull(_cursorIndexOfWeatherCondition)) {
              _tmpWeatherCondition = null;
            } else {
              _tmpWeatherCondition = _cursor.getString(_cursorIndexOfWeatherCondition);
            }
            final Double _tmpWeatherTempCelsius;
            if (_cursor.isNull(_cursorIndexOfWeatherTempCelsius)) {
              _tmpWeatherTempCelsius = null;
            } else {
              _tmpWeatherTempCelsius = _cursor.getDouble(_cursorIndexOfWeatherTempCelsius);
            }
            final Integer _tmpWeatherHumidity;
            if (_cursor.isNull(_cursorIndexOfWeatherHumidity)) {
              _tmpWeatherHumidity = null;
            } else {
              _tmpWeatherHumidity = _cursor.getInt(_cursorIndexOfWeatherHumidity);
            }
            final Double _tmpWeatherWindSpeed;
            if (_cursor.isNull(_cursorIndexOfWeatherWindSpeed)) {
              _tmpWeatherWindSpeed = null;
            } else {
              _tmpWeatherWindSpeed = _cursor.getDouble(_cursorIndexOfWeatherWindSpeed);
            }
            final String _tmpWeatherIcon;
            if (_cursor.isNull(_cursorIndexOfWeatherIcon)) {
              _tmpWeatherIcon = null;
            } else {
              _tmpWeatherIcon = _cursor.getString(_cursorIndexOfWeatherIcon);
            }
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpPassedItems;
            _tmpPassedItems = _cursor.getInt(_cursorIndexOfPassedItems);
            final int _tmpFailedItems;
            _tmpFailedItems = _cursor.getInt(_cursorIndexOfFailedItems);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            _item = new InspectionSessionEntity(_tmpSessionId,_tmpSessionCode,_tmpTitle,_tmpDescription,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpInspectorName,_tmpInspectorId,_tmpStatus,_tmpScheduledDate,_tmpStartTime,_tmpEndTime,_tmpWeatherCondition,_tmpWeatherTempCelsius,_tmpWeatherHumidity,_tmpWeatherWindSpeed,_tmpWeatherIcon,_tmpTotalItems,_tmpPassedItems,_tmpFailedItems,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getTotalSessionCount() {
    final String _sql = "SELECT COUNT(*) FROM inspection_sessions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_sessions"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getSessionCountByStatus(final SessionStatus status) {
    final String _sql = "SELECT COUNT(*) FROM inspection_sessions WHERE status = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __inspekProConverters.fromSessionStatus(status);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_sessions"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp_1;
            if (_cursor.isNull(0)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getInt(0);
            }
            _result = _tmp_1;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUnsyncedSessions(
      final Continuation<? super List<InspectionSessionEntity>> arg0) {
    final String _sql = "SELECT * FROM inspection_sessions WHERE is_synced = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<InspectionSessionEntity>>() {
      @Override
      @NonNull
      public List<InspectionSessionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfSessionCode = CursorUtil.getColumnIndexOrThrow(_cursor, "session_code");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfLocationName = CursorUtil.getColumnIndexOrThrow(_cursor, "location_name");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final int _cursorIndexOfInspectorName = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_name");
          final int _cursorIndexOfInspectorId = CursorUtil.getColumnIndexOrThrow(_cursor, "inspector_id");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfScheduledDate = CursorUtil.getColumnIndexOrThrow(_cursor, "scheduled_date");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "start_time");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "end_time");
          final int _cursorIndexOfWeatherCondition = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_condition");
          final int _cursorIndexOfWeatherTempCelsius = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_temp_celsius");
          final int _cursorIndexOfWeatherHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_humidity");
          final int _cursorIndexOfWeatherWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_wind_speed");
          final int _cursorIndexOfWeatherIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "weather_icon");
          final int _cursorIndexOfTotalItems = CursorUtil.getColumnIndexOrThrow(_cursor, "total_items");
          final int _cursorIndexOfPassedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "passed_items");
          final int _cursorIndexOfFailedItems = CursorUtil.getColumnIndexOrThrow(_cursor, "failed_items");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "is_synced");
          final List<InspectionSessionEntity> _result = new ArrayList<InspectionSessionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionSessionEntity _item;
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final String _tmpSessionCode;
            if (_cursor.isNull(_cursorIndexOfSessionCode)) {
              _tmpSessionCode = null;
            } else {
              _tmpSessionCode = _cursor.getString(_cursorIndexOfSessionCode);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpLocationName;
            if (_cursor.isNull(_cursorIndexOfLocationName)) {
              _tmpLocationName = null;
            } else {
              _tmpLocationName = _cursor.getString(_cursorIndexOfLocationName);
            }
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            final String _tmpInspectorName;
            if (_cursor.isNull(_cursorIndexOfInspectorName)) {
              _tmpInspectorName = null;
            } else {
              _tmpInspectorName = _cursor.getString(_cursorIndexOfInspectorName);
            }
            final String _tmpInspectorId;
            if (_cursor.isNull(_cursorIndexOfInspectorId)) {
              _tmpInspectorId = null;
            } else {
              _tmpInspectorId = _cursor.getString(_cursorIndexOfInspectorId);
            }
            final SessionStatus _tmpStatus;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toSessionStatus(_tmp);
            final long _tmpScheduledDate;
            _tmpScheduledDate = _cursor.getLong(_cursorIndexOfScheduledDate);
            final Long _tmpStartTime;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmpStartTime = null;
            } else {
              _tmpStartTime = _cursor.getLong(_cursorIndexOfStartTime);
            }
            final Long _tmpEndTime;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmpEndTime = null;
            } else {
              _tmpEndTime = _cursor.getLong(_cursorIndexOfEndTime);
            }
            final String _tmpWeatherCondition;
            if (_cursor.isNull(_cursorIndexOfWeatherCondition)) {
              _tmpWeatherCondition = null;
            } else {
              _tmpWeatherCondition = _cursor.getString(_cursorIndexOfWeatherCondition);
            }
            final Double _tmpWeatherTempCelsius;
            if (_cursor.isNull(_cursorIndexOfWeatherTempCelsius)) {
              _tmpWeatherTempCelsius = null;
            } else {
              _tmpWeatherTempCelsius = _cursor.getDouble(_cursorIndexOfWeatherTempCelsius);
            }
            final Integer _tmpWeatherHumidity;
            if (_cursor.isNull(_cursorIndexOfWeatherHumidity)) {
              _tmpWeatherHumidity = null;
            } else {
              _tmpWeatherHumidity = _cursor.getInt(_cursorIndexOfWeatherHumidity);
            }
            final Double _tmpWeatherWindSpeed;
            if (_cursor.isNull(_cursorIndexOfWeatherWindSpeed)) {
              _tmpWeatherWindSpeed = null;
            } else {
              _tmpWeatherWindSpeed = _cursor.getDouble(_cursorIndexOfWeatherWindSpeed);
            }
            final String _tmpWeatherIcon;
            if (_cursor.isNull(_cursorIndexOfWeatherIcon)) {
              _tmpWeatherIcon = null;
            } else {
              _tmpWeatherIcon = _cursor.getString(_cursorIndexOfWeatherIcon);
            }
            final int _tmpTotalItems;
            _tmpTotalItems = _cursor.getInt(_cursorIndexOfTotalItems);
            final int _tmpPassedItems;
            _tmpPassedItems = _cursor.getInt(_cursorIndexOfPassedItems);
            final int _tmpFailedItems;
            _tmpFailedItems = _cursor.getInt(_cursorIndexOfFailedItems);
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            final boolean _tmpIsSynced;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsSynced);
            _tmpIsSynced = _tmp_1 != 0;
            _item = new InspectionSessionEntity(_tmpSessionId,_tmpSessionCode,_tmpTitle,_tmpDescription,_tmpLocationName,_tmpLatitude,_tmpLongitude,_tmpInspectorName,_tmpInspectorId,_tmpStatus,_tmpScheduledDate,_tmpStartTime,_tmpEndTime,_tmpWeatherCondition,_tmpWeatherTempCelsius,_tmpWeatherHumidity,_tmpWeatherWindSpeed,_tmpWeatherIcon,_tmpTotalItems,_tmpPassedItems,_tmpFailedItems,_tmpNotes,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, arg0);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
