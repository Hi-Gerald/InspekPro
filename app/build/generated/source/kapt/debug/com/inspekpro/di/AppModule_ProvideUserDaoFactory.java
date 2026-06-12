package com.inspekpro.di;

import com.inspekpro.data.local.dao.UserDao;
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
public final class AppModule_ProvideUserDaoFactory implements Factory<UserDao> {
  private final Provider<InspekProDatabase> dbProvider;

  public AppModule_ProvideUserDaoFactory(Provider<InspekProDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public UserDao get() {
    return provideUserDao(dbProvider.get());
  }

  public static AppModule_ProvideUserDaoFactory create(Provider<InspekProDatabase> dbProvider) {
    return new AppModule_ProvideUserDaoFactory(dbProvider);
  }

  public static UserDao provideUserDao(InspekProDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideUserDao(db));
  }
}
