package com.example.weatherApp.presentation

import androidx.compose.runtime.Stable
import com.example.weatherApp.data.model.forecast.ForecastResult
import com.example.weatherApp.data.model.weather.WeatherResult
import com.example.weatherApp.domain.constant.Const
import com.example.weatherApp.domain.constant.Const.Companion.LOADING
import com.example.weatherApp.domain.constant.Const.Companion.NA
import com.example.weatherApp.domain.utils.Utils

private const val DATE_MASK = "dd-MM-yyyy"

@Stable
sealed class UiState {
    object EmptyState : UiState()

    object LoadingState : UiState()

    data class DataState(
        val weatherResult: WeatherResult = WeatherResult(),
        val forecastResult: ForecastResult = ForecastResult(),
    ) : UiState() {
        fun getTitle(): String = if (!weatherResult.name.isNullOrEmpty()) {
            weatherResult.name
        } else {
            "${weatherResult.coord?.lat}/${weatherResult.coord?.lon} "
        }

        fun getSubtitle(): String {
            val dateVal = (weatherResult.dt ?: 0)
            return if (dateVal == 0) Const.LOADING else Utils.timeStampToHumanDate(
                timeStamp = dateVal.toLong(),
                format = DATE_MASK,
            )
        }

        fun getTemperature(): String = if (weatherResult.main == null) {
            LOADING
        } else {
            "${weatherResult.main.temp } °C"
        }

        fun getWindSpeed(): String = if (weatherResult.wind == null) {
            LOADING
        } else {
            "${weatherResult.wind.speed}"
        }

        fun getClouds(): String = if (weatherResult.clouds == null) {
            LOADING
        } else {
            "${weatherResult.clouds.all}"
        }

        fun getSnow(): String = if (weatherResult.snow == null) {
            NA
        } else {
            "${weatherResult.snow.d1h}"
        }
    }

    data class ErrorState(val message: String) : UiState()
}