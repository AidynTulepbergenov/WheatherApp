package com.example.weatherApp.domain.repository

import com.example.weatherApp.data.model.forecast.ForecastResult
import com.example.weatherApp.data.model.weather.WeatherResult

interface WeatherRepository {
    suspend fun getWeather(
        lat: Double = 0.0,
        lng: Double = 0.0,
    ): WeatherResult

    suspend fun getWeatherByCityName(
        cityName: String,
    ): WeatherResult

    suspend fun getForecast(
        lat: Double = 0.0,
        lng: Double = 0.0,
    ): ForecastResult

    suspend fun getForecastByCityName(
        cityName: String,
    ): ForecastResult

    fun getCityName(): String

    fun setCityName(cityName: String)

    fun isFirstEntrance(): Boolean

    fun setEntrance()
}