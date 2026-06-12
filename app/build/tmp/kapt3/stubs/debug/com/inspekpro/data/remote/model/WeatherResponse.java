package com.inspekpro.data.remote.model;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b$\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001Bi\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0012\u0012\u0006\u0010\u0013\u001a\u00020\u0014\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\u0006\u0010\u0017\u001a\u00020\u0010\u00a2\u0006\u0002\u0010\u0018J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0016H\u00c6\u0003J\t\u00100\u001a\u00020\u0010H\u00c6\u0003J\u000f\u00101\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u00102\u001a\u00020\bH\u00c6\u0003J\t\u00103\u001a\u00020\nH\u00c6\u0003J\t\u00104\u001a\u00020\fH\u00c6\u0003J\u000b\u00105\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\t\u00106\u001a\u00020\u0010H\u00c6\u0003J\t\u00107\u001a\u00020\u0012H\u00c6\u0003J\t\u00108\u001a\u00020\u0014H\u00c6\u0003J\u007f\u00109\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0010H\u00c6\u0001J\u0013\u0010:\u001a\u00020;2\b\u0010<\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010=\u001a\u00020\u0010H\u00d6\u0001J\t\u0010>\u001a\u00020\u0016H\u00d6\u0001R\u0016\u0010\u000b\u001a\u00020\f8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0016\u0010\u0017\u001a\u00020\u00108\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0016\u0010\u0011\u001a\u00020\u00128\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0016\u0010\u0015\u001a\u00020\u00168\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0018\u0010\r\u001a\u0004\u0018\u00010\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0016\u0010\u0013\u001a\u00020\u00148\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010(R\u0016\u0010\u000f\u001a\u00020\u00108\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u001cR\u001c\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-\u00a8\u0006?"}, d2 = {"Lcom/inspekpro/data/remote/model/WeatherResponse;", "", "coord", "Lcom/inspekpro/data/remote/model/Coord;", "weather", "", "Lcom/inspekpro/data/remote/model/WeatherDescription;", "main", "Lcom/inspekpro/data/remote/model/MainWeather;", "wind", "Lcom/inspekpro/data/remote/model/Wind;", "clouds", "Lcom/inspekpro/data/remote/model/Clouds;", "rain", "Lcom/inspekpro/data/remote/model/Rain;", "visibility", "", "dt", "", "sys", "Lcom/inspekpro/data/remote/model/Sys;", "name", "", "cod", "(Lcom/inspekpro/data/remote/model/Coord;Ljava/util/List;Lcom/inspekpro/data/remote/model/MainWeather;Lcom/inspekpro/data/remote/model/Wind;Lcom/inspekpro/data/remote/model/Clouds;Lcom/inspekpro/data/remote/model/Rain;IJLcom/inspekpro/data/remote/model/Sys;Ljava/lang/String;I)V", "getClouds", "()Lcom/inspekpro/data/remote/model/Clouds;", "getCod", "()I", "getCoord", "()Lcom/inspekpro/data/remote/model/Coord;", "getDt", "()J", "getMain", "()Lcom/inspekpro/data/remote/model/MainWeather;", "getName", "()Ljava/lang/String;", "getRain", "()Lcom/inspekpro/data/remote/model/Rain;", "getSys", "()Lcom/inspekpro/data/remote/model/Sys;", "getVisibility", "getWeather", "()Ljava/util/List;", "getWind", "()Lcom/inspekpro/data/remote/model/Wind;", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class WeatherResponse {
    @com.google.gson.annotations.SerializedName(value = "coord")
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.remote.model.Coord coord = null;
    @com.google.gson.annotations.SerializedName(value = "weather")
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.inspekpro.data.remote.model.WeatherDescription> weather = null;
    @com.google.gson.annotations.SerializedName(value = "main")
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.remote.model.MainWeather main = null;
    @com.google.gson.annotations.SerializedName(value = "wind")
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.remote.model.Wind wind = null;
    @com.google.gson.annotations.SerializedName(value = "clouds")
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.remote.model.Clouds clouds = null;
    @com.google.gson.annotations.SerializedName(value = "rain")
    @org.jetbrains.annotations.Nullable()
    private final com.inspekpro.data.remote.model.Rain rain = null;
    @com.google.gson.annotations.SerializedName(value = "visibility")
    private final int visibility = 0;
    @com.google.gson.annotations.SerializedName(value = "dt")
    private final long dt = 0L;
    @com.google.gson.annotations.SerializedName(value = "sys")
    @org.jetbrains.annotations.NotNull()
    private final com.inspekpro.data.remote.model.Sys sys = null;
    @com.google.gson.annotations.SerializedName(value = "name")
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String name = null;
    @com.google.gson.annotations.SerializedName(value = "cod")
    private final int cod = 0;
    
    public WeatherResponse(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.Coord coord, @org.jetbrains.annotations.NotNull()
    java.util.List<com.inspekpro.data.remote.model.WeatherDescription> weather, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.MainWeather main, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.Wind wind, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.Clouds clouds, @org.jetbrains.annotations.Nullable()
    com.inspekpro.data.remote.model.Rain rain, int visibility, long dt, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.Sys sys, @org.jetbrains.annotations.NotNull()
    java.lang.String name, int cod) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.Coord getCoord() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.inspekpro.data.remote.model.WeatherDescription> getWeather() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.MainWeather getMain() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.Wind getWind() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.Clouds getClouds() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.inspekpro.data.remote.model.Rain getRain() {
        return null;
    }
    
    public final int getVisibility() {
        return 0;
    }
    
    public final long getDt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.Sys getSys() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final int getCod() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.Coord component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    public final int component11() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.inspekpro.data.remote.model.WeatherDescription> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.MainWeather component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.Wind component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.Clouds component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.inspekpro.data.remote.model.Rain component6() {
        return null;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.Sys component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.inspekpro.data.remote.model.WeatherResponse copy(@org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.Coord coord, @org.jetbrains.annotations.NotNull()
    java.util.List<com.inspekpro.data.remote.model.WeatherDescription> weather, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.MainWeather main, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.Wind wind, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.Clouds clouds, @org.jetbrains.annotations.Nullable()
    com.inspekpro.data.remote.model.Rain rain, int visibility, long dt, @org.jetbrains.annotations.NotNull()
    com.inspekpro.data.remote.model.Sys sys, @org.jetbrains.annotations.NotNull()
    java.lang.String name, int cod) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}