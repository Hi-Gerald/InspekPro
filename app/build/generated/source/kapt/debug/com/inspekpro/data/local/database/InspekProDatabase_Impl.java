package com.inspekpro.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.inspekpro.data.local.dao.ChecklistDao;
import com.inspekpro.data.local.dao.ChecklistDao_Impl;
import com.inspekpro.data.local.dao.FindingPhotoDao;
import com.inspekpro.data.local.dao.FindingPhotoDao_Impl;
import com.inspekpro.data.local.dao.InspectionFindingDao;
import com.inspekpro.data.local.dao.InspectionFindingDao_Impl;
import com.inspekpro.data.local.dao.InspectionSessionDao;
import com.inspekpro.data.local.dao.InspectionSessionDao_Impl;
import com.inspekpro.data.local.dao.SessionSummaryDao;
import com.inspekpro.data.local.dao.SessionSummaryDao_Impl;
import com.inspekpro.data.local.dao.UserDao;
import com.inspekpro.data.local.dao.UserDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class InspekProDatabase_Impl extends InspekProDatabase {
  private volatile InspectionSessionDao _inspectionSessionDao;

  private volatile InspectionFindingDao _inspectionFindingDao;

  private volatile ChecklistDao _checklistDao;

  private volatile FindingPhotoDao _findingPhotoDao;

  private volatile SessionSummaryDao _sessionSummaryDao;

  private volatile UserDao _userDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `inspection_sessions` (`session_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `session_code` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `location_name` TEXT NOT NULL, `latitude` REAL, `longitude` REAL, `inspector_name` TEXT NOT NULL, `inspector_id` TEXT NOT NULL, `status` TEXT NOT NULL, `scheduled_date` INTEGER NOT NULL, `start_time` INTEGER, `end_time` INTEGER, `weather_condition` TEXT, `weather_temp_celsius` REAL, `weather_humidity` INTEGER, `weather_wind_speed` REAL, `weather_icon` TEXT, `total_items` INTEGER NOT NULL, `passed_items` INTEGER NOT NULL, `failed_items` INTEGER NOT NULL, `notes` TEXT NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, `is_synced` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `inspection_findings` (`finding_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `session_id` INTEGER NOT NULL, `checklist_item_id` INTEGER, `finding_code` TEXT NOT NULL, `category` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `severity` TEXT NOT NULL, `status` TEXT NOT NULL, `result` TEXT NOT NULL, `location_detail` TEXT NOT NULL, `recommendation` TEXT NOT NULL, `due_date` INTEGER, `assigned_to` TEXT NOT NULL, `photo_paths` TEXT NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, FOREIGN KEY(`session_id`) REFERENCES `inspection_sessions`(`session_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`checklist_item_id`) REFERENCES `checklist_items`(`item_id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_inspection_findings_session_id` ON `inspection_findings` (`session_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_inspection_findings_checklist_item_id` ON `inspection_findings` (`checklist_item_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `checklist_templates` (`template_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `inspection_type` TEXT NOT NULL, `version` TEXT NOT NULL, `is_active` INTEGER NOT NULL, `created_at` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `checklist_items` (`item_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `template_id` INTEGER NOT NULL, `item_code` TEXT NOT NULL, `category` TEXT NOT NULL, `sub_category` TEXT NOT NULL, `question` TEXT NOT NULL, `description` TEXT NOT NULL, `reference_standard` TEXT NOT NULL, `is_mandatory` INTEGER NOT NULL, `weight` INTEGER NOT NULL, `sort_order` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, FOREIGN KEY(`template_id`) REFERENCES `checklist_templates`(`template_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_checklist_items_template_id` ON `checklist_items` (`template_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `finding_photos` (`photo_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `finding_id` INTEGER NOT NULL, `local_path` TEXT NOT NULL, `remote_url` TEXT, `caption` TEXT NOT NULL, `is_uploaded` INTEGER NOT NULL, `file_size_bytes` INTEGER NOT NULL, `taken_at` INTEGER NOT NULL, FOREIGN KEY(`finding_id`) REFERENCES `inspection_findings`(`finding_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_finding_photos_finding_id` ON `finding_photos` (`finding_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `session_summaries` (`summary_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `session_id` INTEGER NOT NULL, `total_findings` INTEGER NOT NULL, `critical_count` INTEGER NOT NULL, `major_count` INTEGER NOT NULL, `minor_count` INTEGER NOT NULL, `observation_count` INTEGER NOT NULL, `pass_count` INTEGER NOT NULL, `fail_count` INTEGER NOT NULL, `na_count` INTEGER NOT NULL, `compliance_score` REAL NOT NULL, `open_findings` INTEGER NOT NULL, `resolved_findings` INTEGER NOT NULL, `duration_minutes` INTEGER NOT NULL, `overall_grade` TEXT NOT NULL, `generated_at` INTEGER NOT NULL, FOREIGN KEY(`session_id`) REFERENCES `inspection_sessions`(`session_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_session_summaries_session_id` ON `session_summaries` (`session_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `full_name` TEXT NOT NULL, `email` TEXT NOT NULL, `company_name` TEXT NOT NULL, `password_hash` TEXT NOT NULL, `is_logged_in` INTEGER NOT NULL, `created_at` INTEGER NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_users_email` ON `users` (`email`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1d9b3b529d7a963e925f51aadd6cd30d')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `inspection_sessions`");
        db.execSQL("DROP TABLE IF EXISTS `inspection_findings`");
        db.execSQL("DROP TABLE IF EXISTS `checklist_templates`");
        db.execSQL("DROP TABLE IF EXISTS `checklist_items`");
        db.execSQL("DROP TABLE IF EXISTS `finding_photos`");
        db.execSQL("DROP TABLE IF EXISTS `session_summaries`");
        db.execSQL("DROP TABLE IF EXISTS `users`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsInspectionSessions = new HashMap<String, TableInfo.Column>(25);
        _columnsInspectionSessions.put("session_id", new TableInfo.Column("session_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("session_code", new TableInfo.Column("session_code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("location_name", new TableInfo.Column("location_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("latitude", new TableInfo.Column("latitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("longitude", new TableInfo.Column("longitude", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("inspector_name", new TableInfo.Column("inspector_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("inspector_id", new TableInfo.Column("inspector_id", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("scheduled_date", new TableInfo.Column("scheduled_date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("start_time", new TableInfo.Column("start_time", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("end_time", new TableInfo.Column("end_time", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("weather_condition", new TableInfo.Column("weather_condition", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("weather_temp_celsius", new TableInfo.Column("weather_temp_celsius", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("weather_humidity", new TableInfo.Column("weather_humidity", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("weather_wind_speed", new TableInfo.Column("weather_wind_speed", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("weather_icon", new TableInfo.Column("weather_icon", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("total_items", new TableInfo.Column("total_items", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("passed_items", new TableInfo.Column("passed_items", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("failed_items", new TableInfo.Column("failed_items", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("notes", new TableInfo.Column("notes", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionSessions.put("is_synced", new TableInfo.Column("is_synced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInspectionSessions = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesInspectionSessions = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoInspectionSessions = new TableInfo("inspection_sessions", _columnsInspectionSessions, _foreignKeysInspectionSessions, _indicesInspectionSessions);
        final TableInfo _existingInspectionSessions = TableInfo.read(db, "inspection_sessions");
        if (!_infoInspectionSessions.equals(_existingInspectionSessions)) {
          return new RoomOpenHelper.ValidationResult(false, "inspection_sessions(com.inspekpro.data.local.entity.InspectionSessionEntity).\n"
                  + " Expected:\n" + _infoInspectionSessions + "\n"
                  + " Found:\n" + _existingInspectionSessions);
        }
        final HashMap<String, TableInfo.Column> _columnsInspectionFindings = new HashMap<String, TableInfo.Column>(17);
        _columnsInspectionFindings.put("finding_id", new TableInfo.Column("finding_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("session_id", new TableInfo.Column("session_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("checklist_item_id", new TableInfo.Column("checklist_item_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("finding_code", new TableInfo.Column("finding_code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("result", new TableInfo.Column("result", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("location_detail", new TableInfo.Column("location_detail", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("recommendation", new TableInfo.Column("recommendation", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("due_date", new TableInfo.Column("due_date", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("assigned_to", new TableInfo.Column("assigned_to", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("photo_paths", new TableInfo.Column("photo_paths", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsInspectionFindings.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysInspectionFindings = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysInspectionFindings.add(new TableInfo.ForeignKey("inspection_sessions", "CASCADE", "NO ACTION", Arrays.asList("session_id"), Arrays.asList("session_id")));
        _foreignKeysInspectionFindings.add(new TableInfo.ForeignKey("checklist_items", "SET NULL", "NO ACTION", Arrays.asList("checklist_item_id"), Arrays.asList("item_id")));
        final HashSet<TableInfo.Index> _indicesInspectionFindings = new HashSet<TableInfo.Index>(2);
        _indicesInspectionFindings.add(new TableInfo.Index("index_inspection_findings_session_id", false, Arrays.asList("session_id"), Arrays.asList("ASC")));
        _indicesInspectionFindings.add(new TableInfo.Index("index_inspection_findings_checklist_item_id", false, Arrays.asList("checklist_item_id"), Arrays.asList("ASC")));
        final TableInfo _infoInspectionFindings = new TableInfo("inspection_findings", _columnsInspectionFindings, _foreignKeysInspectionFindings, _indicesInspectionFindings);
        final TableInfo _existingInspectionFindings = TableInfo.read(db, "inspection_findings");
        if (!_infoInspectionFindings.equals(_existingInspectionFindings)) {
          return new RoomOpenHelper.ValidationResult(false, "inspection_findings(com.inspekpro.data.local.entity.InspectionFindingEntity).\n"
                  + " Expected:\n" + _infoInspectionFindings + "\n"
                  + " Found:\n" + _existingInspectionFindings);
        }
        final HashMap<String, TableInfo.Column> _columnsChecklistTemplates = new HashMap<String, TableInfo.Column>(7);
        _columnsChecklistTemplates.put("template_id", new TableInfo.Column("template_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistTemplates.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistTemplates.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistTemplates.put("inspection_type", new TableInfo.Column("inspection_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistTemplates.put("version", new TableInfo.Column("version", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistTemplates.put("is_active", new TableInfo.Column("is_active", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistTemplates.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChecklistTemplates = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesChecklistTemplates = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoChecklistTemplates = new TableInfo("checklist_templates", _columnsChecklistTemplates, _foreignKeysChecklistTemplates, _indicesChecklistTemplates);
        final TableInfo _existingChecklistTemplates = TableInfo.read(db, "checklist_templates");
        if (!_infoChecklistTemplates.equals(_existingChecklistTemplates)) {
          return new RoomOpenHelper.ValidationResult(false, "checklist_templates(com.inspekpro.data.local.entity.ChecklistTemplateEntity).\n"
                  + " Expected:\n" + _infoChecklistTemplates + "\n"
                  + " Found:\n" + _existingChecklistTemplates);
        }
        final HashMap<String, TableInfo.Column> _columnsChecklistItems = new HashMap<String, TableInfo.Column>(12);
        _columnsChecklistItems.put("item_id", new TableInfo.Column("item_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("template_id", new TableInfo.Column("template_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("item_code", new TableInfo.Column("item_code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("sub_category", new TableInfo.Column("sub_category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("question", new TableInfo.Column("question", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("reference_standard", new TableInfo.Column("reference_standard", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("is_mandatory", new TableInfo.Column("is_mandatory", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("weight", new TableInfo.Column("weight", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("sort_order", new TableInfo.Column("sort_order", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsChecklistItems.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysChecklistItems = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysChecklistItems.add(new TableInfo.ForeignKey("checklist_templates", "CASCADE", "NO ACTION", Arrays.asList("template_id"), Arrays.asList("template_id")));
        final HashSet<TableInfo.Index> _indicesChecklistItems = new HashSet<TableInfo.Index>(1);
        _indicesChecklistItems.add(new TableInfo.Index("index_checklist_items_template_id", false, Arrays.asList("template_id"), Arrays.asList("ASC")));
        final TableInfo _infoChecklistItems = new TableInfo("checklist_items", _columnsChecklistItems, _foreignKeysChecklistItems, _indicesChecklistItems);
        final TableInfo _existingChecklistItems = TableInfo.read(db, "checklist_items");
        if (!_infoChecklistItems.equals(_existingChecklistItems)) {
          return new RoomOpenHelper.ValidationResult(false, "checklist_items(com.inspekpro.data.local.entity.ChecklistItemEntity).\n"
                  + " Expected:\n" + _infoChecklistItems + "\n"
                  + " Found:\n" + _existingChecklistItems);
        }
        final HashMap<String, TableInfo.Column> _columnsFindingPhotos = new HashMap<String, TableInfo.Column>(8);
        _columnsFindingPhotos.put("photo_id", new TableInfo.Column("photo_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindingPhotos.put("finding_id", new TableInfo.Column("finding_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindingPhotos.put("local_path", new TableInfo.Column("local_path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindingPhotos.put("remote_url", new TableInfo.Column("remote_url", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindingPhotos.put("caption", new TableInfo.Column("caption", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindingPhotos.put("is_uploaded", new TableInfo.Column("is_uploaded", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindingPhotos.put("file_size_bytes", new TableInfo.Column("file_size_bytes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFindingPhotos.put("taken_at", new TableInfo.Column("taken_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFindingPhotos = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysFindingPhotos.add(new TableInfo.ForeignKey("inspection_findings", "CASCADE", "NO ACTION", Arrays.asList("finding_id"), Arrays.asList("finding_id")));
        final HashSet<TableInfo.Index> _indicesFindingPhotos = new HashSet<TableInfo.Index>(1);
        _indicesFindingPhotos.add(new TableInfo.Index("index_finding_photos_finding_id", false, Arrays.asList("finding_id"), Arrays.asList("ASC")));
        final TableInfo _infoFindingPhotos = new TableInfo("finding_photos", _columnsFindingPhotos, _foreignKeysFindingPhotos, _indicesFindingPhotos);
        final TableInfo _existingFindingPhotos = TableInfo.read(db, "finding_photos");
        if (!_infoFindingPhotos.equals(_existingFindingPhotos)) {
          return new RoomOpenHelper.ValidationResult(false, "finding_photos(com.inspekpro.data.local.entity.FindingPhotoEntity).\n"
                  + " Expected:\n" + _infoFindingPhotos + "\n"
                  + " Found:\n" + _existingFindingPhotos);
        }
        final HashMap<String, TableInfo.Column> _columnsSessionSummaries = new HashMap<String, TableInfo.Column>(16);
        _columnsSessionSummaries.put("summary_id", new TableInfo.Column("summary_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("session_id", new TableInfo.Column("session_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("total_findings", new TableInfo.Column("total_findings", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("critical_count", new TableInfo.Column("critical_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("major_count", new TableInfo.Column("major_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("minor_count", new TableInfo.Column("minor_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("observation_count", new TableInfo.Column("observation_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("pass_count", new TableInfo.Column("pass_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("fail_count", new TableInfo.Column("fail_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("na_count", new TableInfo.Column("na_count", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("compliance_score", new TableInfo.Column("compliance_score", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("open_findings", new TableInfo.Column("open_findings", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("resolved_findings", new TableInfo.Column("resolved_findings", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("duration_minutes", new TableInfo.Column("duration_minutes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("overall_grade", new TableInfo.Column("overall_grade", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionSummaries.put("generated_at", new TableInfo.Column("generated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSessionSummaries = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSessionSummaries.add(new TableInfo.ForeignKey("inspection_sessions", "CASCADE", "NO ACTION", Arrays.asList("session_id"), Arrays.asList("session_id")));
        final HashSet<TableInfo.Index> _indicesSessionSummaries = new HashSet<TableInfo.Index>(1);
        _indicesSessionSummaries.add(new TableInfo.Index("index_session_summaries_session_id", true, Arrays.asList("session_id"), Arrays.asList("ASC")));
        final TableInfo _infoSessionSummaries = new TableInfo("session_summaries", _columnsSessionSummaries, _foreignKeysSessionSummaries, _indicesSessionSummaries);
        final TableInfo _existingSessionSummaries = TableInfo.read(db, "session_summaries");
        if (!_infoSessionSummaries.equals(_existingSessionSummaries)) {
          return new RoomOpenHelper.ValidationResult(false, "session_summaries(com.inspekpro.data.local.entity.SessionSummaryEntity).\n"
                  + " Expected:\n" + _infoSessionSummaries + "\n"
                  + " Found:\n" + _existingSessionSummaries);
        }
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(7);
        _columnsUsers.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("full_name", new TableInfo.Column("full_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("company_name", new TableInfo.Column("company_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password_hash", new TableInfo.Column("password_hash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("is_logged_in", new TableInfo.Column("is_logged_in", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(1);
        _indicesUsers.add(new TableInfo.Index("index_users_email", true, Arrays.asList("email"), Arrays.asList("ASC")));
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.inspekpro.data.local.entity.UserEntity).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1d9b3b529d7a963e925f51aadd6cd30d", "050eb4f6201b24a7e8f16aaec6eb5504");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "inspection_sessions","inspection_findings","checklist_templates","checklist_items","finding_photos","session_summaries","users");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `inspection_sessions`");
      _db.execSQL("DELETE FROM `inspection_findings`");
      _db.execSQL("DELETE FROM `checklist_templates`");
      _db.execSQL("DELETE FROM `checklist_items`");
      _db.execSQL("DELETE FROM `finding_photos`");
      _db.execSQL("DELETE FROM `session_summaries`");
      _db.execSQL("DELETE FROM `users`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(InspectionSessionDao.class, InspectionSessionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(InspectionFindingDao.class, InspectionFindingDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ChecklistDao.class, ChecklistDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FindingPhotoDao.class, FindingPhotoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SessionSummaryDao.class, SessionSummaryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public InspectionSessionDao inspectionSessionDao() {
    if (_inspectionSessionDao != null) {
      return _inspectionSessionDao;
    } else {
      synchronized(this) {
        if(_inspectionSessionDao == null) {
          _inspectionSessionDao = new InspectionSessionDao_Impl(this);
        }
        return _inspectionSessionDao;
      }
    }
  }

  @Override
  public InspectionFindingDao inspectionFindingDao() {
    if (_inspectionFindingDao != null) {
      return _inspectionFindingDao;
    } else {
      synchronized(this) {
        if(_inspectionFindingDao == null) {
          _inspectionFindingDao = new InspectionFindingDao_Impl(this);
        }
        return _inspectionFindingDao;
      }
    }
  }

  @Override
  public ChecklistDao checklistDao() {
    if (_checklistDao != null) {
      return _checklistDao;
    } else {
      synchronized(this) {
        if(_checklistDao == null) {
          _checklistDao = new ChecklistDao_Impl(this);
        }
        return _checklistDao;
      }
    }
  }

  @Override
  public FindingPhotoDao findingPhotoDao() {
    if (_findingPhotoDao != null) {
      return _findingPhotoDao;
    } else {
      synchronized(this) {
        if(_findingPhotoDao == null) {
          _findingPhotoDao = new FindingPhotoDao_Impl(this);
        }
        return _findingPhotoDao;
      }
    }
  }

  @Override
  public SessionSummaryDao sessionSummaryDao() {
    if (_sessionSummaryDao != null) {
      return _sessionSummaryDao;
    } else {
      synchronized(this) {
        if(_sessionSummaryDao == null) {
          _sessionSummaryDao = new SessionSummaryDao_Impl(this);
        }
        return _sessionSummaryDao;
      }
    }
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }
}
