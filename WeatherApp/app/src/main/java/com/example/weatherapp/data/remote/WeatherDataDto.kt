package com.example.weatherapp.data.remote

import com.squareup.moshi.Json

data class WeatherDataDto(
    @field:Json(name = "main")
    val main: Main? = null,
    @field:Json(name = "wind")
    val wind: Wind? = null,
    @field:Json(name = "weather")
    val weatherList: List<Weather>? = null
)

data class Main(
    @field:Json(name = "temp")
    val temp: Double? = null,
    @field:Json(name = "feels_like")
    val feelsLike: Double? = null,
    @field:Json(name = "pressure")
    val pressure: Double? = null,
    @field:Json(name = "humidity")
    val humidity: Double? = null
    )

data class Wind(
    @field:Json(name = "speed")
    val speed: Double? = null,
    @field:Json(name = "deg")
    val deg: Double? = null
    )

data class Weather(
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "main")
    val main: String? = null,
    @field:Json(name = "description")
    val description: String? = null,
    @field:Json(name = "icon")
    val icon: String? = null
    )



