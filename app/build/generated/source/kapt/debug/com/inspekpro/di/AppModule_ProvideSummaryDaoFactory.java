package com.inspekpro.di;

import com.inspekpro.data.local.dao.SessionSummaryDao;
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
public final class AppModule_ProvideSummaryDaoFactory implements Factory<SessionSummaryDao> {
  private final Provider<InspekProDatabase> dbProvider;

  public AppModule_ProvideSummaryDaoFactory(Provider<InspekProDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SessionSummaryDao get() {
    return provideSummaryDao(dbProvider.get());
  }

  public static AppModule_ProvideSummaryDaoFactory create(Provider<InspekProDatabase> dbProvider) {
    return new AppModule_ProvideSummaryDaoFactory(dbProvider);
  }

  public static SessionSummaryDao provideSummaryDao(InspekProDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSummaryDao(db));
  }
}
