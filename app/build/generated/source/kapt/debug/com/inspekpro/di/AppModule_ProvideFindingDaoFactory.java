package com.inspekpro.di;

import com.inspekpro.data.local.dao.InspectionFindingDao;
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
public final class AppModule_ProvideFindingDaoFactory implements Factory<InspectionFindingDao> {
  private final Provider<InspekProDatabase> dbProvider;

  public AppModule_ProvideFindingDaoFactory(Provider<InspekProDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public InspectionFindingDao get() {
    return provideFindingDao(dbProvider.get());
  }

  public static AppModule_ProvideFindingDaoFactory create(Provider<InspekProDatabase> dbProvider) {
    return new AppModule_ProvideFindingDaoFactory(dbProvider);
  }

  public static InspectionFindingDao provideFindingDao(InspekProDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFindingDao(db));
  }
}
