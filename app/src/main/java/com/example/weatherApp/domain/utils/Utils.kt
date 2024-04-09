package com.example.weatherApp.domain.utils

import java.text.SimpleDateFormat

class Utils {
    companion object {
        fun timeStampToHumanDate(timeStamp: Long, format: String): String =
            SimpleDateFormat(format).format(timeStamp * 1000)

        fun buildIcon(icon: String, isBigSize: Boolean = true): String = if (isBigSize) {
            "https://openweathermap.org/img/wn/$icon@4x.png"
        } else {
            "https://openweathermap.org/img/wn/$icon.png"
        }
    }
}