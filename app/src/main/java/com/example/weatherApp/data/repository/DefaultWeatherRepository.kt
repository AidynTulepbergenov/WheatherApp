package com.example.weatherApp.data.repository

import android.content.Context
import com.example.weatherApp.data.WeatherService
import com.example.weatherApp.data.model.forecast.ForecastResult
import com.example.weatherApp.data.model.weather.WeatherResult
import com.example.weatherApp.domain.repository.WeatherRepository

class DefaultWeatherRepository(
    private val weatherService: WeatherService,
    context: Context,
) : WeatherRepository {

    private val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    override suspend fun getWeather(lat: Double, lng: Double): WeatherResult =
        weatherService.getWeather(lat = lat, lon = lng)

    override suspend fun getWeatherByCityName(cityName: String): WeatherResult =
        weatherService.getWeatherByCityName(cityName = cityName)


    override suspend fun getForecast(lat: Double, lng: Double): ForecastResult =
        weatherService.getForecast(lat = lat, lon = lng)

    override suspend fun getForecastByCityName(cityName: String): ForecastResult =
        weatherService.getForecastByCityName(cityName = cityName)

    override fun getCityName(): String {
        val cityName = "Almaty"
        return sharedPreferences.getString("cityName", cityName).orEmpty()
    }

    override fun setCityName(cityName: String) {
        sharedPreferences.edit().putString("cityName", cityName).apply()
    }

    override fun isFirstEntrance(): Boolean = sharedPreferences.getBoolean("isFirstEntrance", true)


    override fun setEntrance() {
        sharedPreferences.edit().putBoolean("isFirstEntrance", false).apply()
    }

}