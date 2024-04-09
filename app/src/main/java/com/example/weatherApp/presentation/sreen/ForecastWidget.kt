package com.example.weatherApp.presentation.sreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherApp.data.model.forecast.ForecastResult
import com.example.weatherApp.domain.constant.Const.Companion.NA
import com.example.weatherApp.domain.utils.Utils.Companion.buildIcon
import com.example.weatherApp.domain.utils.Utils.Companion.timeStampToHumanDate
import com.example.weatherApp.presentation.UiState

private const val TIME_MASK = "EEE HH:mm"

@Composable
fun ForecastWidget(uiState: UiState.DataState) {
    if (uiState.forecastResult == ForecastResult()) {
        CircularProgressIndicator(
            modifier = Modifier.padding(vertical = 16.dp),
            color = Color.White,
        )
    } else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                text = "Weather for this week",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
            )

            uiState.forecastResult.list?.let { listForecast ->
                if (listForecast.isNotEmpty()) {
                    LazyRow(modifier = Modifier.fillMaxSize()) {
                        items(listForecast) { currentItem ->
                            currentItem.let { item ->
                                var temp: String
                                var icon: String
                                var time: String

                                item.main.let { main ->
                                    temp = if (main == null) NA else "${main.temp} °C"
                                }
                                item.weather.let { weather ->
                                    icon = if (weather == null) NA else buildIcon(
                                        icon = weather.first().icon.orEmpty(),
                                        isBigSize = false,
                                    )
                                }
                                item.dt.let { dateTime ->
                                    time = if (dateTime == null) NA else timeStampToHumanDate(
                                        timeStamp = dateTime.toLong(),
                                        format = TIME_MASK,
                                    )
                                }
                                ForecastTile(temp = temp, icon = icon, time = time)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastTile(temp: String, icon: String, time: String) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff333639).copy(alpha = 0.7f),
            contentColor = Color.White,
        ),
    ) {
        Column(
            modifier = Modifier.padding(60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = temp, color = Color.White)
            AsyncImage(
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.FillBounds,
                model = icon,
                contentDescription = null,
            )
            Text(text = time, color = Color.White)
        }
    }
}
