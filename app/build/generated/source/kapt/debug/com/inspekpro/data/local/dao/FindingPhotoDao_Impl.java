package com.inspekpro.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.inspekpro.data.local.entity.FindingPhotoEntity;
import java.lang.Class;
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
public final class FindingPhotoDao_Impl implements FindingPhotoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FindingPhotoEntity> __insertionAdapterOfFindingPhotoEntity;

  private final EntityDeletionOrUpdateAdapter<FindingPhotoEntity> __deletionAdapterOfFindingPhotoEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeletePhotoById;

  private final SharedSQLiteStatement __preparedStmtOfMarkPhotoUploaded;

  public FindingPhotoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFindingPhotoEntity = new EntityInsertionAdapter<FindingPhotoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `finding_photos` (`photo_id`,`finding_id`,`local_path`,`remote_url`,`caption`,`is_uploaded`,`file_size_bytes`,`taken_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FindingPhotoEntity entity) {
        statement.bindLong(1, entity.getPhotoId());
        statement.bindLong(2, entity.getFindingId());
        if (entity.getLocalPath() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLocalPath());
        }
        if (entity.getRemoteUrl() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getRemoteUrl());
        }
        if (entity.getCaption() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCaption());
        }
        final int _tmp = entity.isUploaded() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getFileSizeBytes());
        statement.bindLong(8, entity.getTakenAt());
      }
    };
    this.__deletionAdapterOfFindingPhotoEntity = new EntityDeletionOrUpdateAdapter<FindingPhotoEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `finding_photos` WHERE `photo_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FindingPhotoEntity entity) {
        statement.bindLong(1, entity.getPhotoId());
      }
    };
    this.__preparedStmtOfDeletePhotoById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM finding_photos WHERE photo_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkPhotoUploaded = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE finding_photos SET is_uploaded = 1, remote_url = ? WHERE photo_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertPhoto(final FindingPhotoEntity photo,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfFindingPhotoEntity.insertAndReturnId(photo);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertPhotos(final List<FindingPhotoEntity> photos,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFindingPhotoEntity.insert(photos);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePhoto(final FindingPhotoEntity photo,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFindingPhotoEntity.handle(photo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deletePhotoById(final long photoId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePhotoById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, photoId);
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
          __preparedStmtOfDeletePhotoById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markPhotoUploaded(final long photoId, final String url,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkPhotoUploaded.acquire();
        int _argIndex = 1;
        if (url == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, url);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, photoId);
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
          __preparedStmtOfMarkPhotoUploaded.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FindingPhotoEntity>> getPhotosByFinding(final long findingId) {
    final String _sql = "SELECT * FROM finding_photos WHERE finding_id = ? ORDER BY taken_at ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, findingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"finding_photos"}, new Callable<List<FindingPhotoEntity>>() {
      @Override
      @NonNull
      public List<FindingPhotoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPhotoId = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_id");
          final int _cursorIndexOfFindingId = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_id");
          final int _cursorIndexOfLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "local_path");
          final int _cursorIndexOfRemoteUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "remote_url");
          final int _cursorIndexOfCaption = CursorUtil.getColumnIndexOrThrow(_cursor, "caption");
          final int _cursorIndexOfIsUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "is_uploaded");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size_bytes");
          final int _cursorIndexOfTakenAt = CursorUtil.getColumnIndexOrThrow(_cursor, "taken_at");
          final List<FindingPhotoEntity> _result = new ArrayList<FindingPhotoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FindingPhotoEntity _item;
            final long _tmpPhotoId;
            _tmpPhotoId = _cursor.getLong(_cursorIndexOfPhotoId);
            final long _tmpFindingId;
            _tmpFindingId = _cursor.getLong(_cursorIndexOfFindingId);
            final String _tmpLocalPath;
            if (_cursor.isNull(_cursorIndexOfLocalPath)) {
              _tmpLocalPath = null;
            } else {
              _tmpLocalPath = _cursor.getString(_cursorIndexOfLocalPath);
            }
            final String _tmpRemoteUrl;
            if (_cursor.isNull(_cursorIndexOfRemoteUrl)) {
              _tmpRemoteUrl = null;
            } else {
              _tmpRemoteUrl = _cursor.getString(_cursorIndexOfRemoteUrl);
            }
            final String _tmpCaption;
            if (_cursor.isNull(_cursorIndexOfCaption)) {
              _tmpCaption = null;
            } else {
              _tmpCaption = _cursor.getString(_cursorIndexOfCaption);
            }
            final boolean _tmpIsUploaded;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUploaded);
            _tmpIsUploaded = _tmp != 0;
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            final long _tmpTakenAt;
            _tmpTakenAt = _cursor.getLong(_cursorIndexOfTakenAt);
            _item = new FindingPhotoEntity(_tmpPhotoId,_tmpFindingId,_tmpLocalPath,_tmpRemoteUrl,_tmpCaption,_tmpIsUploaded,_tmpFileSizeBytes,_tmpTakenAt);
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
  public Object getPendingUploadPhotos(
      final Continuation<? super List<FindingPhotoEntity>> $completion) {
    final String _sql = "SELECT * FROM finding_photos WHERE is_uploaded = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<FindingPhotoEntity>>() {
      @Override
      @NonNull
      public List<FindingPhotoEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPhotoId = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_id");
          final int _cursorIndexOfFindingId = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_id");
          final int _cursorIndexOfLocalPath = CursorUtil.getColumnIndexOrThrow(_cursor, "local_path");
          final int _cursorIndexOfRemoteUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "remote_url");
          final int _cursorIndexOfCaption = CursorUtil.getColumnIndexOrThrow(_cursor, "caption");
          final int _cursorIndexOfIsUploaded = CursorUtil.getColumnIndexOrThrow(_cursor, "is_uploaded");
          final int _cursorIndexOfFileSizeBytes = CursorUtil.getColumnIndexOrThrow(_cursor, "file_size_bytes");
          final int _cursorIndexOfTakenAt = CursorUtil.getColumnIndexOrThrow(_cursor, "taken_at");
          final List<FindingPhotoEntity> _result = new ArrayList<FindingPhotoEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FindingPhotoEntity _item;
            final long _tmpPhotoId;
            _tmpPhotoId = _cursor.getLong(_cursorIndexOfPhotoId);
            final long _tmpFindingId;
            _tmpFindingId = _cursor.getLong(_cursorIndexOfFindingId);
            final String _tmpLocalPath;
            if (_cursor.isNull(_cursorIndexOfLocalPath)) {
              _tmpLocalPath = null;
            } else {
              _tmpLocalPath = _cursor.getString(_cursorIndexOfLocalPath);
            }
            final String _tmpRemoteUrl;
            if (_cursor.isNull(_cursorIndexOfRemoteUrl)) {
              _tmpRemoteUrl = null;
            } else {
              _tmpRemoteUrl = _cursor.getString(_cursorIndexOfRemoteUrl);
            }
            final String _tmpCaption;
            if (_cursor.isNull(_cursorIndexOfCaption)) {
              _tmpCaption = null;
            } else {
              _tmpCaption = _cursor.getString(_cursorIndexOfCaption);
            }
            final boolean _tmpIsUploaded;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsUploaded);
            _tmpIsUploaded = _tmp != 0;
            final long _tmpFileSizeBytes;
            _tmpFileSizeBytes = _cursor.getLong(_cursorIndexOfFileSizeBytes);
            final long _tmpTakenAt;
            _tmpTakenAt = _cursor.getLong(_cursorIndexOfTakenAt);
            _item = new FindingPhotoEntity(_tmpPhotoId,_tmpFindingId,_tmpLocalPath,_tmpRemoteUrl,_tmpCaption,_tmpIsUploaded,_tmpFileSizeBytes,_tmpTakenAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> getPhotoCountByFinding(final long findingId) {
    final String _sql = "SELECT COUNT(*) FROM finding_photos WHERE finding_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, findingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"finding_photos"}, new Callable<Integer>() {
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
