package com.example.weatherapp.data.remote

import com.squareup.moshi.Json

data class CityDataDto(
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "lat")
    val lat: Double? = null,
    @field:Json(name = "lon")
    val lon: Double? = null,
    @field:Json(name = "country")
    val country: String? = null,
    @field:Json(name = "state")
    val state: String? = null
)


