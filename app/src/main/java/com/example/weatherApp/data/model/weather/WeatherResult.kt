package com.example.weatherApp.data.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherResult(
    @SerializedName("coord") val coord: Coordinates? = null,
    @SerializedName("weather") val weather: ArrayList<Weather>? = arrayListOf(),
    @SerializedName("base") val base: String? = null,
    @SerializedName("main") val main: Main? = null,
    @SerializedName("visibility") val visibility: Int? = null,
    @SerializedName("wind") val wind: Wind? = null,
    @SerializedName("clouds") val clouds: Clouds? = null,
    @SerializedName("dt") val dt: Int? = null,
    @SerializedName("sys") val sys: Sys? = null,
    @SerializedName("timezone") val timeZone: Int? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("code") val code: Int? = null,
    @SerializedName("snow") val snow: Snow? = null,
)