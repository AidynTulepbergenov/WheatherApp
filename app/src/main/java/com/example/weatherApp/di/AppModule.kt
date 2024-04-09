package com.example.weatherApp.di

import com.example.weatherApp.data.WeatherService
import com.example.weatherApp.data.repository.DefaultWeatherRepository
import com.example.weatherApp.domain.repository.WeatherRepository
import com.example.weatherApp.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        WeatherService.create()
    }

    single<WeatherRepository> {
        DefaultWeatherRepository(weatherService = get(), context = get())
    }

    viewModel {
        MainViewModel(weatherRepository = get())
    }
}