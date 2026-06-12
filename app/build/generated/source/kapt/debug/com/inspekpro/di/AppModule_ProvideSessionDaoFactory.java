package com.inspekpro.di;

import com.inspekpro.data.local.dao.InspectionSessionDao;
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
public final class AppModule_ProvideSessionDaoFactory implements Factory<InspectionSessionDao> {
  private final Provider<InspekProDatabase> dbProvider;

  public AppModule_ProvideSessionDaoFactory(Provider<InspekProDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public InspectionSessionDao get() {
    return provideSessionDao(dbProvider.get());
  }

  public static AppModule_ProvideSessionDaoFactory create(Provider<InspekProDatabase> dbProvider) {
    return new AppModule_ProvideSessionDaoFactory(dbProvider);
  }

  public static InspectionSessionDao provideSessionDao(InspekProDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSessionDao(db));
  }
}
