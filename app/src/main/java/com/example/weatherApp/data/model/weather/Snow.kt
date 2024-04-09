package com.example.weatherApp.data.model.weather

import com.google.gson.annotations.SerializedName

data class Snow(
    @SerializedName("d1h") val d1h: Double? = null,
)