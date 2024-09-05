package com.example.weatherapp.data.repository

import com.example.weatherapp.data.mappers.toCityInfoMap
import com.example.weatherapp.data.mappers.toWeatherInfoMap
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.domain.util.Resource
import com.example.weatherapp.domain.weather.CityInfo
import com.example.weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherInfo(lat: Double, long: Double, appId: String):
            Resource<WeatherInfo> {
        return try {
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    long = long,
                    appId = appId
                ).toWeatherInfoMap()
            )
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    override suspend fun getCityInfo(name: String, limit: Int, appId: String): Resource<CityInfo> {
        return try {
            val cityInfo = api.getWeatherDataByCityName(
                q = name,
                limit = limit,
                appId = appId
            ).toCityInfoMap()
            Resource.Success(
                data = cityInfo)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}