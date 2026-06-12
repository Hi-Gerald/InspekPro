package com.inspekpro.di;

import com.inspekpro.data.remote.api.WeatherApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AppModule_ProvideWeatherApiFactory implements Factory<WeatherApiService> {
  @Override
  public WeatherApiService get() {
    return provideWeatherApi();
  }

  public static AppModule_ProvideWeatherApiFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static WeatherApiService provideWeatherApi() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideWeatherApi());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideWeatherApiFactory INSTANCE = new AppModule_ProvideWeatherApiFactory();
  }
}
