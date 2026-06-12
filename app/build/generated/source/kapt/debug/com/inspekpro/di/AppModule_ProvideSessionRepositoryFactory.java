package com.inspekpro.di;

import com.inspekpro.data.local.database.InspekProDatabase;
import com.inspekpro.data.remote.api.WeatherApiService;
import com.inspekpro.data.repository.InspectionSessionRepository;
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
public final class AppModule_ProvideSessionRepositoryFactory implements Factory<InspectionSessionRepository> {
  private final Provider<InspekProDatabase> dbProvider;

  private final Provider<WeatherApiService> weatherApiProvider;

  public AppModule_ProvideSessionRepositoryFactory(Provider<InspekProDatabase> dbProvider,
      Provider<WeatherApiService> weatherApiProvider) {
    this.dbProvider = dbProvider;
    this.weatherApiProvider = weatherApiProvider;
  }

  @Override
  public InspectionSessionRepository get() {
    return provideSessionRepository(dbProvider.get(), weatherApiProvider.get());
  }

  public static AppModule_ProvideSessionRepositoryFactory create(
      Provider<InspekProDatabase> dbProvider, Provider<WeatherApiService> weatherApiProvider) {
    return new AppModule_ProvideSessionRepositoryFactory(dbProvider, weatherApiProvider);
  }

  public static InspectionSessionRepository provideSessionRepository(InspekProDatabase db,
      WeatherApiService weatherApi) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideSessionRepository(db, weatherApi));
  }
}
