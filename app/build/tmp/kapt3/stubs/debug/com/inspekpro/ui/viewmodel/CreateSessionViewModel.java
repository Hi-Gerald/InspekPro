package com.inspekpro.ui.viewmodel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\u000fJ\u001e\u0010\"\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010&\u001a\u00020\u000fJ\u0006\u0010\'\u001a\u00020 R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\rR\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011R\u0017\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\r\u00a8\u0006("}, d2 = {"Lcom/inspekpro/ui/viewmodel/CreateSessionViewModel;", "Landroidx/lifecycle/ViewModel;", "sessionRepo", "Lcom/inspekpro/data/repository/InspectionSessionRepository;", "(Lcom/inspekpro/data/repository/InspectionSessionRepository;)V", "_createResult", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/inspekpro/ui/viewmodel/CreateSessionResult;", "_weatherPreview", "Lcom/inspekpro/ui/viewmodel/WeatherUiState;", "createResult", "Lkotlinx/coroutines/flow/StateFlow;", "getCreateResult", "()Lkotlinx/coroutines/flow/StateFlow;", "inspectorName", "", "getInspectorName", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "isFormValid", "", "locationName", "getLocationName", "notes", "getNotes", "scheduledDate", "", "getScheduledDate", "title", "getTitle", "weatherPreview", "getWeatherPreview", "createSession", "", "inspectorId", "onLocationSelected", "lat", "", "lon", "cityName", "resetForm", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class CreateSessionViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.repository.InspectionSessionRepository sessionRepo = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> title = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> locationName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> inspectorName = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> scheduledDate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> notes = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.inspekpro.ui.viewmodel.WeatherUiState> _weatherPreview = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.WeatherUiState> weatherPreview = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.inspekpro.ui.viewmodel.CreateSessionResult> _createResult = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.CreateSessionResult> createResult = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFormValid = null;
    
    @javax.inject.Inject()
    public CreateSessionViewModel(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.repository.InspectionSessionRepository sessionRepo) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getTitle() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getLocationName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getInspectorName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Long> getScheduledDate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> getNotes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.WeatherUiState> getWeatherPreview() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.inspekpro.ui.viewmodel.CreateSessionResult> getCreateResult() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isFormValid() {
        return null;
    }
    
    public final void onLocationSelected(double lat, double lon, @org.jetbrains.annotations.NotNull()
    java.lang.String cityName) {
    }
    
    public final void createSession(@org.jetbrains.annotations.NotNull()
    java.lang.String inspectorId) {
    }
    
    public final void resetForm() {
    }
}