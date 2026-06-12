package com.inspekpro.ui.viewmodel;

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
public final class SessionListViewModel_Factory implements Factory<SessionListViewModel> {
  private final Provider<InspectionSessionRepository> sessionRepoProvider;

  public SessionListViewModel_Factory(Provider<InspectionSessionRepository> sessionRepoProvider) {
    this.sessionRepoProvider = sessionRepoProvider;
  }

  @Override
  public SessionListViewModel get() {
    return newInstance(sessionRepoProvider.get());
  }

  public static SessionListViewModel_Factory create(
      Provider<InspectionSessionRepository> sessionRepoProvider) {
    return new SessionListViewModel_Factory(sessionRepoProvider);
  }

  public static SessionListViewModel newInstance(InspectionSessionRepository sessionRepo) {
    return new SessionListViewModel(sessionRepo);
  }
}
