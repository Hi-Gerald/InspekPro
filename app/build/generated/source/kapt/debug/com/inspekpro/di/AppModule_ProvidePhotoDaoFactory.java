package com.inspekpro.di;

import com.inspekpro.data.local.dao.FindingPhotoDao;
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
public final class AppModule_ProvidePhotoDaoFactory implements Factory<FindingPhotoDao> {
  private final Provider<InspekProDatabase> dbProvider;

  public AppModule_ProvidePhotoDaoFactory(Provider<InspekProDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public FindingPhotoDao get() {
    return providePhotoDao(dbProvider.get());
  }

  public static AppModule_ProvidePhotoDaoFactory create(Provider<InspekProDatabase> dbProvider) {
    return new AppModule_ProvidePhotoDaoFactory(dbProvider);
  }

  public static FindingPhotoDao providePhotoDao(InspekProDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePhotoDao(db));
  }
}
