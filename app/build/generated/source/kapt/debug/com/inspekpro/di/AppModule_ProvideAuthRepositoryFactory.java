package com.inspekpro.di;

import com.inspekpro.data.local.database.InspekProDatabase;
import com.inspekpro.data.repository.AuthRepository;
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
public final class AppModule_ProvideAuthRepositoryFactory implements Factory<AuthRepository> {
  private final Provider<InspekProDatabase> dbProvider;

  public AppModule_ProvideAuthRepositoryFactory(Provider<InspekProDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AuthRepository get() {
    return provideAuthRepository(dbProvider.get());
  }

  public static AppModule_ProvideAuthRepositoryFactory create(
      Provider<InspekProDatabase> dbProvider) {
    return new AppModule_ProvideAuthRepositoryFactory(dbProvider);
  }

  public static AuthRepository provideAuthRepository(InspekProDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAuthRepository(db));
  }
}
