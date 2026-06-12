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
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.inspekpro.data.local.entity.ChecklistItemEntity;
import com.inspekpro.data.local.entity.ChecklistTemplateEntity;
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
public final class ChecklistDao_Impl implements ChecklistDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ChecklistTemplateEntity> __insertionAdapterOfChecklistTemplateEntity;

  private final EntityInsertionAdapter<ChecklistItemEntity> __insertionAdapterOfChecklistItemEntity;

  private final EntityDeletionOrUpdateAdapter<ChecklistItemEntity> __deletionAdapterOfChecklistItemEntity;

  private final EntityDeletionOrUpdateAdapter<ChecklistTemplateEntity> __updateAdapterOfChecklistTemplateEntity;

  private final EntityDeletionOrUpdateAdapter<ChecklistItemEntity> __updateAdapterOfChecklistItemEntity;

  public ChecklistDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfChecklistTemplateEntity = new EntityInsertionAdapter<ChecklistTemplateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `checklist_templates` (`template_id`,`name`,`description`,`inspection_type`,`version`,`is_active`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChecklistTemplateEntity entity) {
        statement.bindLong(1, entity.getTemplateId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getInspectionType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getInspectionType());
        }
        if (entity.getVersion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getVersion());
        }
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getCreatedAt());
      }
    };
    this.__insertionAdapterOfChecklistItemEntity = new EntityInsertionAdapter<ChecklistItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `checklist_items` (`item_id`,`template_id`,`item_code`,`category`,`sub_category`,`question`,`description`,`reference_standard`,`is_mandatory`,`weight`,`sort_order`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChecklistItemEntity entity) {
        statement.bindLong(1, entity.getItemId());
        statement.bindLong(2, entity.getTemplateId());
        if (entity.getItemCode() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getItemCode());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCategory());
        }
        if (entity.getSubCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSubCategory());
        }
        if (entity.getQuestion() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getQuestion());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDescription());
        }
        if (entity.getReferenceStandard() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getReferenceStandard());
        }
        final int _tmp = entity.isMandatory() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getWeight());
        statement.bindLong(11, entity.getSortOrder());
        statement.bindLong(12, entity.getCreatedAt());
      }
    };
    this.__deletionAdapterOfChecklistItemEntity = new EntityDeletionOrUpdateAdapter<ChecklistItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `checklist_items` WHERE `item_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChecklistItemEntity entity) {
        statement.bindLong(1, entity.getItemId());
      }
    };
    this.__updateAdapterOfChecklistTemplateEntity = new EntityDeletionOrUpdateAdapter<ChecklistTemplateEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `checklist_templates` SET `template_id` = ?,`name` = ?,`description` = ?,`inspection_type` = ?,`version` = ?,`is_active` = ?,`created_at` = ? WHERE `template_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChecklistTemplateEntity entity) {
        statement.bindLong(1, entity.getTemplateId());
        if (entity.getName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getName());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getInspectionType() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getInspectionType());
        }
        if (entity.getVersion() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getVersion());
        }
        final int _tmp = entity.isActive() ? 1 : 0;
        statement.bindLong(6, _tmp);
        statement.bindLong(7, entity.getCreatedAt());
        statement.bindLong(8, entity.getTemplateId());
      }
    };
    this.__updateAdapterOfChecklistItemEntity = new EntityDeletionOrUpdateAdapter<ChecklistItemEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `checklist_items` SET `item_id` = ?,`template_id` = ?,`item_code` = ?,`category` = ?,`sub_category` = ?,`question` = ?,`description` = ?,`reference_standard` = ?,`is_mandatory` = ?,`weight` = ?,`sort_order` = ?,`created_at` = ? WHERE `item_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ChecklistItemEntity entity) {
        statement.bindLong(1, entity.getItemId());
        statement.bindLong(2, entity.getTemplateId());
        if (entity.getItemCode() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getItemCode());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getCategory());
        }
        if (entity.getSubCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getSubCategory());
        }
        if (entity.getQuestion() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getQuestion());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getDescription());
        }
        if (entity.getReferenceStandard() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getReferenceStandard());
        }
        final int _tmp = entity.isMandatory() ? 1 : 0;
        statement.bindLong(9, _tmp);
        statement.bindLong(10, entity.getWeight());
        statement.bindLong(11, entity.getSortOrder());
        statement.bindLong(12, entity.getCreatedAt());
        statement.bindLong(13, entity.getItemId());
      }
    };
  }

  @Override
  public Object insertTemplate(final ChecklistTemplateEntity template,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfChecklistTemplateEntity.insertAndReturnId(template);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertItem(final ChecklistItemEntity item,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfChecklistItemEntity.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertItems(final List<ChecklistItemEntity> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfChecklistItemEntity.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteItem(final ChecklistItemEntity item,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfChecklistItemEntity.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTemplate(final ChecklistTemplateEntity template,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfChecklistTemplateEntity.handle(template);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateItem(final ChecklistItemEntity item,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfChecklistItemEntity.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ChecklistTemplateEntity>> getAllActiveTemplates() {
    final String _sql = "SELECT * FROM checklist_templates WHERE is_active = 1 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"checklist_templates"}, new Callable<List<ChecklistTemplateEntity>>() {
      @Override
      @NonNull
      public List<ChecklistTemplateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfInspectionType = CursorUtil.getColumnIndexOrThrow(_cursor, "inspection_type");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<ChecklistTemplateEntity> _result = new ArrayList<ChecklistTemplateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChecklistTemplateEntity _item;
            final long _tmpTemplateId;
            _tmpTemplateId = _cursor.getLong(_cursorIndexOfTemplateId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpInspectionType;
            if (_cursor.isNull(_cursorIndexOfInspectionType)) {
              _tmpInspectionType = null;
            } else {
              _tmpInspectionType = _cursor.getString(_cursorIndexOfInspectionType);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ChecklistTemplateEntity(_tmpTemplateId,_tmpName,_tmpDescription,_tmpInspectionType,_tmpVersion,_tmpIsActive,_tmpCreatedAt);
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
  public Object getTemplateById(final long templateId,
      final Continuation<? super ChecklistTemplateEntity> $completion) {
    final String _sql = "SELECT * FROM checklist_templates WHERE template_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, templateId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ChecklistTemplateEntity>() {
      @Override
      @Nullable
      public ChecklistTemplateEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfInspectionType = CursorUtil.getColumnIndexOrThrow(_cursor, "inspection_type");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final ChecklistTemplateEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpTemplateId;
            _tmpTemplateId = _cursor.getLong(_cursorIndexOfTemplateId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpInspectionType;
            if (_cursor.isNull(_cursorIndexOfInspectionType)) {
              _tmpInspectionType = null;
            } else {
              _tmpInspectionType = _cursor.getString(_cursorIndexOfInspectionType);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _result = new ChecklistTemplateEntity(_tmpTemplateId,_tmpName,_tmpDescription,_tmpInspectionType,_tmpVersion,_tmpIsActive,_tmpCreatedAt);
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
  public Flow<List<ChecklistTemplateEntity>> getTemplatesByType(final String type) {
    final String _sql = "SELECT * FROM checklist_templates WHERE inspection_type = ? AND is_active = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, type);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"checklist_templates"}, new Callable<List<ChecklistTemplateEntity>>() {
      @Override
      @NonNull
      public List<ChecklistTemplateEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfInspectionType = CursorUtil.getColumnIndexOrThrow(_cursor, "inspection_type");
          final int _cursorIndexOfVersion = CursorUtil.getColumnIndexOrThrow(_cursor, "version");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<ChecklistTemplateEntity> _result = new ArrayList<ChecklistTemplateEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChecklistTemplateEntity _item;
            final long _tmpTemplateId;
            _tmpTemplateId = _cursor.getLong(_cursorIndexOfTemplateId);
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpInspectionType;
            if (_cursor.isNull(_cursorIndexOfInspectionType)) {
              _tmpInspectionType = null;
            } else {
              _tmpInspectionType = _cursor.getString(_cursorIndexOfInspectionType);
            }
            final String _tmpVersion;
            if (_cursor.isNull(_cursorIndexOfVersion)) {
              _tmpVersion = null;
            } else {
              _tmpVersion = _cursor.getString(_cursorIndexOfVersion);
            }
            final boolean _tmpIsActive;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp != 0;
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ChecklistTemplateEntity(_tmpTemplateId,_tmpName,_tmpDescription,_tmpInspectionType,_tmpVersion,_tmpIsActive,_tmpCreatedAt);
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
  public Flow<List<ChecklistItemEntity>> getItemsByTemplate(final long templateId) {
    final String _sql = "SELECT * FROM checklist_items WHERE template_id = ? ORDER BY sort_order ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, templateId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"checklist_items"}, new Callable<List<ChecklistItemEntity>>() {
      @Override
      @NonNull
      public List<ChecklistItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "item_id");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_id");
          final int _cursorIndexOfItemCode = CursorUtil.getColumnIndexOrThrow(_cursor, "item_code");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSubCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_category");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfReferenceStandard = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_standard");
          final int _cursorIndexOfIsMandatory = CursorUtil.getColumnIndexOrThrow(_cursor, "is_mandatory");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sort_order");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<ChecklistItemEntity> _result = new ArrayList<ChecklistItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChecklistItemEntity _item;
            final long _tmpItemId;
            _tmpItemId = _cursor.getLong(_cursorIndexOfItemId);
            final long _tmpTemplateId;
            _tmpTemplateId = _cursor.getLong(_cursorIndexOfTemplateId);
            final String _tmpItemCode;
            if (_cursor.isNull(_cursorIndexOfItemCode)) {
              _tmpItemCode = null;
            } else {
              _tmpItemCode = _cursor.getString(_cursorIndexOfItemCode);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpSubCategory;
            if (_cursor.isNull(_cursorIndexOfSubCategory)) {
              _tmpSubCategory = null;
            } else {
              _tmpSubCategory = _cursor.getString(_cursorIndexOfSubCategory);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpReferenceStandard;
            if (_cursor.isNull(_cursorIndexOfReferenceStandard)) {
              _tmpReferenceStandard = null;
            } else {
              _tmpReferenceStandard = _cursor.getString(_cursorIndexOfReferenceStandard);
            }
            final boolean _tmpIsMandatory;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsMandatory);
            _tmpIsMandatory = _tmp != 0;
            final int _tmpWeight;
            _tmpWeight = _cursor.getInt(_cursorIndexOfWeight);
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ChecklistItemEntity(_tmpItemId,_tmpTemplateId,_tmpItemCode,_tmpCategory,_tmpSubCategory,_tmpQuestion,_tmpDescription,_tmpReferenceStandard,_tmpIsMandatory,_tmpWeight,_tmpSortOrder,_tmpCreatedAt);
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
  public Flow<List<ChecklistItemEntity>> getItemsByCategory(final long templateId,
      final String category) {
    final String _sql = "SELECT * FROM checklist_items WHERE template_id = ? AND category = ? ORDER BY sort_order ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, templateId);
    _argIndex = 2;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"checklist_items"}, new Callable<List<ChecklistItemEntity>>() {
      @Override
      @NonNull
      public List<ChecklistItemEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "item_id");
          final int _cursorIndexOfTemplateId = CursorUtil.getColumnIndexOrThrow(_cursor, "template_id");
          final int _cursorIndexOfItemCode = CursorUtil.getColumnIndexOrThrow(_cursor, "item_code");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfSubCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "sub_category");
          final int _cursorIndexOfQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "question");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfReferenceStandard = CursorUtil.getColumnIndexOrThrow(_cursor, "reference_standard");
          final int _cursorIndexOfIsMandatory = CursorUtil.getColumnIndexOrThrow(_cursor, "is_mandatory");
          final int _cursorIndexOfWeight = CursorUtil.getColumnIndexOrThrow(_cursor, "weight");
          final int _cursorIndexOfSortOrder = CursorUtil.getColumnIndexOrThrow(_cursor, "sort_order");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<ChecklistItemEntity> _result = new ArrayList<ChecklistItemEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ChecklistItemEntity _item;
            final long _tmpItemId;
            _tmpItemId = _cursor.getLong(_cursorIndexOfItemId);
            final long _tmpTemplateId;
            _tmpTemplateId = _cursor.getLong(_cursorIndexOfTemplateId);
            final String _tmpItemCode;
            if (_cursor.isNull(_cursorIndexOfItemCode)) {
              _tmpItemCode = null;
            } else {
              _tmpItemCode = _cursor.getString(_cursorIndexOfItemCode);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpSubCategory;
            if (_cursor.isNull(_cursorIndexOfSubCategory)) {
              _tmpSubCategory = null;
            } else {
              _tmpSubCategory = _cursor.getString(_cursorIndexOfSubCategory);
            }
            final String _tmpQuestion;
            if (_cursor.isNull(_cursorIndexOfQuestion)) {
              _tmpQuestion = null;
            } else {
              _tmpQuestion = _cursor.getString(_cursorIndexOfQuestion);
            }
            final String _tmpDescription;
            if (_cursor.isNull(_cursorIndexOfDescription)) {
              _tmpDescription = null;
            } else {
              _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            }
            final String _tmpReferenceStandard;
            if (_cursor.isNull(_cursorIndexOfReferenceStandard)) {
              _tmpReferenceStandard = null;
            } else {
              _tmpReferenceStandard = _cursor.getString(_cursorIndexOfReferenceStandard);
            }
            final boolean _tmpIsMandatory;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsMandatory);
            _tmpIsMandatory = _tmp != 0;
            final int _tmpWeight;
            _tmpWeight = _cursor.getInt(_cursorIndexOfWeight);
            final int _tmpSortOrder;
            _tmpSortOrder = _cursor.getInt(_cursorIndexOfSortOrder);
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            _item = new ChecklistItemEntity(_tmpItemId,_tmpTemplateId,_tmpItemCode,_tmpCategory,_tmpSubCategory,_tmpQuestion,_tmpDescription,_tmpReferenceStandard,_tmpIsMandatory,_tmpWeight,_tmpSortOrder,_tmpCreatedAt);
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
  public Flow<List<String>> getCategoriesByTemplate(final long templateId) {
    final String _sql = "SELECT DISTINCT category FROM checklist_items WHERE template_id = ? ORDER BY category ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, templateId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"checklist_items"}, new Callable<List<String>>() {
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
  public Flow<Integer> getItemCount(final long templateId) {
    final String _sql = "SELECT COUNT(*) FROM checklist_items WHERE template_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, templateId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"checklist_items"}, new Callable<Integer>() {
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
