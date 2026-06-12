package com.inspekpro.data.remote.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0004\bf\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010J<\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u0007\u001a\u00020\u00062\b\b\u0003\u0010\b\u001a\u00020\u00062\b\b\u0003\u0010\t\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\nJF\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\f\u001a\u00020\r2\b\b\u0001\u0010\u000e\u001a\u00020\r2\b\b\u0003\u0010\u0007\u001a\u00020\u00062\b\b\u0003\u0010\b\u001a\u00020\u00062\b\b\u0003\u0010\t\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u000f\u00a8\u0006\u0011"}, d2 = {"Lcom/inspekpro/data/remote/api/WeatherApiService;", "", "getWeatherByCity", "Lretrofit2/Response;", "Lcom/inspekpro/data/remote/model/WeatherResponse;", "cityName", "", "apiKey", "units", "lang", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWeatherByCoordinates", "lat", "", "lon", "(DDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_debug"})
public abstract interface WeatherApiService {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String API_KEY = "YOUR_OPENWEATHERMAP_API_KEY";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ICON_BASE_URL = "https://openweathermap.org/img/wn/";
    @org.jetbrains.annotations.NotNull()
    public static final com.inspekpro.data.remote.api.WeatherApiService.Companion Companion = null;
    
    /**
     * Ambil cuaca berdasarkan koordinat GPS (dipakai saat sesi baru dibuat dengan lokasi GPS)
     * units=metric → suhu langsung Celsius
     * lang=id      → deskripsi dalam Bahasa Indonesia
     */
    @retrofit2.http.GET(value = "weather")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWeatherByCoordinates(@retrofit2.http.Query(value = "lat")
    double lat, @retrofit2.http.Query(value = "lon")
    double lon, @retrofit2.http.Query(value = "appid")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Query(value = "units")
    @org.jetbrains.annotations.NotNull()
    java.lang.String units, @retrofit2.http.Query(value = "lang")
    @org.jetbrains.annotations.NotNull()
    java.lang.String lang, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.inspekpro.data.remote.model.WeatherResponse>> $completion);
    
    /**
     * Ambil cuaca berdasarkan nama kota (dipakai saat user isi lokasi manual)
     */
    @retrofit2.http.GET(value = "weather")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWeatherByCity(@retrofit2.http.Query(value = "q")
    @org.jetbrains.annotations.NotNull()
    java.lang.String cityName, @retrofit2.http.Query(value = "appid")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Query(value = "units")
    @org.jetbrains.annotations.NotNull()
    java.lang.String units, @retrofit2.http.Query(value = "lang")
    @org.jetbrains.annotations.NotNull()
    java.lang.String lang, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.inspekpro.data.remote.model.WeatherResponse>> $completion);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/inspekpro/data/remote/api/WeatherApiService$Companion;", "", "()V", "API_KEY", "", "BASE_URL", "ICON_BASE_URL", "app_debug"})
    public static final class Companion {
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String API_KEY = "YOUR_OPENWEATHERMAP_API_KEY";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String BASE_URL = "https://api.openweathermap.org/data/2.5/";
        @org.jetbrains.annotations.NotNull()
        public static final java.lang.String ICON_BASE_URL = "https://openweathermap.org/img/wn/";
        
        private Companion() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}