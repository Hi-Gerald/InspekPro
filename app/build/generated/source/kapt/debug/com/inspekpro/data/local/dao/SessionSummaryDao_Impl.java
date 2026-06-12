package com.inspekpro.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.inspekpro.data.local.entity.SessionSummaryEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SessionSummaryDao_Impl implements SessionSummaryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SessionSummaryEntity> __insertionAdapterOfSessionSummaryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteSummaryBySession;

  public SessionSummaryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSessionSummaryEntity = new EntityInsertionAdapter<SessionSummaryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `session_summaries` (`summary_id`,`session_id`,`total_findings`,`critical_count`,`major_count`,`minor_count`,`observation_count`,`pass_count`,`fail_count`,`na_count`,`compliance_score`,`open_findings`,`resolved_findings`,`duration_minutes`,`overall_grade`,`generated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SessionSummaryEntity entity) {
        statement.bindLong(1, entity.getSummaryId());
        statement.bindLong(2, entity.getSessionId());
        statement.bindLong(3, entity.getTotalFindings());
        statement.bindLong(4, entity.getCriticalCount());
        statement.bindLong(5, entity.getMajorCount());
        statement.bindLong(6, entity.getMinorCount());
        statement.bindLong(7, entity.getObservationCount());
        statement.bindLong(8, entity.getPassCount());
        statement.bindLong(9, entity.getFailCount());
        statement.bindLong(10, entity.getNaCount());
        statement.bindDouble(11, entity.getComplianceScore());
        statement.bindLong(12, entity.getOpenFindings());
        statement.bindLong(13, entity.getResolvedFindings());
        statement.bindLong(14, entity.getDurationMinutes());
        if (entity.getOverallGrade() == null) {
          statement.bindNull(15);
        } else {
          statement.bindString(15, entity.getOverallGrade());
        }
        statement.bindLong(16, entity.getGeneratedAt());
      }
    };
    this.__preparedStmtOfDeleteSummaryBySession = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM session_summaries WHERE session_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrUpdateSummary(final SessionSummaryEntity summary,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSessionSummaryEntity.insertAndReturnId(summary);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSummaryBySession(final long sessionId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteSummaryBySession.acquire();
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
          __preparedStmtOfDeleteSummaryBySession.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<SessionSummaryEntity> getSummaryBySession(final long sessionId) {
    final String _sql = "SELECT * FROM session_summaries WHERE session_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"session_summaries"}, new Callable<SessionSummaryEntity>() {
      @Override
      @Nullable
      public SessionSummaryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSummaryId = CursorUtil.getColumnIndexOrThrow(_cursor, "summary_id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfTotalFindings = CursorUtil.getColumnIndexOrThrow(_cursor, "total_findings");
          final int _cursorIndexOfCriticalCount = CursorUtil.getColumnIndexOrThrow(_cursor, "critical_count");
          final int _cursorIndexOfMajorCount = CursorUtil.getColumnIndexOrThrow(_cursor, "major_count");
          final int _cursorIndexOfMinorCount = CursorUtil.getColumnIndexOrThrow(_cursor, "minor_count");
          final int _cursorIndexOfObservationCount = CursorUtil.getColumnIndexOrThrow(_cursor, "observation_count");
          final int _cursorIndexOfPassCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pass_count");
          final int _cursorIndexOfFailCount = CursorUtil.getColumnIndexOrThrow(_cursor, "fail_count");
          final int _cursorIndexOfNaCount = CursorUtil.getColumnIndexOrThrow(_cursor, "na_count");
          final int _cursorIndexOfComplianceScore = CursorUtil.getColumnIndexOrThrow(_cursor, "compliance_score");
          final int _cursorIndexOfOpenFindings = CursorUtil.getColumnIndexOrThrow(_cursor, "open_findings");
          final int _cursorIndexOfResolvedFindings = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved_findings");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_minutes");
          final int _cursorIndexOfOverallGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "overall_grade");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generated_at");
          final SessionSummaryEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpSummaryId;
            _tmpSummaryId = _cursor.getLong(_cursorIndexOfSummaryId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
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
            final float _tmpComplianceScore;
            _tmpComplianceScore = _cursor.getFloat(_cursorIndexOfComplianceScore);
            final int _tmpOpenFindings;
            _tmpOpenFindings = _cursor.getInt(_cursorIndexOfOpenFindings);
            final int _tmpResolvedFindings;
            _tmpResolvedFindings = _cursor.getInt(_cursorIndexOfResolvedFindings);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpOverallGrade;
            if (_cursor.isNull(_cursorIndexOfOverallGrade)) {
              _tmpOverallGrade = null;
            } else {
              _tmpOverallGrade = _cursor.getString(_cursorIndexOfOverallGrade);
            }
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            _result = new SessionSummaryEntity(_tmpSummaryId,_tmpSessionId,_tmpTotalFindings,_tmpCriticalCount,_tmpMajorCount,_tmpMinorCount,_tmpObservationCount,_tmpPassCount,_tmpFailCount,_tmpNaCount,_tmpComplianceScore,_tmpOpenFindings,_tmpResolvedFindings,_tmpDurationMinutes,_tmpOverallGrade,_tmpGeneratedAt);
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
  public Object getSummaryBySessionOnce(final long sessionId,
      final Continuation<? super SessionSummaryEntity> $completion) {
    final String _sql = "SELECT * FROM session_summaries WHERE session_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, sessionId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SessionSummaryEntity>() {
      @Override
      @Nullable
      public SessionSummaryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfSummaryId = CursorUtil.getColumnIndexOrThrow(_cursor, "summary_id");
          final int _cursorIndexOfSessionId = CursorUtil.getColumnIndexOrThrow(_cursor, "session_id");
          final int _cursorIndexOfTotalFindings = CursorUtil.getColumnIndexOrThrow(_cursor, "total_findings");
          final int _cursorIndexOfCriticalCount = CursorUtil.getColumnIndexOrThrow(_cursor, "critical_count");
          final int _cursorIndexOfMajorCount = CursorUtil.getColumnIndexOrThrow(_cursor, "major_count");
          final int _cursorIndexOfMinorCount = CursorUtil.getColumnIndexOrThrow(_cursor, "minor_count");
          final int _cursorIndexOfObservationCount = CursorUtil.getColumnIndexOrThrow(_cursor, "observation_count");
          final int _cursorIndexOfPassCount = CursorUtil.getColumnIndexOrThrow(_cursor, "pass_count");
          final int _cursorIndexOfFailCount = CursorUtil.getColumnIndexOrThrow(_cursor, "fail_count");
          final int _cursorIndexOfNaCount = CursorUtil.getColumnIndexOrThrow(_cursor, "na_count");
          final int _cursorIndexOfComplianceScore = CursorUtil.getColumnIndexOrThrow(_cursor, "compliance_score");
          final int _cursorIndexOfOpenFindings = CursorUtil.getColumnIndexOrThrow(_cursor, "open_findings");
          final int _cursorIndexOfResolvedFindings = CursorUtil.getColumnIndexOrThrow(_cursor, "resolved_findings");
          final int _cursorIndexOfDurationMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "duration_minutes");
          final int _cursorIndexOfOverallGrade = CursorUtil.getColumnIndexOrThrow(_cursor, "overall_grade");
          final int _cursorIndexOfGeneratedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "generated_at");
          final SessionSummaryEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpSummaryId;
            _tmpSummaryId = _cursor.getLong(_cursorIndexOfSummaryId);
            final long _tmpSessionId;
            _tmpSessionId = _cursor.getLong(_cursorIndexOfSessionId);
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
            final float _tmpComplianceScore;
            _tmpComplianceScore = _cursor.getFloat(_cursorIndexOfComplianceScore);
            final int _tmpOpenFindings;
            _tmpOpenFindings = _cursor.getInt(_cursorIndexOfOpenFindings);
            final int _tmpResolvedFindings;
            _tmpResolvedFindings = _cursor.getInt(_cursorIndexOfResolvedFindings);
            final int _tmpDurationMinutes;
            _tmpDurationMinutes = _cursor.getInt(_cursorIndexOfDurationMinutes);
            final String _tmpOverallGrade;
            if (_cursor.isNull(_cursorIndexOfOverallGrade)) {
              _tmpOverallGrade = null;
            } else {
              _tmpOverallGrade = _cursor.getString(_cursorIndexOfOverallGrade);
            }
            final long _tmpGeneratedAt;
            _tmpGeneratedAt = _cursor.getLong(_cursorIndexOfGeneratedAt);
            _result = new SessionSummaryEntity(_tmpSummaryId,_tmpSessionId,_tmpTotalFindings,_tmpCriticalCount,_tmpMajorCount,_tmpMinorCount,_tmpObservationCount,_tmpPassCount,_tmpFailCount,_tmpNaCount,_tmpComplianceScore,_tmpOpenFindings,_tmpResolvedFindings,_tmpDurationMinutes,_tmpOverallGrade,_tmpGeneratedAt);
          } else {
            _result = null;
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
  public Object getDashboardStats(
      final Continuation<? super SessionSummaryDao.DashboardStats> $completion) {
    final String _sql = "\n"
            + "        SELECT \n"
            + "            COUNT(DISTINCT s.session_id) as totalSessions,\n"
            + "            SUM(CASE WHEN s.status = 'COMPLETED' THEN 1 ELSE 0 END) as completedSessions,\n"
            + "            SUM(COALESCE(sm.total_findings, 0)) as totalFindings,\n"
            + "            SUM(COALESCE(sm.critical_count, 0)) as totalCritical\n"
            + "        FROM inspection_sessions s\n"
            + "        LEFT JOIN session_summaries sm ON s.session_id = sm.session_id\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SessionSummaryDao.DashboardStats>() {
      @Override
      @NonNull
      public SessionSummaryDao.DashboardStats call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTotalSessions = 0;
          final int _cursorIndexOfCompletedSessions = 1;
          final int _cursorIndexOfTotalFindings = 2;
          final int _cursorIndexOfTotalCritical = 3;
          final SessionSummaryDao.DashboardStats _result;
          if (_cursor.moveToFirst()) {
            final int _tmpTotalSessions;
            _tmpTotalSessions = _cursor.getInt(_cursorIndexOfTotalSessions);
            final int _tmpCompletedSessions;
            _tmpCompletedSessions = _cursor.getInt(_cursorIndexOfCompletedSessions);
            final int _tmpTotalFindings;
            _tmpTotalFindings = _cursor.getInt(_cursorIndexOfTotalFindings);
            final int _tmpTotalCritical;
            _tmpTotalCritical = _cursor.getInt(_cursorIndexOfTotalCritical);
            _result = new SessionSummaryDao.DashboardStats(_tmpTotalSessions,_tmpCompletedSessions,_tmpTotalFindings,_tmpTotalCritical);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
