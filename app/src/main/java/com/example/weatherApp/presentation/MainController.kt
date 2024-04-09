package com.example.weatherApp.presentation

import com.example.weatherApp.domain.model.LatLng

interface MainController {
    fun fetchData(latLng: LatLng)
    fun fetchDataByCityName(cityName: String)
}