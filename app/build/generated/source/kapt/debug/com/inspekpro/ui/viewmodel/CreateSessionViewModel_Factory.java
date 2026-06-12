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
public final class CreateSessionViewModel_Factory implements Factory<CreateSessionViewModel> {
  private final Provider<InspectionSessionRepository> sessionRepoProvider;

  public CreateSessionViewModel_Factory(Provider<InspectionSessionRepository> sessionRepoProvider) {
    this.sessionRepoProvider = sessionRepoProvider;
  }

  @Override
  public CreateSessionViewModel get() {
    return newInstance(sessionRepoProvider.get());
  }

  public static CreateSessionViewModel_Factory create(
      Provider<InspectionSessionRepository> sessionRepoProvider) {
    return new CreateSessionViewModel_Factory(sessionRepoProvider);
  }

  public static CreateSessionViewModel newInstance(InspectionSessionRepository sessionRepo) {
    return new CreateSessionViewModel(sessionRepo);
  }
}
