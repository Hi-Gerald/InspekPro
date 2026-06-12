package com.inspekpro.di;

import com.inspekpro.data.local.dao.ChecklistDao;
import com.inspekpro.data.local.database.InspekProDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AppModule_ProvideChecklistDaoFactory implements Factory<ChecklistDao> {
  private final Provider<InspekProDatabase> dbProvider;

  public AppModule_ProvideChecklistDaoFactory(Provider<InspekProDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public ChecklistDao get() {
    return provideChecklistDao(dbProvider.get());
  }

  public static AppModule_ProvideChecklistDaoFactory create(
      Provider<InspekProDatabase> dbProvider) {
    return new AppModule_ProvideChecklistDaoFactory(dbProvider);
  }

  public static ChecklistDao provideChecklistDao(InspekProDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideChecklistDao(db));
  }
}
