package com.example.weatherApp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherApp.data.model.forecast.City
import com.example.weatherApp.domain.model.LatLng
import com.example.weatherApp.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class MainViewModel(
    private val weatherRepository: WeatherRepository,
) : ViewModel(), KoinComponent, MainController {
    val uiState = MutableStateFlow<UiState>(UiState.EmptyState)

    init {
        if (!weatherRepository.isFirstEntrance()) {
            val cityName = weatherRepository.getCityName()
            fetchDataByCityName(cityName)
        }
    }

    override fun fetchData(latLng: LatLng) {
        getWeatherData(latLng = latLng)
        getForecastData(latLng = latLng)
    }

    override fun fetchDataByCityName(cityName: String) {
        if (checkFirstEntrance()) {
            setCityName(cityName)
            fetchCityWeather(cityName)
        } else {
            fetchCityWeather(cityName)
        }
    }

    private fun checkFirstEntrance(): Boolean = weatherRepository.isFirstEntrance()

    private fun setCityName(cityName: String) {
        weatherRepository.setCityName(cityName)
        weatherRepository.setEntrance()
    }

    private fun fetchCityWeather(cityName: String) {
        getWeatherDataByCityName(cityName = cityName)
        getForecastDataByCityName(cityName = cityName)
    }

    private fun getWeatherData(latLng: LatLng) {
        viewModelScope.launch {
            initLoading()
            try {
                val response = weatherRepository.getWeather(lat = latLng.lat, lng = latLng.lng)
                val prevData = uiState.value
                uiState.update {
                    if (prevData is UiState.DataState) {
                        prevData.copy(
                            weatherResult = response
                        )
                    } else UiState.DataState(weatherResult = response)
                }
            } catch (e: Exception) {
                uiState.update {
                    UiState.ErrorState(message = e.message!!.toString())
                }
            }
        }
    }

    private fun getWeatherDataByCityName(cityName: String) {
        viewModelScope.launch {
            initLoading()
            try {
                val response = weatherRepository.getWeatherByCityName(cityName = cityName)
                val prevData = uiState.value
                uiState.update {
                    if (prevData is UiState.DataState) {
                        prevData.copy(
                            weatherResult = response
                        )
                    } else UiState.DataState(weatherResult = response)
                }
            } catch (e: Exception) {
                uiState.update {
                    UiState.ErrorState(message = e.message!!.toString())
                }
            }
        }
    }

    private fun getForecastData(latLng: LatLng) {
        viewModelScope.launch {
            initLoading()
            try {
                val response = weatherRepository.getForecast(lat = latLng.lat, lng = latLng.lng)
                val prevData = uiState.value
                uiState.update {
                    if (prevData is UiState.DataState) {
                        prevData.copy(
                            forecastResult = response
                        )
                    } else UiState.DataState(forecastResult = response)
                }
            } catch (e: Exception) {
                uiState.update {
                    UiState.ErrorState(message = e.message!!.toString())
                }
            }
        }
    }

    private fun getForecastDataByCityName(cityName: String) {
        viewModelScope.launch {
            initLoading()
            try {
                val response = weatherRepository.getForecastByCityName(cityName = cityName)
                val prevData = uiState.value
                uiState.update {
                    if (prevData is UiState.DataState) {
                        prevData.copy(
                            forecastResult = response
                        )
                    } else UiState.DataState(forecastResult = response)
                }
            } catch (e: Exception) {
                uiState.update {
                    UiState.ErrorState(message = e.message!!.toString())
                }
            }
        }
    }

    private fun initLoading() {
        uiState.update {
            UiState.LoadingState
        }
    }
}