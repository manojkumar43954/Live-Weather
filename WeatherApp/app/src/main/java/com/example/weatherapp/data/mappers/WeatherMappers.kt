package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.remote.CityDataDto
import com.example.weatherapp.data.remote.WeatherDataDto
import com.example.weatherapp.domain.weather.CityInfo
import com.example.weatherapp.domain.weather.WeatherInfo

/**
 * Transfers only the screen's needed information
 */
fun WeatherDataDto.toWeatherInfoMap() : WeatherInfo {
   return WeatherInfo(
       temp = this.main?.temp,
       description = this.weatherList?.get(0)?.description,
       windSpeed = this.wind?.speed,
       windGust = this.wind?.deg,
       humidity = this.main?.humidity?.toInt(),
       icon = this.weatherList?.get(0)?.icon
   )
}

fun List<CityDataDto>.toCityInfoMap() : CityInfo {
    return CityInfo(
        lat =  this[0].lat,
        long = this[0].lon
    )
}