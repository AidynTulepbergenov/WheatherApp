package com.example.weatherApp.data.model.forecast

import com.example.weatherApp.data.model.weather.Clouds
import com.example.weatherApp.data.model.weather.Main
import com.example.weatherApp.data.model.weather.Sys
import com.example.weatherApp.data.model.weather.Weather
import com.example.weatherApp.data.model.weather.Wind
import com.google.gson.annotations.SerializedName

data class CustomList (
    @SerializedName("dt") val dt: Int? = null,
    @SerializedName("main") val main: Main? = null,
    @SerializedName("weather") val weather: ArrayList<Weather>? = null,
    @SerializedName("clouds") val clouds: Clouds? = null,
    @SerializedName("wind") val wind: Wind? = null,
    @SerializedName("visibility") val visibility: Int? = null,
    @SerializedName("pop") val pop: Double? = null,
    @SerializedName("sys") val sys: Sys? = null,
    @SerializedName("dt_text") val dtText: String? = null,
)