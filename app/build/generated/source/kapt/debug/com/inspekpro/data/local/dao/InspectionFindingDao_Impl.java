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
import com.inspekpro.data.local.entity.FindingResult;
import com.inspekpro.data.local.entity.FindingSeverity;
import com.inspekpro.data.local.entity.FindingStatus;
import com.inspekpro.data.local.entity.InspectionFindingEntity;
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
public final class InspectionFindingDao_Impl implements InspectionFindingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<InspectionFindingEntity> __insertionAdapterOfInspectionFindingEntity;

  private final InspekProConverters __inspekProConverters = new InspekProConverters();

  private final EntityDeletionOrUpdateAdapter<InspectionFindingEntity> __deletionAdapterOfInspectionFindingEntity;

  private final EntityDeletionOrUpdateAdapter<InspectionFindingEntity> __updateAdapterOfInspectionFindingEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFindingById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFindingResult;

  private final SharedSQLiteStatement __preparedStmtOfUpdateFindingStatus;

  public InspectionFindingDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfInspectionFindingEntity = new EntityInsertionAdapter<InspectionFindingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `inspection_findings` (`finding_id`,`session_id`,`checklist_item_id`,`finding_code`,`category`,`title`,`description`,`severity`,`status`,`result`,`location_detail`,`recommendation`,`due_date`,`assigned_to`,`photo_paths`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InspectionFindingEntity entity) {
        statement.bindLong(1, entity.getFindingId());
        statement.bindLong(2, entity.getSessionId());
        if (entity.getChecklistItemId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getChecklistItemId());
        }
        if (entity.getFindingCode() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getFindingCode());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategory());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDescription());
        }
        final String _tmp = __inspekProConverters.fromFindingSeverity(entity.getSeverity());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        final String _tmp_1 = __inspekProConverters.fromFindingStatus(entity.getStatus());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final String _tmp_2 = __inspekProConverters.fromFindingResult(entity.getResult());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_2);
        }
        if (entity.getLocationDetail() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getLocationDetail());
        }
        if (entity.getRecommendation() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getRecommendation());
        }
        if (entity.getDueDate() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getDueDate());
        }
        if (entity.getAssignedTo() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getAssignedTo());
        }
        if (entity.getPhotoPaths() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getPhotoPaths());
        }
        statement.bindLong(16, entity.getCreatedAt());
        statement.bindLong(17, entity.getUpdatedAt());
      }
    };
    this.__deletionAdapterOfInspectionFindingEntity = new EntityDeletionOrUpdateAdapter<InspectionFindingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `inspection_findings` WHERE `finding_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InspectionFindingEntity entity) {
        statement.bindLong(1, entity.getFindingId());
      }
    };
    this.__updateAdapterOfInspectionFindingEntity = new EntityDeletionOrUpdateAdapter<InspectionFindingEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `inspection_findings` SET `finding_id` = ?,`session_id` = ?,`checklist_item_id` = ?,`finding_code` = ?,`category` = ?,`title` = ?,`description` = ?,`severity` = ?,`status` = ?,`result` = ?,`location_detail` = ?,`recommendation` = ?,`due_date` = ?,`assigned_to` = ?,`photo_paths` = ?,`created_at` = ?,`updated_at` = ? WHERE `finding_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final InspectionFindingEntity entity) {
        statement.bindLong(1, entity.getFindingId());
        statement.bindLong(2, entity.getSessionId());
        if (entity.getChecklistItemId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getChecklistItemId());
        }
        if (entity.getFindingCode() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getFindingCode());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getCategory());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDescription());
        }
        final String _tmp = __inspekProConverters.fromFindingSeverity(entity.getSeverity());
        if (_tmp == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp);
        }
        final String _tmp_1 = __inspekProConverters.fromFindingStatus(entity.getStatus());
        if (_tmp_1 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_1);
        }
        final String _tmp_2 = __inspekProConverters.fromFindingResult(entity.getResult());
        if (_tmp_2 == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp_2);
        }
        if (entity.getLocationDetail() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getLocationDetail());
        }
        if (entity.getRecommendation() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getRecommendation());
        }
        if (entity.getDueDate() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getDueDate());
        }
        if (entity.getAssignedTo() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getAssignedTo());
        }
        if (entity.getPhotoPaths() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getPhotoPaths());
        }
        statement.bindLong(16, entity.getCreatedAt());
        statement.bindLong(17, entity.getUpdatedAt());
        statement.bindLong(18, entity.getFindingId());
      }
    };
    this.__preparedStmtOfDeleteFindingById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM inspection_findings WHERE finding_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFindingResult = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE inspection_findings \n"
                + "        SET result = ?, updated_at = ? \n"
                + "        WHERE finding_id = ?\n"
                + "    ";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateFindingStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "\n"
                + "        UPDATE inspection_findings \n"
                + "        SET status = ?, updated_at = ? \n"
                + "        WHERE finding_id = ?\n"
                + "    ";
        return _query;
      }
    };
  }

  @Override
  public Object insertFinding(final InspectionFindingEntity finding,
      final Continuation<? super Long> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfInspectionFindingEntity.insertAndReturnId(finding);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object insertFindings(final List<InspectionFindingEntity> findings,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfInspectionFindingEntity.insert(findings);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteFinding(final InspectionFindingEntity finding,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfInspectionFindingEntity.handle(finding);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object updateFinding(final InspectionFindingEntity finding,
      final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfInspectionFindingEntity.handle(finding);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, arg1);
  }

  @Override
  public Object deleteFindingById(final long findingId, final Continuation<? super Unit> arg1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFindingById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, findingId);
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
          __preparedStmtOfDeleteFindingById.release(_stmt);
        }
      }
    }, arg1);
  }

  @Override
  public Object updateFindingResult(final long findingId, final FindingResult result,
      final long now, final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFindingResult.acquire();
        int _argIndex = 1;
        final String _tmp = __inspekProConverters.fromFindingResult(result);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, findingId);
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
          __preparedStmtOfUpdateFindingResult.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Object updateFindingStatus(final long findingId, final FindingStatus status,
      final long now, final Continuation<? super Unit> arg3) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateFindingStatus.acquire();
        int _argIndex = 1;
        final String _tmp = __inspekProConverters.fromFindingStatus(status);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
        _argIndex = 2;
        _stmt.bindLong(_argIndex, now);
        _argIndex = 3;
        _stmt.bindLong(_argIndex, findingId);
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
          __preparedStmtOfUpdateFindingStatus.release(_stmt);
        }
      }
    }, arg3);
  }

  @Override
  public Flow<List<InspectionFindingEntity>> getFindingsBySession(final long sessionId) {
    final String _sql = "SELECT * FROM inspection_findings WHERE session_id = ? ORDER BY created_at ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<List<InspectionFindingEntity>>() {
      @Override
      @NonNull
      public List<InspectionFindingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFindingId = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfChecklistItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "checklist_item_id");
          final int _cursorIndexOfFindingCode = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_code");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfLocationDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "location_detail");
          final int _cursorIndexOfRecommendation = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendation");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAssignedTo = CursorUtil.getColumnIndexOrThrow(_cursor, "assigned_to");
          final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_paths");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<InspectionFindingEntity> _result = new ArrayList<InspectionFindingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionFindingEntity _item;
            final long _tmpFindingId;
            _tmpFindingId = _cursor.getLong(_cursorIndexOfFindingId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final Long _tmpChecklistItemId;
            if (_cursor.isNull(_cursorIndexOfChecklistItemId)) {
              _tmpChecklistItemId = null;
            } else {
              _tmpChecklistItemId = _cursor.getLong(_cursorIndexOfChecklistItemId);
            }
            final String _tmpFindingCode;
            if (_cursor.isNull(_cursorIndexOfFindingCode)) {
              _tmpFindingCode = null;
            } else {
              _tmpFindingCode = _cursor.getString(_cursorIndexOfFindingCode);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
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
            final FindingSeverity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __inspekProConverters.toFindingSeverity(_tmp);
            final FindingStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toFindingStatus(_tmp_1);
            final FindingResult _tmpResult;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfResult);
            }
            _tmpResult = __inspekProConverters.toFindingResult(_tmp_2);
            final String _tmpLocationDetail;
            if (_cursor.isNull(_cursorIndexOfLocationDetail)) {
              _tmpLocationDetail = null;
            } else {
              _tmpLocationDetail = _cursor.getString(_cursorIndexOfLocationDetail);
            }
            final String _tmpRecommendation;
            if (_cursor.isNull(_cursorIndexOfRecommendation)) {
              _tmpRecommendation = null;
            } else {
              _tmpRecommendation = _cursor.getString(_cursorIndexOfRecommendation);
            }
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpAssignedTo;
            if (_cursor.isNull(_cursorIndexOfAssignedTo)) {
              _tmpAssignedTo = null;
            } else {
              _tmpAssignedTo = _cursor.getString(_cursorIndexOfAssignedTo);
            }
            final String _tmpPhotoPaths;
            if (_cursor.isNull(_cursorIndexOfPhotoPaths)) {
              _tmpPhotoPaths = null;
            } else {
              _tmpPhotoPaths = _cursor.getString(_cursorIndexOfPhotoPaths);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new InspectionFindingEntity(_tmpFindingId,_tmpSessionId,_tmpChecklistItemId,_tmpFindingCode,_tmpCategory,_tmpTitle,_tmpDescription,_tmpSeverity,_tmpStatus,_tmpResult,_tmpLocationDetail,_tmpRecommendation,_tmpDueDate,_tmpAssignedTo,_tmpPhotoPaths,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<InspectionFindingEntity> getFindingById(final long findingId) {
    final String _sql = "SELECT * FROM inspection_findings WHERE finding_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, findingId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<InspectionFindingEntity>() {
      @Override
      @Nullable
      public InspectionFindingEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFindingId = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfChecklistItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "checklist_item_id");
          final int _cursorIndexOfFindingCode = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_code");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfLocationDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "location_detail");
          final int _cursorIndexOfRecommendation = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendation");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAssignedTo = CursorUtil.getColumnIndexOrThrow(_cursor, "assigned_to");
          final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_paths");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final InspectionFindingEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpFindingId;
            _tmpFindingId = _cursor.getLong(_cursorIndexOfFindingId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final Long _tmpChecklistItemId;
            if (_cursor.isNull(_cursorIndexOfChecklistItemId)) {
              _tmpChecklistItemId = null;
            } else {
              _tmpChecklistItemId = _cursor.getLong(_cursorIndexOfChecklistItemId);
            }
            final String _tmpFindingCode;
            if (_cursor.isNull(_cursorIndexOfFindingCode)) {
              _tmpFindingCode = null;
            } else {
              _tmpFindingCode = _cursor.getString(_cursorIndexOfFindingCode);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
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
            final FindingSeverity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __inspekProConverters.toFindingSeverity(_tmp);
            final FindingStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toFindingStatus(_tmp_1);
            final FindingResult _tmpResult;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfResult);
            }
            _tmpResult = __inspekProConverters.toFindingResult(_tmp_2);
            final String _tmpLocationDetail;
            if (_cursor.isNull(_cursorIndexOfLocationDetail)) {
              _tmpLocationDetail = null;
            } else {
              _tmpLocationDetail = _cursor.getString(_cursorIndexOfLocationDetail);
            }
            final String _tmpRecommendation;
            if (_cursor.isNull(_cursorIndexOfRecommendation)) {
              _tmpRecommendation = null;
            } else {
              _tmpRecommendation = _cursor.getString(_cursorIndexOfRecommendation);
            }
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpAssignedTo;
            if (_cursor.isNull(_cursorIndexOfAssignedTo)) {
              _tmpAssignedTo = null;
            } else {
              _tmpAssignedTo = _cursor.getString(_cursorIndexOfAssignedTo);
            }
            final String _tmpPhotoPaths;
            if (_cursor.isNull(_cursorIndexOfPhotoPaths)) {
              _tmpPhotoPaths = null;
            } else {
              _tmpPhotoPaths = _cursor.getString(_cursorIndexOfPhotoPaths);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new InspectionFindingEntity(_tmpFindingId,_tmpSessionId,_tmpChecklistItemId,_tmpFindingCode,_tmpCategory,_tmpTitle,_tmpDescription,_tmpSeverity,_tmpStatus,_tmpResult,_tmpLocationDetail,_tmpRecommendation,_tmpDueDate,_tmpAssignedTo,_tmpPhotoPaths,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Object getFindingByIdOnce(final long findingId,
      final Continuation<? super InspectionFindingEntity> arg1) {
    final String _sql = "SELECT * FROM inspection_findings WHERE finding_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, findingId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<InspectionFindingEntity>() {
      @Override
      @Nullable
      public InspectionFindingEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFindingId = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfChecklistItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "checklist_item_id");
          final int _cursorIndexOfFindingCode = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_code");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfLocationDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "location_detail");
          final int _cursorIndexOfRecommendation = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendation");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAssignedTo = CursorUtil.getColumnIndexOrThrow(_cursor, "assigned_to");
          final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_paths");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final InspectionFindingEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpFindingId;
            _tmpFindingId = _cursor.getLong(_cursorIndexOfFindingId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final Long _tmpChecklistItemId;
            if (_cursor.isNull(_cursorIndexOfChecklistItemId)) {
              _tmpChecklistItemId = null;
            } else {
              _tmpChecklistItemId = _cursor.getLong(_cursorIndexOfChecklistItemId);
            }
            final String _tmpFindingCode;
            if (_cursor.isNull(_cursorIndexOfFindingCode)) {
              _tmpFindingCode = null;
            } else {
              _tmpFindingCode = _cursor.getString(_cursorIndexOfFindingCode);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
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
            final FindingSeverity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __inspekProConverters.toFindingSeverity(_tmp);
            final FindingStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toFindingStatus(_tmp_1);
            final FindingResult _tmpResult;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfResult);
            }
            _tmpResult = __inspekProConverters.toFindingResult(_tmp_2);
            final String _tmpLocationDetail;
            if (_cursor.isNull(_cursorIndexOfLocationDetail)) {
              _tmpLocationDetail = null;
            } else {
              _tmpLocationDetail = _cursor.getString(_cursorIndexOfLocationDetail);
            }
            final String _tmpRecommendation;
            if (_cursor.isNull(_cursorIndexOfRecommendation)) {
              _tmpRecommendation = null;
            } else {
              _tmpRecommendation = _cursor.getString(_cursorIndexOfRecommendation);
            }
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpAssignedTo;
            if (_cursor.isNull(_cursorIndexOfAssignedTo)) {
              _tmpAssignedTo = null;
            } else {
              _tmpAssignedTo = _cursor.getString(_cursorIndexOfAssignedTo);
            }
            final String _tmpPhotoPaths;
            if (_cursor.isNull(_cursorIndexOfPhotoPaths)) {
              _tmpPhotoPaths = null;
            } else {
              _tmpPhotoPaths = _cursor.getString(_cursorIndexOfPhotoPaths);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _result = new InspectionFindingEntity(_tmpFindingId,_tmpSessionId,_tmpChecklistItemId,_tmpFindingCode,_tmpCategory,_tmpTitle,_tmpDescription,_tmpSeverity,_tmpStatus,_tmpResult,_tmpLocationDetail,_tmpRecommendation,_tmpDueDate,_tmpAssignedTo,_tmpPhotoPaths,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<InspectionFindingEntity>> getFindingsBySeverity(final long sessionId,
      final FindingSeverity severity) {
    final String _sql = "\n"
            + "        SELECT * FROM inspection_findings \n"
            + "        WHERE session_id = ? AND severity = ?\n"
            + "        ORDER BY created_at ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    _argIndex = 2;
    final String _tmp = __inspekProConverters.fromFindingSeverity(severity);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<List<InspectionFindingEntity>>() {
      @Override
      @NonNull
      public List<InspectionFindingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFindingId = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfChecklistItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "checklist_item_id");
          final int _cursorIndexOfFindingCode = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_code");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfLocationDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "location_detail");
          final int _cursorIndexOfRecommendation = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendation");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAssignedTo = CursorUtil.getColumnIndexOrThrow(_cursor, "assigned_to");
          final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_paths");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<InspectionFindingEntity> _result = new ArrayList<InspectionFindingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionFindingEntity _item;
            final long _tmpFindingId;
            _tmpFindingId = _cursor.getLong(_cursorIndexOfFindingId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final Long _tmpChecklistItemId;
            if (_cursor.isNull(_cursorIndexOfChecklistItemId)) {
              _tmpChecklistItemId = null;
            } else {
              _tmpChecklistItemId = _cursor.getLong(_cursorIndexOfChecklistItemId);
            }
            final String _tmpFindingCode;
            if (_cursor.isNull(_cursorIndexOfFindingCode)) {
              _tmpFindingCode = null;
            } else {
              _tmpFindingCode = _cursor.getString(_cursorIndexOfFindingCode);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
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
            final FindingSeverity _tmpSeverity;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __inspekProConverters.toFindingSeverity(_tmp_1);
            final FindingStatus _tmpStatus;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toFindingStatus(_tmp_2);
            final FindingResult _tmpResult;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfResult);
            }
            _tmpResult = __inspekProConverters.toFindingResult(_tmp_3);
            final String _tmpLocationDetail;
            if (_cursor.isNull(_cursorIndexOfLocationDetail)) {
              _tmpLocationDetail = null;
            } else {
              _tmpLocationDetail = _cursor.getString(_cursorIndexOfLocationDetail);
            }
            final String _tmpRecommendation;
            if (_cursor.isNull(_cursorIndexOfRecommendation)) {
              _tmpRecommendation = null;
            } else {
              _tmpRecommendation = _cursor.getString(_cursorIndexOfRecommendation);
            }
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpAssignedTo;
            if (_cursor.isNull(_cursorIndexOfAssignedTo)) {
              _tmpAssignedTo = null;
            } else {
              _tmpAssignedTo = _cursor.getString(_cursorIndexOfAssignedTo);
            }
            final String _tmpPhotoPaths;
            if (_cursor.isNull(_cursorIndexOfPhotoPaths)) {
              _tmpPhotoPaths = null;
            } else {
              _tmpPhotoPaths = _cursor.getString(_cursorIndexOfPhotoPaths);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new InspectionFindingEntity(_tmpFindingId,_tmpSessionId,_tmpChecklistItemId,_tmpFindingCode,_tmpCategory,_tmpTitle,_tmpDescription,_tmpSeverity,_tmpStatus,_tmpResult,_tmpLocationDetail,_tmpRecommendation,_tmpDueDate,_tmpAssignedTo,_tmpPhotoPaths,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<InspectionFindingEntity>> getFindingsByStatus(final long sessionId,
      final FindingStatus status) {
    final String _sql = "\n"
            + "        SELECT * FROM inspection_findings \n"
            + "        WHERE session_id = ? AND status = ?\n"
            + "        ORDER BY created_at ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    _argIndex = 2;
    final String _tmp = __inspekProConverters.fromFindingStatus(status);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<List<InspectionFindingEntity>>() {
      @Override
      @NonNull
      public List<InspectionFindingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFindingId = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfChecklistItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "checklist_item_id");
          final int _cursorIndexOfFindingCode = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_code");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfLocationDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "location_detail");
          final int _cursorIndexOfRecommendation = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendation");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAssignedTo = CursorUtil.getColumnIndexOrThrow(_cursor, "assigned_to");
          final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_paths");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<InspectionFindingEntity> _result = new ArrayList<InspectionFindingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionFindingEntity _item;
            final long _tmpFindingId;
            _tmpFindingId = _cursor.getLong(_cursorIndexOfFindingId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final Long _tmpChecklistItemId;
            if (_cursor.isNull(_cursorIndexOfChecklistItemId)) {
              _tmpChecklistItemId = null;
            } else {
              _tmpChecklistItemId = _cursor.getLong(_cursorIndexOfChecklistItemId);
            }
            final String _tmpFindingCode;
            if (_cursor.isNull(_cursorIndexOfFindingCode)) {
              _tmpFindingCode = null;
            } else {
              _tmpFindingCode = _cursor.getString(_cursorIndexOfFindingCode);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
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
            final FindingSeverity _tmpSeverity;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __inspekProConverters.toFindingSeverity(_tmp_1);
            final FindingStatus _tmpStatus;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toFindingStatus(_tmp_2);
            final FindingResult _tmpResult;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfResult);
            }
            _tmpResult = __inspekProConverters.toFindingResult(_tmp_3);
            final String _tmpLocationDetail;
            if (_cursor.isNull(_cursorIndexOfLocationDetail)) {
              _tmpLocationDetail = null;
            } else {
              _tmpLocationDetail = _cursor.getString(_cursorIndexOfLocationDetail);
            }
            final String _tmpRecommendation;
            if (_cursor.isNull(_cursorIndexOfRecommendation)) {
              _tmpRecommendation = null;
            } else {
              _tmpRecommendation = _cursor.getString(_cursorIndexOfRecommendation);
            }
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpAssignedTo;
            if (_cursor.isNull(_cursorIndexOfAssignedTo)) {
              _tmpAssignedTo = null;
            } else {
              _tmpAssignedTo = _cursor.getString(_cursorIndexOfAssignedTo);
            }
            final String _tmpPhotoPaths;
            if (_cursor.isNull(_cursorIndexOfPhotoPaths)) {
              _tmpPhotoPaths = null;
            } else {
              _tmpPhotoPaths = _cursor.getString(_cursorIndexOfPhotoPaths);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new InspectionFindingEntity(_tmpFindingId,_tmpSessionId,_tmpChecklistItemId,_tmpFindingCode,_tmpCategory,_tmpTitle,_tmpDescription,_tmpSeverity,_tmpStatus,_tmpResult,_tmpLocationDetail,_tmpRecommendation,_tmpDueDate,_tmpAssignedTo,_tmpPhotoPaths,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<InspectionFindingEntity>> getFindingsByCategory(final long sessionId,
      final String category) {
    final String _sql = "\n"
            + "        SELECT * FROM inspection_findings \n"
            + "        WHERE session_id = ? AND category = ?\n"
            + "        ORDER BY created_at ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    _argIndex = 2;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<List<InspectionFindingEntity>>() {
      @Override
      @NonNull
      public List<InspectionFindingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFindingId = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfChecklistItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "checklist_item_id");
          final int _cursorIndexOfFindingCode = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_code");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfLocationDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "location_detail");
          final int _cursorIndexOfRecommendation = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendation");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAssignedTo = CursorUtil.getColumnIndexOrThrow(_cursor, "assigned_to");
          final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_paths");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<InspectionFindingEntity> _result = new ArrayList<InspectionFindingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionFindingEntity _item;
            final long _tmpFindingId;
            _tmpFindingId = _cursor.getLong(_cursorIndexOfFindingId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final Long _tmpChecklistItemId;
            if (_cursor.isNull(_cursorIndexOfChecklistItemId)) {
              _tmpChecklistItemId = null;
            } else {
              _tmpChecklistItemId = _cursor.getLong(_cursorIndexOfChecklistItemId);
            }
            final String _tmpFindingCode;
            if (_cursor.isNull(_cursorIndexOfFindingCode)) {
              _tmpFindingCode = null;
            } else {
              _tmpFindingCode = _cursor.getString(_cursorIndexOfFindingCode);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
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
            final FindingSeverity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __inspekProConverters.toFindingSeverity(_tmp);
            final FindingStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toFindingStatus(_tmp_1);
            final FindingResult _tmpResult;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfResult);
            }
            _tmpResult = __inspekProConverters.toFindingResult(_tmp_2);
            final String _tmpLocationDetail;
            if (_cursor.isNull(_cursorIndexOfLocationDetail)) {
              _tmpLocationDetail = null;
            } else {
              _tmpLocationDetail = _cursor.getString(_cursorIndexOfLocationDetail);
            }
            final String _tmpRecommendation;
            if (_cursor.isNull(_cursorIndexOfRecommendation)) {
              _tmpRecommendation = null;
            } else {
              _tmpRecommendation = _cursor.getString(_cursorIndexOfRecommendation);
            }
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpAssignedTo;
            if (_cursor.isNull(_cursorIndexOfAssignedTo)) {
              _tmpAssignedTo = null;
            } else {
              _tmpAssignedTo = _cursor.getString(_cursorIndexOfAssignedTo);
            }
            final String _tmpPhotoPaths;
            if (_cursor.isNull(_cursorIndexOfPhotoPaths)) {
              _tmpPhotoPaths = null;
            } else {
              _tmpPhotoPaths = _cursor.getString(_cursorIndexOfPhotoPaths);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new InspectionFindingEntity(_tmpFindingId,_tmpSessionId,_tmpChecklistItemId,_tmpFindingCode,_tmpCategory,_tmpTitle,_tmpDescription,_tmpSeverity,_tmpStatus,_tmpResult,_tmpLocationDetail,_tmpRecommendation,_tmpDueDate,_tmpAssignedTo,_tmpPhotoPaths,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<List<InspectionFindingEntity>> getRecentFindings(final int limit) {
    final String _sql = "SELECT * FROM inspection_findings ORDER BY created_at DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<List<InspectionFindingEntity>>() {
      @Override
      @NonNull
      public List<InspectionFindingEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfFindingId = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfChecklistItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "checklist_item_id");
          final int _cursorIndexOfFindingCode = CursorUtil.getColumnIndexOrThrow(_cursor, "finding_code");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfSeverity = CursorUtil.getColumnIndexOrThrow(_cursor, "severity");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfLocationDetail = CursorUtil.getColumnIndexOrThrow(_cursor, "location_detail");
          final int _cursorIndexOfRecommendation = CursorUtil.getColumnIndexOrThrow(_cursor, "recommendation");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAssignedTo = CursorUtil.getColumnIndexOrThrow(_cursor, "assigned_to");
          final int _cursorIndexOfPhotoPaths = CursorUtil.getColumnIndexOrThrow(_cursor, "photo_paths");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<InspectionFindingEntity> _result = new ArrayList<InspectionFindingEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final InspectionFindingEntity _item;
            final long _tmpFindingId;
            _tmpFindingId = _cursor.getLong(_cursorIndexOfFindingId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
            final Long _tmpChecklistItemId;
            if (_cursor.isNull(_cursorIndexOfChecklistItemId)) {
              _tmpChecklistItemId = null;
            } else {
              _tmpChecklistItemId = _cursor.getLong(_cursorIndexOfChecklistItemId);
            }
            final String _tmpFindingCode;
            if (_cursor.isNull(_cursorIndexOfFindingCode)) {
              _tmpFindingCode = null;
            } else {
              _tmpFindingCode = _cursor.getString(_cursorIndexOfFindingCode);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
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
            final FindingSeverity _tmpSeverity;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSeverity)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSeverity);
            }
            _tmpSeverity = __inspekProConverters.toFindingSeverity(_tmp);
            final FindingStatus _tmpStatus;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStatus)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStatus);
            }
            _tmpStatus = __inspekProConverters.toFindingStatus(_tmp_1);
            final FindingResult _tmpResult;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfResult);
            }
            _tmpResult = __inspekProConverters.toFindingResult(_tmp_2);
            final String _tmpLocationDetail;
            if (_cursor.isNull(_cursorIndexOfLocationDetail)) {
              _tmpLocationDetail = null;
            } else {
              _tmpLocationDetail = _cursor.getString(_cursorIndexOfLocationDetail);
            }
            final String _tmpRecommendation;
            if (_cursor.isNull(_cursorIndexOfRecommendation)) {
              _tmpRecommendation = null;
            } else {
              _tmpRecommendation = _cursor.getString(_cursorIndexOfRecommendation);
            }
            final Long _tmpDueDate;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmpDueDate = null;
            } else {
              _tmpDueDate = _cursor.getLong(_cursorIndexOfDueDate);
            }
            final String _tmpAssignedTo;
            if (_cursor.isNull(_cursorIndexOfAssignedTo)) {
              _tmpAssignedTo = null;
            } else {
              _tmpAssignedTo = _cursor.getString(_cursorIndexOfAssignedTo);
            }
            final String _tmpPhotoPaths;
            if (_cursor.isNull(_cursorIndexOfPhotoPaths)) {
              _tmpPhotoPaths = null;
            } else {
              _tmpPhotoPaths = _cursor.getString(_cursorIndexOfPhotoPaths);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpUpdatedAt;
            _tmpUpdatedAt = _cursor.getLong(_cursorIndexOfUpdatedAt);
            _item = new InspectionFindingEntity(_tmpFindingId,_tmpSessionId,_tmpChecklistItemId,_tmpFindingCode,_tmpCategory,_tmpTitle,_tmpDescription,_tmpSeverity,_tmpStatus,_tmpResult,_tmpLocationDetail,_tmpRecommendation,_tmpDueDate,_tmpAssignedTo,_tmpPhotoPaths,_tmpCreatedAt,_tmpUpdatedAt);
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
  public Flow<Integer> getTotalFindingsCount(final long sessionId) {
    final String _sql = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<Integer>() {
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
  public Flow<Integer> getCriticalCount(final long sessionId) {
    final String _sql = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = ? AND severity = 'CRITICAL'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<Integer>() {
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
  public Flow<Integer> getMajorCount(final long sessionId) {
    final String _sql = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = ? AND severity = 'MAJOR'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<Integer>() {
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
  public Flow<Integer> getMinorCount(final long sessionId) {
    final String _sql = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = ? AND severity = 'MINOR'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<Integer>() {
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
  public Flow<Integer> getPassCount(final long sessionId) {
    final String _sql = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = ? AND result = 'PASS'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<Integer>() {
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
  public Flow<Integer> getFailCount(final long sessionId) {
    final String _sql = "SELECT COUNT(*) FROM inspection_findings WHERE session_id = ? AND result = 'FAIL'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<Integer>() {
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
  public Flow<List<String>> getCategoriesBySession(final long sessionId) {
    final String _sql = "SELECT DISTINCT category FROM inspection_findings WHERE session_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"inspection_findings"}, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            if (_cursor.isNull(0)) {
              _item = null;
            } else {
              _item = _cursor.getString(0);
            }
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
  public Object getFindingSummaryRaw(final long sessionId,
      final Continuation<? super InspectionFindingDao.FindingSummaryRaw> arg1) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            COUNT(*) as totalFindings,\n"
            + "            SUM(CASE WHEN severity = 'CRITICAL' THEN 1 ELSE 0 END) as criticalCount,\n"
            + "            SUM(CASE WHEN severity = 'MAJOR' THEN 1 ELSE 0 END) as majorCount,\n"
            + "            SUM(CASE WHEN severity = 'MINOR' THEN 1 ELSE 0 END) as minorCount,\n"
            + "            SUM(CASE WHEN severity = 'OBSERVATION' THEN 1 ELSE 0 END) as observationCount,\n"
            + "            SUM(CASE WHEN result = 'PASS' THEN 1 ELSE 0 END) as passCount,\n"
            + "            SUM(CASE WHEN result = 'FAIL' THEN 1 ELSE 0 END) as failCount,\n"
            + "            SUM(CASE WHEN result = 'NOT_APPLICABLE' THEN 1 ELSE 0 END) as naCount\n"
            + "        FROM inspection_findings\n"
            + "        WHERE session_id = ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<InspectionFindingDao.FindingSummaryRaw>() {
      @Override
      @NonNull
      public InspectionFindingDao.FindingSummaryRaw call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTotalFindings = 0;
          final int _cursorIndexOfCriticalCount = 1;
          final int _cursorIndexOfMajorCount = 2;
          final int _cursorIndexOfMinorCount = 3;
          final int _cursorIndexOfObservationCount = 4;
          final int _cursorIndexOfPassCount = 5;
          final int _cursorIndexOfFailCount = 6;
          final int _cursorIndexOfNaCount = 7;
          final InspectionFindingDao.FindingSummaryRaw _result;
          if (_cursor.moveToFirst()) {
            final int _tmpTotalFindings;
            _tmpTotalFindings = _cursor.getInt(_cursorIndexOfTotalFindings);
            final int _tmpCriticalCount;
            _tmpCriticalCount = _cursor.getInt(_cursorIndexOfCriticalCount);
            final int _tmpMajorCount;
            _tmpMajorCount = _cursor.getInt(_cursorIndexOfMajorCount);
            final int _tmpMinorCount;
            _tmpMinorCount = _cursor.getInt(_cursorIndexOfMinorCount);
            final int _tmpObservationCount;
            _tmpObservationCount = _cursor.getInt(_cursorIndexOfObservationCount);
            final int _tmpPassCount;
            _tmpPassCount = _cursor.getInt(_cursorIndexOfPassCount);
            final int _tmpFailCount;
            _tmpFailCount = _cursor.getInt(_cursorIndexOfFailCount);
            final int _tmpNaCount;
            _tmpNaCount = _cursor.getInt(_cursorIndexOfNaCount);
            _result = new InspectionFindingDao.FindingSummaryRaw(_tmpTotalFindings,_tmpCriticalCount,_tmpMajorCount,_tmpMinorCount,_tmpObservationCount,_tmpPassCount,_tmpFailCount,_tmpNaCount);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
