package com.example.weatherApp.data

import android.content.Context
import com.example.weatherApp.domain.constant.Const
import com.example.weatherApp.domain.constant.Const.Companion.ENTER_PREFERENCE

class WeatherPreference(val context: Context) {
    private val enterPreference =
        context.getSharedPreferences(ENTER_PREFERENCE, Context.MODE_PRIVATE)


    fun isFirstEnter(): Boolean = enterPreference.getBoolean(Const.FIRST_LAUNCH, true)

    fun setFirstEnter() = enterPreference.edit().putBoolean(Const.FIRST_LAUNCH, false).apply()

}