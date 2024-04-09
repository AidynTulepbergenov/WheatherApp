package com.example.weatherApp.data.model.weather

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") val speed: Double? = null,
    @SerializedName("deg") val deg: Int? = null,
)