package com.inspekpro.ui.viewmodel;

import androidx.lifecycle.SavedStateHandle;
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
public final class SessionDetailViewModel_Factory implements Factory<SessionDetailViewModel> {
  private final Provider<InspectionSessionRepository> sessionRepoProvider;

  private final Provider<FindingRepository> findingRepoProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public SessionDetailViewModel_Factory(Provider<InspectionSessionRepository> sessionRepoProvider,
      Provider<FindingRepository> findingRepoProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.sessionRepoProvider = sessionRepoProvider;
    this.findingRepoProvider = findingRepoProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public SessionDetailViewModel get() {
    return newInstance(sessionRepoProvider.get(), findingRepoProvider.get(), savedStateHandleProvider.get());
  }

  public static SessionDetailViewModel_Factory create(
      Provider<InspectionSessionRepository> sessionRepoProvider,
      Provider<FindingRepository> findingRepoProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new SessionDetailViewModel_Factory(sessionRepoProvider, findingRepoProvider, savedStateHandleProvider);
  }

  public static SessionDetailViewModel newInstance(InspectionSessionRepository sessionRepo,
      FindingRepository findingRepo, SavedStateHandle savedStateHandle) {
    return new SessionDetailViewModel(sessionRepo, findingRepo, savedStateHandle);
  }
}
