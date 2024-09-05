package com.example.weatherapp

import com.example.weatherapp.domain.util.Resource
import com.example.weatherapp.domain.weather.WeatherInfo

class FakeWeatherRepository {

    fun getWeatherInfo(): Resource<WeatherInfo> {
        val weatherInfo = WeatherInfo(
            temp = 298.48,
            description = "description"
        )
        return Resource.Success(weatherInfo)
    }
}