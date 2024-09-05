package com.example.weatherapp.domain.weather

data class WeatherInfo(
    val temp: Double? = null,
    val description: String? = null,
    val windSpeed: Double? = null,
    val windGust: Double? = null,
    val humidity: Int? = null,
    val icon: String? = null
)