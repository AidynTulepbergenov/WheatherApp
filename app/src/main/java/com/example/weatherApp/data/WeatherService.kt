package com.example.weatherApp.data

import com.example.weatherApp.domain.constant.Const.Companion.openWeatherMapApiKey
import com.example.weatherApp.data.model.forecast.ForecastResult
import com.example.weatherApp.data.model.weather.WeatherResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherMapApiKey,
    ): WeatherResult

    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherMapApiKey,
    ): WeatherResult

    @GET("forecast")
    suspend fun getForecast(
        @Query("lat") lat: Double = 0.0,
        @Query("lon") lon: Double = 0.0,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherMapApiKey,
    ): ForecastResult

    @GET("forecast")
    suspend fun getForecastByCityName(
        @Query("q") cityName: String,
        @Query("units") units: String = "metric",
        @Query("appid") appId: String = openWeatherMapApiKey,
    ): ForecastResult

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        private val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun create(): WeatherService =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
    }
}
