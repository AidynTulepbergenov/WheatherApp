package com.example.weatherApp.presentation.sreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherApp.R
import com.example.weatherApp.domain.constant.Const
import com.example.weatherApp.domain.utils.Utils
import com.example.weatherApp.presentation.UiState

@Composable
fun WeatherWidget(uiState: UiState.DataState) {
    var icon = ""
    var description = ""
    uiState.weatherResult.weather.let {
        if (it!!.size > 0) {
            description =
                if (it.first().description == null) Const.LOADING else it.first().description.orEmpty()
            icon = if (it.first().icon == null) Const.LOADING else it.first().icon.orEmpty()
        }
    }

    WeatherHeader(text = uiState.getTitle(), subText = uiState.getSubtitle())
    WeatherImage(icon)
    WeatherHeader(text = uiState.getTemperature(), subText = description)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        WeatherInfo(text = uiState.getWindSpeed(), icon = R.drawable.wind_solid)
        WeatherInfo(text = uiState.getClouds(), icon = R.drawable.cloud_solid)
        WeatherInfo(text = uiState.getSnow(), icon = R.drawable.snowflake_solid)
    }

}

@Composable
private fun WeatherHeader(text: String, subText: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = text, fontSize = 30.sp, color = Color.White, fontWeight = FontWeight.Bold)
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = subText,
            fontSize = 14.sp,
            color = Color.White,
        )
    }
}

@Composable
private fun WeatherImage(icon: String) {
    AsyncImage(
        modifier = Modifier.size(200.dp),
        contentScale = ContentScale.FillBounds,
        model = Utils.buildIcon(icon),
        contentDescription = icon,
    )
}

@Composable
private fun WeatherInfo(text: String, icon: Int) {
    Column {
        Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.White,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            text = text,
            fontSize = 24.sp,
            color = Color.White,
        )
    }
}