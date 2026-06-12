package com.inspekpro.di;

import com.inspekpro.data.local.database.InspekProDatabase;
import com.inspekpro.data.repository.FindingRepository;
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
public final class AppModule_ProvideFindingRepositoryFactory implements Factory<FindingRepository> {
  private final Provider<InspekProDatabase> dbProvider;

  private final Provider<InspectionSessionRepository> sessionRepoProvider;

  public AppModule_ProvideFindingRepositoryFactory(Provider<InspekProDatabase> dbProvider,
      Provider<InspectionSessionRepository> sessionRepoProvider) {
    this.dbProvider = dbProvider;
    this.sessionRepoProvider = sessionRepoProvider;
  }

  @Override
  public FindingRepository get() {
    return provideFindingRepository(dbProvider.get(), sessionRepoProvider.get());
  }

  public static AppModule_ProvideFindingRepositoryFactory create(
      Provider<InspekProDatabase> dbProvider,
      Provider<InspectionSessionRepository> sessionRepoProvider) {
    return new AppModule_ProvideFindingRepositoryFactory(dbProvider, sessionRepoProvider);
  }

  public static FindingRepository provideFindingRepository(InspekProDatabase db,
      InspectionSessionRepository sessionRepo) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideFindingRepository(db, sessionRepo));
  }
}
