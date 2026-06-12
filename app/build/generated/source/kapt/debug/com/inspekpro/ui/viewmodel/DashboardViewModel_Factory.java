package com.inspekpro.ui.viewmodel;

import com.inspekpro.data.repository.FindingRepository;
import com.inspekpro.data.repository.InspectionSessionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<InspectionSessionRepository> sessionRepoProvider;

  private final Provider<FindingRepository> findingRepoProvider;

  public DashboardViewModel_Factory(Provider<InspectionSessionRepository> sessionRepoProvider,
      Provider<FindingRepository> findingRepoProvider) {
    this.sessionRepoProvider = sessionRepoProvider;
    this.findingRepoProvider = findingRepoProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(sessionRepoProvider.get(), findingRepoProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<InspectionSessionRepository> sessionRepoProvider,
      Provider<FindingRepository> findingRepoProvider) {
    return new DashboardViewModel_Factory(sessionRepoProvider, findingRepoProvider);
  }

  public static DashboardViewModel newInstance(InspectionSessionRepository sessionRepo,
      FindingRepository findingRepo) {
    return new DashboardViewModel(sessionRepo, findingRepo);
  }
}
