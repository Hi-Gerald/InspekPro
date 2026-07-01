package com.inspekpro.data.remote.api

import com.inspekpro.data.remote.model.WeatherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// ─── API SERVICE INTERFACE ────────────────────────────────────────────────────

interface WeatherApiService {

    /**
     * Ambil cuaca berdasarkan koordinat GPS (dipakai saat sesi baru dibuat dengan lokasi GPS)
     * units=metric → suhu langsung Celsius
     * lang=id      → deskripsi dalam Bahasa Indonesia
     */
    @GET("weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat")     lat: Double,
        @Query("lon")     lon: Double,
        @Query("appid")   apiKey: String = WeatherApiService.API_KEY,
        @Query("units")   units: String = "metric",
        @Query("lang")    lang: String = "id"
    ): Response<WeatherResponse>

    /**
     * Ambil cuaca berdasarkan nama kota (dipakai saat user isi lokasi manual)
     */
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q")       cityName: String,
        @Query("appid")   apiKey: String = WeatherApiService.API_KEY,
        @Query("units")   units: String = "metric",
        @Query("lang")    lang: String = "id"
    ): Response<WeatherResponse>

    companion object {
        /**
         * API key dibaca dari BuildConfig agar tidak ter-commit ke Git.
         * Setup:
         *   1. Buka file local.properties (jangan di-commit!)
         *   2. Tambahkan baris:  WEATHER_API_KEY=isi_key_kamu_di_sini
         *   3. Di app/build.gradle.kts, di dalam defaultConfig tambahkan:
         *        buildConfigField("String", "WEATHER_API_KEY", "\"${properties["WEATHER_API_KEY"] ?: ""}\"")
         *      dan di atas android { } tambahkan:
         *        val properties = java.util.Properties().also {
         *            it.load(rootProject.file("local.properties").inputStream())
         *        }
         *   4. Sync Gradle → BuildConfig.WEATHER_API_KEY siap dipakai.
         *
         * Daftar key gratis di: https://openweathermap.org/api
         */
        val API_KEY: String get() = try {
            com.inspekpro.BuildConfig.WEATHER_API_KEY
        } catch (_: Exception) {
            "" // fallback kosong → request gagal gracefully, tidak crash
        }
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val ICON_BASE_URL = "https://openweathermap.org/img/wn/"
    }
}

// ─── RETROFIT CLIENT ─────────────────────────────────────────────────────────

object RetrofitClient {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY  // Ganti ke NONE di production
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(WeatherApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApiService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}
