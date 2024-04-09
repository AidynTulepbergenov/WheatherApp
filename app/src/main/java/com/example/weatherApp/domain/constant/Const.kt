package com.example.weatherApp.domain.constant

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

class Const {
    companion object {
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )

        val backGradient = Brush.linearGradient(
            colors = listOf(Color(0xff33C7FF), Color(0xff00678E)),
            start = Offset(1000f, -1000f),
            end = Offset(1000f, 1000f),
        )

        const val openWeatherMapApiKey = "7879fe3bda579ae94bb0843959689996"

        const val LOADING = "Loading..."
        const val NA = "N/A"
        const val ENTER_PREFERENCE = "EnterPreference"
        const val FIRST_LAUNCH = "isFirstLaunch"
    }
}