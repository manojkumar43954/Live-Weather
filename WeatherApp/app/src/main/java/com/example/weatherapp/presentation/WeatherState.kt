package com.example.weatherapp.presentation

import com.example.weatherapp.domain.weather.WeatherInfo

/**
 * Represents the weather screen's current states.
 */
data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLocationNotFound: Boolean = false
)
