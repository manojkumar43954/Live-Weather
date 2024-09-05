package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.util.Resource
import com.example.weatherapp.domain.weather.CityInfo
import com.example.weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherInfo(lat: Double, long: Double, appId: String): Resource<WeatherInfo>
    suspend fun getCityInfo(name : String, limit: Int, appId: String): Resource<CityInfo>
}